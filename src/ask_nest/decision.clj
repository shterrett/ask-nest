(ns ask-nest.decision
  (:require [ask-nest.weather-data :refer [precipitation-threshold]]))

(defn external-temp [{:keys [average-temp
                             average-apparent-temp]}]
    (/ (+ average-temp average-apparent-temp) 2))

(defn nest-cool-comparison [thermostat external-temperature]
  (if (>= (:target_temperature_high_f thermostat) external-temperature)
    :off
    :cool))

(defn nest-heat-comparison [thermostat external-temperature]
  (if (<= (:target_temperature_low_f thermostat) external-temperature)
    :off
    :heat))

(defn nest-heat-cool-comparison [thermostat external-temperature]
  (let [cool (nest-cool-comparison thermostat external-temperature)
        heat (nest-heat-comparison thermostat external-temperature)]
    (if (and (= heat :off) (= cool :off))
      :off
      :heat-cool)))

(defn nest-off-comparison [thermostat external-temperature]
  (let [cool (nest-cool-comparison thermostat external-temperature)
        heat (nest-heat-comparison thermostat external-temperature)]
    (if (and (= heat :off) (= cool :off))
      :off
      (first (remove #(= % :off) [heat cool])))))

(def nest-comparison-functions
  {"cool" nest-cool-comparison
   "heat" nest-heat-comparison
   "heat-cool" nest-heat-cool-comparison
   "off" nest-off-comparison})

(defn future-nest-state [thermostat weather]
  ((get nest-comparison-functions (:hvac_mode thermostat))
     thermostat
     (external-temp weather)))

(defn likely-precip [weather]
  (>= (:max-precip-prob weather) precipitation-threshold))

(defn future-window-state [weather]
  (if (likely-precip weather) :closed :open))

(defn turn-nest-on [current-state future-state]
   (if-let [state (first (remove #(= % :off) [current-state future-state]))]
     {:nest state
      :windows :closed
      :change (not= state current-state)}
     {:nest :on
      :windows :closed
      :change true}))

(defn close-windows [current-state future-state]
  {:nest future-state
   :windows :closed
   :change (not= current-state future-state)})

(defn combined-future-state [thermostat nest-state windows-state]
  (let [current-nest (keyword (:hvac_mode thermostat))
        nest-turning-on? (not= :off nest-state)
        windows-closing? (= :closed windows-state)]

    (cond windows-closing? (turn-nest-on current-nest nest-state)
          nest-turning-on? (close-windows current-nest nest-state)
          :else {:nest nest-state
                 :windows windows-state
                 :change (not= current-nest nest-state)})))


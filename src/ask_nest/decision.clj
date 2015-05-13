(ns ask-nest.decision)

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

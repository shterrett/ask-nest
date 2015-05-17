(ns ask-nest.weather-data
  (:require [org.httpkit.client :as http]
            [ask-nest.data-shared :as data]
            [clojure.string :as string :only join]
            [environ.core :refer [env]]))

(def precipitation-threshold 0.3)

(def api-url "https://api.forecast.io/forecast")

(defn weather-request [token latitude longitude]
  (let [url (string/join "/" [api-url
                              token
                              (string/join "," [latitude longitude])])]
    @(http/get url)))

(defn read-weather [latitude longitude]
  (let [token (env :weather-token)]
    (data/remote-request weather-request [token latitude longitude])))

(defn section-ly-data [data section]
  (get-in data [section :data]))

(defn average [data section sample-size value]
  (/ (reduce (fn [total sample] (+ total (get sample value)))
             0
             (take sample-size (section-ly-data data section)))
     sample-size))

(defn max-val [data section sample-size value]
  (value (apply max-key value (take sample-size (section-ly-data data section)))))

(defn count-greater-than [data section sample-size value threshold]
  (count (filter (fn [sample] (>= (get sample value) threshold))
                 (take sample-size (section-ly-data data section)))))

(defn major-value [data section sample-size value]
  (let [value-counts (reduce (fn [counts sample]
                               (assoc counts (get sample value) (+ 1 (get counts value 0))))
                             {}
                             (take sample-size (section-ly-data data section)))]
    (key (apply max-key val (-> value-counts
                              (dissoc nil)
                              (assoc "none" 0))))))

(defn forecast [weather-data]
  (let [hourly-average (partial average weather-data :hourly 12)]
    {:average-temp (hourly-average :temperature)
     :average-apparent-temp (hourly-average :apparentTemperature)
     :max-precip-prob (max-val weather-data :hourly 12 :precipProbability)
     :hours-likely-precip (count-greater-than weather-data :hourly 12 :precipProbability precipitation-threshold)
     :precip-type (major-value weather-data :hourly 12 :precipType)}))

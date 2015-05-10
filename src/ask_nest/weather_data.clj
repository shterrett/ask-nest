(ns ask-nest.weather-data
  (:require [org.httpkit.client :as http]
            [ask-nest.data-shared :as data]))

(def api-url "http://api.openweathermap.org/data/2.5/forecast/daily")

(defn weather-request [city-id country-code]
  (let [zip-param (format "%s,%s" city-id country-code)]
    @(http/get api-url
               {:query-params {:q zip-param
                               :mode "json"
                               :units "metric"
                               :cnt 1}})))

(defn read-weather [zip-code-or-name country-code]
  (data/remote-request weather-request [zip-code-or-name country-code]))

(defn forecast [weather-data]
  (let [conditions (-> weather-data
                      :list
                      (first))
        temp (:temp conditions)]
    {:morning-temp (:morn temp)
     :day-temp (:day temp)
     :evening-temp (:eve temp)
     :night-temp (:night temp)
     :weather-type (map #(:id %) (:weather conditions))}))

(ns ask-nest.nest-data
  (:require [org.httpkit.client :as http]
            [ask-nest.data-shared :as data]))

(def api-url "https://developer-api.nest.com/")

(defn nest-request [token]
  @(http/get api-url {:query-params {:auth token}}))

(defn read-devices [token]
  (data/remote-request nest-request [token]))

(defn houses [nest-data]
  (reduce (fn [hs h]
            (into hs [[(:name h) {:postal-code (:postal_code h)
                                  :country-code (:country_code h)
                                  :thermostat-ids (:thermostats h)}]]))
          {}
          (vals (:structures nest-data))))

(defn thermostats [nest-data]
  (:thermostats (:devices nest-data)))

(defn thermostats-for-house [house therms]
  (->> (:thermostat-ids house)
    (map keyword)
    (map #(get therms %))))

(defn houses-with-thermostats [house-map therms-map]
  (reduce-kv (fn [hs name data]
               (assoc hs
                      name
                      (assoc data
                             :thermostats
                             (thermostats-for-house data therms-map))))
             {}
             house-map))

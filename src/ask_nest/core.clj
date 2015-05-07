(ns ask-nest.core
  (:require [org.httpkit.client :as http]
            [clojure.data.json :as json]))

(defn nest-request [token]
  @(http/get "https://developer-api.nest.com/" {:query-params {:auth token}}))

(defn to-json [response-body]
  (json/read-str response-body :key-fn keyword))

(defn read-devices [token]
  (let [{:keys [status headers body error]} (nest-request token)]
    (if error
      {:error true
       :message error}
      (into {:error false} (to-json body)))))

(defn houses [nest-data]
  (reduce (fn [hs h]
            (into hs [[(:name h) {:postal-code (:postal_code h)
                                  :country-code (:country_code h)
                                  :thermostat-ids (:thermostats h)}]]))
          {}
          (vals (:structures nest-data))))

(defn thermostats [nest-data]
  (:thermostats (:devices nest-data)))

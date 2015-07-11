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
            (into hs [[(:structure_id h)
                       {:name (:name h)
                        :postal-code (:postal_code h)
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
  (reduce-kv (fn [hs structure-id data]
               (assoc hs
                      structure-id
                      (assoc data
                             :thermostats
                             (thermostats-for-house data therms-map))))
             {}
             house-map))

(def token-url "https://api.home.nest.com/oauth2/access_token")

(defn client-api-token [pin client-id client-secret]
  @(http/post token-url
              {:query-params {:code pin
                              :client_id client-id
                              :client_secret client-secret
                              :grant_type "authorization_code"}}))

(defn get-token [pin client-id client-secret]
  (:access_token (data/remote-request client-api-token [pin client-id client-secret])))

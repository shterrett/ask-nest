(ns ask-nest.weather-data-test
  (:require [clojure.test :refer :all]
            [ask-nest.weather-data :refer :all]
            [org.httpkit.client :as http]
            [org.httpkit.fake :as fake-http]
            [environ.core :refer [env]]))

(deftest weather_data
  (testing "data from weather api"
    (let [response (read-string (slurp "test/data/weather_api_boston.clj"))
          latitude 42.3601
          longitude -71.0589]
      (fake-http/with-fake-http [(format "%s/%s/%s,%s"
                                         api-url
                                         (env :weather-token)
                                         latitude
                                         longitude)
                                 response]
        (testing "fake data"
          (is (= 200 (:status (weather-request (env :weather-token)
                                               latitude
                                               longitude)))))
        (testing "forecast"
          (let [data (forecast (read-weather latitude longitude))]
            (is (> 0.1 (Math/abs (- 65.53 (:average-temp data)))))
            (is (> 0.1 (Math/abs (- 65.82 (:average-apparent-temp data)))))
            (is (= 0.43 (:max-precip-prob data)))
            (is (= 3 (:hours-likely-precip data)))
            (is (= "rain" (:precip-type data)))))))))

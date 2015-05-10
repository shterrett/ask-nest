(ns ask-nest.weather-data-test
  (:require [clojure.test :refer :all]
            [ask-nest.weather-data :refer :all]
            [org.httpkit.client :as http]
            [org.httpkit.fake :as fake-http]))

(deftest weather_data
  (testing "data from weather api"
    (let [response (read-string (slurp "test/data/weather_api_boston.clj"))
          zip-code "02215"
          country-code "us"]
      (fake-http/with-fake-http [api-url response]
        (testing "fake data"
          (is (= 200 (:status (weather-request zip-code country-code)))))
        (testing "forecast"
          (let [data (forecast (read-weather zip-code country-code))]
            (is (= 16.4 (:morning-temp data)))
            (is (= 20.84 (:day-temp data)))
            (is (= 23.68 (:evening-temp data)))
            (is (= 19.71 (:night-temp data)))
            (is (= [500] (:weather-type data)))))))))

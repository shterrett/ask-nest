(ns ask-nest.nest-data-test
  (:require [clojure.test :refer :all]
            [ask-nest.nest-data :refer :all]
            [org.httpkit.client :as http]
            [org.httpkit.fake :as fake-http]))

(deftest nest_data
  (testing "data from nest api"
    (let [response (read-string (slurp "test/data/nest_api_root.clj"))
          token "secrettoken"]
      (fake-http/with-fake-http [api-url response]
        (testing "fake data"
          (is (= 200 (:status (nest-request token)))))
        (testing "returning nest data"
          (let [nest-data (read-devices token)]
            (is (= ["Home"]
                  (keys (houses nest-data))))
            (is (= ["US"] (map :country-code (vals (houses nest-data)))))
            (is (= [:2-ShT4H3BvS8kWCWjPQZiHtAaHJ5cKch]
                   (keys (thermostats nest-data))))
            (is (= [68] (map :target_temperature_low_f
                             (vals (thermostats nest-data)))))
            (let [house (get (houses-with-thermostats
                               (houses nest-data)
                               (thermostats nest-data))
                             "Home")]
              (is (= (:thermostat-ids house)
                     (map :device_id (:thermostats house)))))))))))

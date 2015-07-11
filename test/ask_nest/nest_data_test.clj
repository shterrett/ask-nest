(ns ask-nest.nest-data-test
  (:require [clojure.test :refer :all]
            [ask-nest.nest-data :refer :all]
            [org.httpkit.client :as http]
            [org.httpkit.fake :as fake-http]))

(deftest nest_data
  (deftest retrieve-device-data
    (let [response (read-string (slurp "test/data/nest_api_root.clj"))
          token "secrettoken"]
      (fake-http/with-fake-http [api-url response]
        (testing "fake data"
          (is (= 200 (:status (nest-request token)))))
        (testing "returning nest data"
          (let [nest-data (read-devices token)]
            (is (= ["dLlhu0oXCfCqxL-0_-rmq9FZcrpy_1_bXI8uB6aDJtxLhNmXjw2iJQ"]
                  (keys (houses nest-data))))
            (is (= ["US"] (map :country-code (vals (houses nest-data)))))
            (is (= ["Home"] (map :name (vals (houses nest-data)))))
            (is (= [:2-ShT4H3BvS8kWCWjPQZiHtAaHJ5cKch]
                   (keys (thermostats nest-data))))
            (is (= [68] (map :target_temperature_low_f
                             (vals (thermostats nest-data)))))
            (let [house (get (houses-with-thermostats
                               (houses nest-data)
                               (thermostats nest-data))
                             "dLlhu0oXCfCqxL-0_-rmq9FZcrpy_1_bXI8uB6aDJtxLhNmXjw2iJQ")]
              (is (= (:thermostat-ids house)
                     (map :device_id (:thermostats house))))))))))
  (deftest retrieve-access-token
    (let [response (read-string (slurp "test/data/nest_access_token.clj"))
          pin "abc123"
          client-id "gibberish"
          client-secret "more&gibberish"
          client-api-token "c.edWORRyvbQFkF2femy7hgQtazu8kLQTaVkCk2bqv66vyGKBMCABBUwd6VQUG9ZZ7ynGCctFgEtY73vpWS5Dk0hTHNNZEmvywSfLnbilOOjTA7djloRK6nQnHoKVDuMDCUcFPT2VvnToeaFuh"
          ]
      (fake-http/with-fake-http [token-url response]
        (is (= client-api-token (get-token pin client-id client-secret)))))))

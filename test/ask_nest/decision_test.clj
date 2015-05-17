(ns ask-nest.decision-test
  (:require [clojure.test :refer :all]
            [ask-nest.weather-data :refer [precipitation-threshold]]
            [ask-nest.decision :refer :all]))

(deftest decision
  (testing "deciding nest and windows based on weather"
    (testing "future state of nest by temperature"
      (let [warm-weather {:average-temp 78
                          :average-apparent-temp 82}
            cold-weather {:average-temp 50
                          :average-apparent-temp 46}
              really-hot-weather {:average-temp 90
                                   :average-apparent-temp 94}]
      (testing "external temperature"
          (is (= (/ (+ 78 82) 2) (external-temp warm-weather)))
          (is (= (/ (+ 50 46) 2) (external-temp cold-weather))))
      (testing "currently heat"
        (let [thermostat {:temperature_scale "F"
                          :hvac_mode "heat"
                          :target_temperature_high_f 76
                          :target_temperature_low_f 72}]
          (is (= :off (future-nest-state thermostat warm-weather)))
          (is (= :heat (future-nest-state thermostat cold-weather)))))
      (testing "currently cool"
        (let [thermostat {:temperature_scale "F"
                          :hvac_mode "cool"
                          :target_temperature_high_f 76
                          :target_temperature_low_f 72}]
          (is (= :off (future-nest-state thermostat cold-weather)))
          (is (= :cool (future-nest-state thermostat warm-weather)))))
      (testing "currently heat-cool"
        (let [thermostat {:temperature_scale "F"
                          :hvac_mode "heat-cool"
                          :target_temperature_high_f 81
                          :target_temperature_low_f 79}]
          (is (= :off (future-nest-state thermostat warm-weather)))
          (is (= :heat-cool (future-nest-state thermostat cold-weather)))
          (is (= :heat-cool (future-nest-state thermostat really-hot-weather)))))
      (testing "currently off"
        (let [thermostat {:temperature_scale "F"
                          :hvac_mode "off"
                          :target_temperature_high_f 81
                          :target_temperature_low_f 79}]
          (is (= :off (future-nest-state thermostat warm-weather)))
          (is (= :cool (future-nest-state thermostat really-hot-weather)))
          (is (= :heat (future-nest-state thermostat cold-weather)))))))
    (testing "future state of windows by weather type"
      (let [rain {:average-temp 68.35
                  :average-apparent-temp 70.23
                  :max-precip-prob 0.4
                  :hours-likely-precip 3
                  :precip-type "rain"}
            maybe-rain {:average-temp 68.35
                        :average-apparent-temp 70.23
                        :max-precip-prob 0.2
                        :hours-likely-precip 0
                        :precip-type "rain"}
            short-rain {:average-temp 68.35
                        :average-apparent-temp 70.23
                        :max-precip-prob 0.3
                        :hours-likely-precip 1
                        :precip-type "rain"}
            sun {:average-temp 68.35
                 :average-apparent-temp 70.23
                 :max-precip-prob 0.0
                 :hours-likely-precip 0
                 :precip-type "none"}]
        (testing "future state open if precip under threshold"
          (is (= :open (future-window-state maybe-rain)))
          (is (= :open (future-window-state sun))))
        (testing "future state closed if precip at or over threshold"
          (is (= :closed (future-window-state short-rain)))
          (is (= :closed (future-window-state rain))))))
    (testing "combined future state with interactions"
      (let [thermostat {:temperature_scale "F"
                        :hvac_mode "cool"
                        :target_temperature_high_f 76
                        :target_temperature_low_f 72}]
        (testing "nest stays on when windows closed"
          (is (= :cool (:nest (combined-future-state thermostat :off :closed))))
          (is (= :closed (:windows (combined-future-state thermostat :off :closed))))
          (is (= :cool (:nest (combined-future-state thermostat :cool :closed))))
          (is (= :closed (:windows (combined-future-state thermostat :cool :closed)))))
        (testing "nest turns on when currently off but windows closed"
          (let [off-thermostat (assoc thermostat :hvac_mode "off")]
            (is (= :on (:nest (combined-future-state off-thermostat :off :closed))))
            (is (= :cool (:nest (combined-future-state off-thermostat :cool :closed))))))
        (testing "nest and windows unchanged when nest is off and windows open"
          (is (= :off (:nest (combined-future-state thermostat :off :open))))
          (is (= :open (:windows (combined-future-state thermostat :off :open)))))
        (testing "windows close if nest is on"
          (is (= :cool (:nest (combined-future-state thermostat :cool :open))))
          (is (= :closed (:windows (combined-future-state thermostat :cool :open))))
          (is (= :cool (:nest (combined-future-state thermostat :cool :closed))))
          (is (= :closed (:windows (combined-future-state thermostat :cool :closed)))))
        (testing "change required if future nest state differs from current"
          (is (= true (:change (combined-future-state thermostat :off :open))))
          (is (= false (:change (combined-future-state thermostat :cool :closed)))))))))

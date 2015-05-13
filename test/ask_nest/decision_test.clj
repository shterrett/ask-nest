(ns ask-nest.decision-test

  (:require [clojure.test :refer :all]
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
          (is (= :heat (future-nest-state thermostat cold-weather)))))))))

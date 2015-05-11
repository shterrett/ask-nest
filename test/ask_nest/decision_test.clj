(ns ask-nest.decision-test

  (:require [clojure.test :refer :all]
            [ask-nest.decision :refer :all]))

(deftest decision
  (testing "deciding nest and windows based on weather"
    (testing "future state of nest by temperature"
      (let [warm-weather {:morning-temp 18
                          :day-temp 20
                          :evening-temp 19
                          :night-temp 17}
            cold-weather {:morning-temp 12
                          :day-temp 14
                          :evening-temp 11
                          :night-temp 9}]
      (testing "external temperature"
          (is (= (/ (+ 18 20 19) 3) (external-temp warm-weather :morning)))
          (is (= (/ (+ 19 17 18) 3) (external-temp warm-weather :evening))))
      (testing "currently heat"
        (let [thermostat {:temperature_scale "C"
                          :hvac_mode "heat"
                          :target_temperature_high_c 18
                          :target_temperature_low_c 16}]
          (is (= :off (future-nest-state thermostat warm-weather :morning)))
          (is (= :heat (future-nest-state thermostat cold-weather :morning)))))
      (testing "currently cool"
        (let [thermostat {:temperature_scale "C"
                          :hvac_mode "cool"
                          :target_temperature_high_c 12
                          :target_temperature_low_c 10}]
          (println (external-temp cold-weather :morning))
          (is (= :off (future-nest-state thermostat cold-weather :evening)))
          (is (= :cool (future-nest-state thermostat warm-weather :morning)))))
      (testing "currently heat-cool"
        (let [thermostat {:temperature_scale "C"
                          :hvac_mode "heat-cool"
                          :target_temperature_high_c 13
                          :target_temperature_low_c 11}]
          (is (= :off (future-nest-state thermostat cold-weather :morning)))
          (is (= :heat-cool (future-nest-state thermostat cold-weather :evening)))
          (is (= :heat-cool (future-nest-state thermostat warm-weather :morning)))))
      (testing "currently off"
        (let [thermostat {:temperature_scale "C"
                          :hvac_mode "off"
                          :target_temperature_high_c 18
                          :target_temperature_low_c 16}]
          (is (= :off (future-nest-state thermostat warm-weather :evening)))
          (is (= :cool (future-nest-state thermostat warm-weather :morning)))
          (is (= :heat (future-nest-state thermostat cold-weather :morning)))))))))

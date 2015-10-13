(ns ask-nest.message-test
  (:require [clojure.test :refer :all]
            [ask-nest.message :refer :all]))

(deftest message
  (testing "weather string"
    (let [forecast {:average-temp 70
                    :average-apparent-temp 72
                    :max-precip-prob 20
                    :hours-likely-precip 1
                    :precip-type :rain}]
      (is (= (weather-string forecast)
             "It will be about 70 degrees outside, with a 20% chance of rain."))))
  (testing "nest string"
    (let [decision {:change true
                    :windows :open
                    :nest :off}]
      (is (= (nest-string decision)
             "Consider setting the thermostat to off and leaving the windows open."))))
  (testing "full text message"
    (let [decision {:change true
                    :windows :closed
                    :nest :heat}
          forecast {:average-temp 25
                    :average-apparent-temp 32
                    :max-precip-prob 25
                    :hours-likely-precip 3
                    :precip-type :snow}]
      (is (= (text-message forecast decision)
             (str "It will be about 25 degrees outside, with a 25% chance of snow. "
                  "Consider setting the thermostat to heat and leaving the windows closed."))))))

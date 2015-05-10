(ns ask-nest.weather-codes-test
  (:require [clojure.test :refer :all]
            [ask-nest.weather-codes :refer :all]))

(deftest weather_codes
  (testing "retrieving information by weather code"
    (testing "detail descriptions"
      (is (= "light thunderstorm" (detail-description 210))))
    (testing "images"
      (is (= ["http://openweathermap.org/img/w/11d.png"] (images 200)))
      (is (= ["http://openweathermap.org/img/w/04d.png"
              "http://openweathermap.org/img/w/03d.png"]
             (images 803))))
    (testing "category codes"
      (is (= "clouds" (category 803)))
      (is (= "extreme" (category 903)))
      (is (= "additional" (category 954)))
      (is (= "additional" (category 962))))))


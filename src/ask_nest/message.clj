(ns ask-nest.message
  (:require [clojure.string :as string :only [join]]))

(defn weather-string [forecast]
  (string/join ["It will be about "
                (:average-temp forecast)
                " degrees outside, with a "
                (:max-precip-prob forecast)
                "% chance of "
                (name (:precip-type forecast))
               "."]))

(defn nest-string [decision]
  (string/join ["Consider setting the thermostat to "
                (name (:nest decision))
                " and leaving the windows "
                (name (:windows decision))
                "."]))

(defn text-message [forecast decision]
  (string/join [(weather-string forecast)
                " "
                (nest-string decision)]))

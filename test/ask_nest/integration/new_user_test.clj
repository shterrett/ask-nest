(ns ask-nest.integration.new-user-test
  (:require [kerodon.core :refer :all]
            [kerodon.test :refer :all]
            [clojure.test :refer :all]
            [clojure.java.io :as io]
            [ask-nest.handler :as handler]))

(deftest user-record-created-on-signup
  (-> (session handler/app)
    (visit "/users/new")
    (fill-in "email" "newuser@example.com")
    (fill-in "password" "password")
    (fill-in "confirm password" "password")
    (fill-in "phone number" "123-456-7890")
    (fill-in "nest pin" "1234")
    (press "submit")
    (has (text? "Welcome newuser@example.com"))))

(ns ask-nest.integration.new-user-test
  (:require [kerodon.core :refer :all]
            [kerodon.test :refer :all]
            [clojure.java.io :as io]
            [clojure.test :refer :all]
            [ask-nest.handler :as handler]
            [ask-nest.test-helper :refer :all]))

(use-fixtures :once (fn [f] (migrate-test-db) (f)))
(use-fixtures :each (fn [f] (truncate-db) (f)))

(deftest user-record-created-on-signup
  (-> (session handler/app)
    (visit "/users/new")
    (fill-in "email" "newuser@example.com")
    (fill-in "password" "password")
    (fill-in "confirm password" "password")
    (fill-in "phone number" "123-456-7890")
    (fill-in "nest pin" "1234")
    (press "submit")
    (follow-redirect)
    (has (some-text? "Welcome newuser@example.com"))))

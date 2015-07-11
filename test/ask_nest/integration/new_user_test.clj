(ns ask-nest.integration.new-user-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer :all]
            [kerodon.core :refer :all]
            [kerodon.test :refer :all]
            [org.httpkit.client :as http]
            [org.httpkit.fake :as fake-http]
            [ask-nest.handler :as handler]
            [ask-nest.nest-data :as nest :only [token-url]]
            [ask-nest.test-helper :refer :all]))

(use-fixtures :once (fn [f] (migrate-test-db) (f)))
(use-fixtures :each (fn [f] (truncate-db) (f)))

(deftest user-record-created-on-signup
  (let [response (read-string (slurp "test/data/nest_access_token.clj"))]
    (fake-http/with-fake-http [nest/token-url response]
      (-> (session handler/app)
        (visit "/users/new")
        (fill-in "email" "newuser@example.com")
        (fill-in "password" "password")
        (fill-in "confirm password" "password")
        (fill-in "phone number" "123-456-7890")
        (fill-in "nest pin" "1234")
        (press "submit")
        (follow-redirect)
        (has (some-text? "Welcome newuser@example.com"))))))

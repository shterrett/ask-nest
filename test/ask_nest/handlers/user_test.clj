(ns ask-nest.handlers.user-test
  (:require [clojure.test :refer :all]
            [ask-nest.db.query :as db]
            [ask-nest.handlers.user :refer :all]
            [ask-nest.test-helper :refer :all]))

(use-fixtures :once (fn [f] (migrate-test-db) (f)))
(use-fixtures :each (fn [f] (truncate-db) (f)))

(deftest create-user-from-params
  (testing "validates presence of required parameters"
    (is (= false (create-user "" "1234567890" "1234")))
  (testing "creates user"
    (let [id (create-user "sterrett@example.com" "1234567890" "1234")]
      (is (= "sterrett@example.com" (:email (first (db/user-by-id db/db-spec id)))))))))

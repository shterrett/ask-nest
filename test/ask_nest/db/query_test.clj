(ns ask-nest.db.query-test
  (:require [clojure.test :refer :all]
            [clojure.string :as string :only [join]]
            [ask-nest.test-helper :refer :all]
            [ask-nest.db.query :refer :all]
            [environ.core :refer [env]]))

(use-fixtures :each (transaction-rollback db-spec))
(use-fixtures :once (fn [f] (migrate-test-db) (f)))

(deftest queries
  (testing "database queries"
    (testing "user creation and retrieval"
      (let [id (:id (first (create-user *txn* "sterrett@example.com" "123abc" "1234567890")))
            other-id (:id (first (create-user *txn* "houdini@example.com" "456def" "1234567890")))]
        (is (= {:id id :email "sterrett@example.com" :nest_api_key "123abc" :phone_number "1234567890"}
               (first (user-by-id *txn* id))))
        (is (= ["sterrett@example.com" "houdini@example.com"]
              (map :email (all-users *txn*))))))))

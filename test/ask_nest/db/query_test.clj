(ns ask-nest.db.query-test
  (:require [clojure.test :refer :all]
            [clojure.string :as string :only [join]]
            [ask-nest.test-helper :refer :all]
            [ask-nest.db.query :refer :all]
            [environ.core :refer [env]]))

(use-fixtures :each (transaction-rollback db-spec))

(deftest queries
  (testing "database queries"
    (testing "user creation and retrieval"
      (let [id (:id (first (create-user *txn* "sterrett" "123abc")))
            other-id (:id (first (create-user *txn* "houdini" "456def")))]
        (is (= {:id id :username "sterrett" :nest_api_key "123abc"}
               (first (user-by-id *txn* id))))
        (is (= ["sterrett" "houdini"]
              (map :username (all-users *txn*))))))))

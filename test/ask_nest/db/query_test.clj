(ns ask-nest.db.query-test
  (:require [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [clojure.string :as string :only [join]]
            [ask-nest.db.query :refer :all]
            [environ.core :refer [env]]))

(declare ^:dynamic *txn*)

(use-fixtures
  :each
  (fn [f]
    (jdbc/with-db-transaction
      [transaction db-spec]
      (jdbc/db-set-rollback-only! transaction)
      (binding [*txn* transaction] (f)))))

(deftest queries
  (testing "database queries"
    (testing "user creation and retrieval"
      (let [id (:id (first (create-user *txn* "sterrett" "123abc")))
            other-id (:id (first (create-user *txn* "houdini" "456def")))]
        (is (= {:id id :username "sterrett" :nest_api_key "123abc"}
               (first (user-by-id *txn* id))))
        (is (= ["sterrett" "houdini"]
              (map :username (all-users *txn*))))))))

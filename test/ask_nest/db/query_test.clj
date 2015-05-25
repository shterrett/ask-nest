(ns ask-nest.db.query-test
  (:require [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [clojure.string :as string :only [join]]
            [ask-nest.db.query :refer :all]
            [environ.core :refer [env]]))

(use-fixtures
  :each
  (fn [f]
    (jdbc/with-db-transaction
      [transaction db-spec]
      (jdbc/db-set-rollback-only! transaction)
      (f))))

(deftest queries
  (testing "database queries"
    (testing "user creation and retrieval"
      (let [id (:id (first (create-user db-spec "sterrett" "123abc")))
            other-id (:id (first (create-user db-spec "houdini" "456def")))]
        (is (= {:id id :username "sterrett" :nest_api_key "123abc"}
               (first (user-by-id db-spec id))))
        (is (= ["sterrett" "houdini"]
              (map :username (all-users db-spec))))))))

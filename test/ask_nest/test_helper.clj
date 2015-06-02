(ns ask-nest.test-helper
  (:require [clojure.java.jdbc :as jdbc]
            [ask-nest.db.migrations :as migrate]))

(declare ^:dynamic *txn*)

(defn migrate-test-db []
  (migrate/migrate))

(defn transaction-rollback [db]
  (fn [f]
    (jdbc/with-db-transaction
      [transaction db]
      (jdbc/db-set-rollback-only! transaction)
      (binding [*txn* transaction] (f)))))

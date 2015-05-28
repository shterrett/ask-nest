(ns ask-nest.test-helper
  (:require [clojure.java.jdbc :as jdbc]))

(declare ^:dynamic *txn*)

(defn transaction-rollback [db]
  (fn [f]
    (jdbc/with-db-transaction
      [transaction db]
      (jdbc/db-set-rollback-only! transaction)
      (binding [*txn* transaction] (f)))))

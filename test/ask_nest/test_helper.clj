(ns ask-nest.test-helper
  (:require [clojure.java.jdbc :as jdbc]
            [ask-nest.db.query :as db]
            [ask-nest.db.migrations :as migrate]))

(defn migrate-test-db []
  (migrate/migrate))

(defn truncate-db []
  (doseq [table (->> (db/all-tables db/db-spec)
                     (map #(:table_name %))
                     (remove #(= "ragtime_migrations" %)))]
    (db/truncate-table table)))

(ns ask-nest.db.migrations
  (:require [ragtime.jdbc :as jdbc]
            [environ.core :refer [env]]
            [ragtime.repl :as rag]))

(def config
  {:database (jdbc/sql-database {:connection-uri (:database (env :ragtime))})
   :migrations (jdbc/load-resources (:migrations (env :ragtime)))})

(defn migrate []
  (rag/migrate config))

(defn rollback []
  (rag/rollback config))

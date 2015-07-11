(ns ask-nest.db.query
  (:require [yesql.core :as y]
            [environ.core :refer [env]]
            [clojure.string :as string :only [join]]
            [clojure.java.jdbc :as j]))

(def db-spec {:classname "org.postgresql.Driver"
              :subprotocol "postgresql"
              :subname (str "//"
                            (string/join "/"
                                         [(env :db-host) (env :db)]))
              :user "stuart"})

(y/defqueries "ask_nest/db/queries/users.sql")
(y/defqueries "ask_nest/db/queries/test_helpers.sql")

(defn truncate-table [table-name]
  (j/execute! db-spec [(string/join " " ["TRUNCATE TABLE" table-name])]))

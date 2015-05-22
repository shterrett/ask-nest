(ns ask-nest.db.query
  (:require [yesql.core :as y]
            [environ.core :refer [env]]
            [clojure.string :as string :only [join]]))

(def db-spec {:classname "org.postgresql.Driver"
              :subprotocol "postgresql"
              :subname (str "//"
                            (string/join "/"
                                         ["localhost:5432" (env :db)]))
              :user "root"})

(defn query-path [file-name]
  (string/join "/"
               ["ask_nest" "db" "queries" (str file-name ".sql")]))

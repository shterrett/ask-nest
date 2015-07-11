(ns ask-nest.handlers.user
  (:require [ask-nest.db.query :as db]))

(defn create-user [email phone-number nest-api-key]
  (if (some (fn [param]
              (clojure.string/blank? param))
              [email phone-number nest-api-key])
    false
    (:id (first (db/create-user db/db-spec email nest-api-key phone-number)))))

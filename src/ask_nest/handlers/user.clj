(ns ask-nest.handlers.user
  (:require [environ.core :refer [env]]
            [ask-nest.db.query :as db]
            [ask-nest.nest-data :as nest]))

(defn nest-api-key [pin]
  (nest/get-token pin (env :nest-client-id) (env :nest-client-secret)))

(defn create-user [email phone-number nest-pin]
  (if (some (fn [param]
              (clojure.string/blank? param))
              [email phone-number nest-pin])
    false
    (:id (first (db/create-user db/db-spec
                                email
                                (nest-api-key nest-pin)
                                phone-number)))))

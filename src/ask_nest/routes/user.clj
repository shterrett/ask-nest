(ns ask-nest.routes.user
  (:require [ask-nest.data-shared :as data]
            [ask-nest.db.query :as db]
            [ask-nest.handlers.user :as handle]
            [ask-nest.views.user :as view]
            [compojure.core :refer [defroutes GET POST PUT]]
            [ring.util.http-response :refer [ok]]
            [ring.util.response :refer [redirect]]))

(defroutes user-routes
  (GET "/users/new" []
    (view/new))

  (POST "/users" [email
                  phone-number
                  api-key]
    (let [id (handle/create-user email phone-number api-key)]
      (if id
        (redirect (clojure.string/join "/" ["/users" id "edit"]))
        (view/new))))

  (GET "/users/:id/edit" [id]
    (view/edit (first (db/user-by-id db/db-spec
                                       (data/to-int id)))))

  (PUT "/users/:id" [] ))

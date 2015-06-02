(ns ask-nest.routes.user
  (:require [ask-nest.views.user :as view]
            [compojure.core :refer [defroutes GET POST PUT]]
            [ring.util.http-response :refer [ok]]))

(defroutes user-routes
  (GET "/users/new" []
    (view/new))
  (POST "/users" [] )
  (GET "/users/edit" [] )
  (PUT "/users/update" [] ))

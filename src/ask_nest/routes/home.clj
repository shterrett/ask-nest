(ns ask-nest.routes.home
  (:require [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :refer [ok]]))

(defn home-page []
  "<h1>Hello World</h1>")

(defroutes home-routes
  (GET "/" [] (home-page)))

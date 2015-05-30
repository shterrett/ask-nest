(ns ask-nest.routes.home
  (:require [ask-nest.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :refer [ok]]
            [clojure.java.io :as io]))

(defn home-page []
  "<h1>Hello World</h1>")

(defroutes home-routes
  (GET "/" [] (home-page)))

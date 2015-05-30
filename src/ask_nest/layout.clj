(ns ask-nest.layout
  (:require [selmer.parser :as parser]
            [selmer.filters :as filters]
            [ring.util.response :refer [content-type response]]
            [compojure.response :refer [Renderable]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
            [environ.core :refer [env]]))


(declare ^:dynamic *servlet-context*)
(parser/set-resource-path!  (clojure.java.io/resource "templates"))
(parser/add-tag! :csrf-field (fn [_ _] (anti-forgery-field)))

(defn render [template & [params]]
  (-> template
      (parser/render-file
        (assoc params
          :page template
          :dev (env :dev)
          :csrf-token *anti-forgery-token*
          :servlet-context *servlet-context*
          ))
      response
      (content-type "text/html; charset=utf-8")))

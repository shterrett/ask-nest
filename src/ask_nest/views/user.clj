(ns ask-nest.views.user
  (:require [ask-nest.layout :as layout]))

(defn new []
  (layout/render "templates/users/new.html" {}))

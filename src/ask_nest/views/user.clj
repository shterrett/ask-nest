(ns ask-nest.views.user
  (:require [ask-nest.layout :as layout]))

(defn random-token []
  (reduce str
          (repeatedly 8
                      (fn []
                        (rand-nth (map char (concat (range 65 91)
                                                    (range 97 123))))))))

(defn new []
  (layout/render "users/new.html" {:random-token (random-token)}))

(defn edit [user]
  (layout/render "users/edit.html" user))

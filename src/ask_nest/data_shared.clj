(ns ask-nest.data-shared
  (:require [clojure.data.json :as json]))

(defn to-json [response-body]
  (json/read-str response-body :key-fn keyword))

(defn remote-request [f args]
  (let [{:keys [status headers body error]} (apply f args)]
    (if error
      {:error true
       :message error}
      (into {:error false} (to-json body)))))

(defn to-int [string]
  (Integer. (re-find #"\d+" string)))

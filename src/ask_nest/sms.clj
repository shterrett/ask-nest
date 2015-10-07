(ns ask-nest.sms
  (:require [org.httpkit.client :as http]
            [ask-nest.data-shared :as data]
            [clojure.string :as string :only join]
            [environ.core :refer [env]]))

(def api-url "https://api.twilio.com/2010-04-01")

(defn sms-sender [account token from]
  (let [url (string/join "/" [api-url
                              "Accounts"
                              account
                              "Messages.json"])]
    (fn [to body]
      @(http/post url {:form-params {:To to
                                     :From from
                                     :Body body
                                    }
                      :basic-auth [account token]}))))

(defn get-sender []
  (sms-sender (env :twilio-account)
              (env :twilio-token)
              (env :twilio-from)))

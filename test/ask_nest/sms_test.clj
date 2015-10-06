(ns ask-nest.sms-test
  (:require [clojure.test :refer :all]
            [ask-nest.sms :refer :all]
            [org.httpkit.client :as http]
            [org.httpkit.fake :as fake-http]
            [environ.core :refer [env]]))

(deftest sms
  (deftest send-text-message
    (let [request-url "https://api.twilio.com/2010-04-01/Accounts/accountid/Messages.json"
          response (read-string (slurp "test/data/sms_response.clj"))
          account "accountid"
          token "secrettoken"]
      (fake-http/with-fake-http [request-url response]
        (testing "successful text message send"
          (let [sender (sms-sender account token)
                response (sender "123-456-7890" "+11235556789" "Hello World")]
            (is (= 201 (:status response)))))))))

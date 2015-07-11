(ns ask-nest.handlers.user-test
  (:require [clojure.test :refer :all]
            [org.httpkit.client :as http]
            [org.httpkit.fake :as fake-http]
            [ask-nest.db.query :as db]
            [ask-nest.handlers.user :refer :all]
            [ask-nest.nest-data :as nest :only [token-url]]
            [ask-nest.test-helper :refer :all]))

(use-fixtures :once (fn [f] (migrate-test-db) (f)))
(use-fixtures :each (fn [f] (truncate-db) (f)))

(deftest create-user-from-params
  (testing "validates presence of required parameters"
    (is (= false (create-user "" "1234567890" "1234"))))
  (testing "creates user"
    (let [response (read-string (slurp "test/data/nest_access_token.clj"))]
      (fake-http/with-fake-http [nest/token-url response]
        (let [id (create-user "sterrett@example.com" "1234567890" "1234")]
          (is (= "sterrett@example.com" (:email (first (db/user-by-id db/db-spec id)))))
          (is (= "c.edWORRyvbQFkF2femy7hgQtazu8kLQTaVkCk2bqv66vyGKBMCABBUwd6VQUG9ZZ7ynGCctFgEtY73vpWS5Dk0hTHNNZEmvywSfLnbilOOjTA7djloRK6nQnHoKVDuMDCUcFPT2VvnToeaFuh"
                 (:nest_api_key (first (db/user-by-id db/db-spec id))))))))))

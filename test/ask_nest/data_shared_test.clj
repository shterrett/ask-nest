(ns ask-nest.data-shared-test
  (:require [clojure.test :refer :all]
            [ask-nest.data-shared :refer :all]))

(deftest data-shared
  (deftest to-int-test
    (testing "casts string to integer"
      (is (= 5 (to-int "5"))))
    (testing "does not alter integer"
      (is (= 5 (to-int 5))))))


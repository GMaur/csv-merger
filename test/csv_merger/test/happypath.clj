(ns csv-merger.test.happypath
  (:use clojure.test)
  (:require [csv-merger.merge-csv :refer [merge-csv header rows]]))

(deftest happypath
  (let [merged (merge-csv "dev-resources/a1.csv" "dev-resources/a2.csv")
         rows- (rows merged)]
    (testing "merge of headers should contain both common and different headers"
      (= (header merged) (list "id" " common1" " common2" " different1_1" " different1_2" " different2_1" " different2_2")))


    (testing "merge of rows should contain both common and different columns"
      (= (nth rows- 0) (list "0a" " 1a" " 2a" " 1_1a" " 1_2a" "" ""))
      (= (nth rows- 2) (list "0a" " 1a" " 2a" "" "" " 1_1a" " 1_2a")))))
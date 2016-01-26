(ns csv-merger.merge-csv
  (:require [clojure.data.csv :as csv]
                [clojure.java.io :as io]))

(defn fetch-csv [filename]
  (with-open [in-file (io/reader filename)]
      (doall (csv/read-csv in-file))))

(defn header [csv-map]
  (first csv-map))

(defn rows [csv-map]
  (rest csv-map))

;(def a1 (fetch-csv "dev-resources/a1.csv"))
;(def a2 (fetch-csv "dev-resources/a2.csv"))

;(def h1 (into [] (flatten (header a1))))
;; ["id" " common1" " common2" " different1_1" " different1_2"]
;(def h2 (into [] (flatten (header a2)))))
;; ["id" " common1" " common2" " different2_1" " different2_2"]
;; http://stackoverflow.com/questions/12658341/how-can-i-merge-two-sequences-in-clojure

(defn merge-headers [header1 header2]
  (distinct (concat header1 header2)))
;(def allh (merge-headers h1 h2))
;; ("id"
;;  " common1"
;;  " common2"
;;  " different1_1"
;;  " different1_2"
;;  " different2_1"
;;  " different2_2")

(defn decorate-row [row header]
  (into {} (for [i (range (count header))] [(nth header i) (nth row i)])))

;(def dr1 (decorate-row r1 h1))
;; {" common1" " 1a",
;; " common2" " 2a",
;; " different1_1" " 1_1a",
;; " different1_2" " 1_2a",
;; "id" "0a"}


 ;;(map #(-> (get dr1 % "")) allh) ;;("0a" " 1a" " 2a" " 1_1a" " 1_2a" "" "")

 (defn select-cols [decorated-row all-headers]
   (map #(-> (get decorated-row % "")) all-headers))

; (def rows1 (rows a1))
 ;; (["0a" " 1a" " 2a" " 1_1a" " 1_2a"] ["0b" " 1b" " 2b" " 1_1b" " 1_2b"])

(defn order-rows [csv-map all-headers]
  (let [h1 (header csv-map)
         rows1 (rows csv-map)
         decorated-rows (map #(decorate-row % h1) rows1)]
   (map #(select-cols % all-headers) decorated-rows)))

(defn merge-csv- [csv-map1 csv-map2]
  (let [h1 (header csv-map1)
         h2 (header csv-map2)
         allh (merge-headers h1 h2)]
         (concat (list allh) (order-rows csv-map1 allh) (order-rows csv-map2 allh))))

(defn merge-csv [filename1 filename2]
  (let [file1 (fetch-csv filename1)
         file2 (fetch-csv filename2)]
         (merge-csv- file1 file2)))

(defn write [filename csv-map]
  (with-open [out-file (io/writer filename)]
    (csv/write-csv out-file csv-map)))

(defn merge-and-write-to [input1-filename input2-filename output-filename]
  (write output-filename (merge-csv input1-filename input2-filename)))
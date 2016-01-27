(ns csv-merger.merge-csv
  (:require [clojure.data.csv :as csv]
                [clojure.java.io :as io])
  (:gen-class))

(defn fetch-csv [filename]
  (with-open [in-file (io/reader filename)]
      (doall (csv/read-csv in-file))))

(defn header [csv-map]
  (first csv-map))

(defn rows [csv-map]
  (rest csv-map))

;; http://stackoverflow.com/questions/12658341/how-can-i-merge-two-sequences-in-clojure
(defn merge-headers [header1 header2]
  (distinct (concat header1 header2)))

(defn decorate-row [row header]
  (into {} (for [i (range (count header))] [(nth header i) (nth row i)])))

(defn select-cols [decorated-row all-headers]
  (map #(-> (get decorated-row % "")) all-headers))

(defn order-rows [csv-map all-headers]
  (let [headers (header csv-map)
         rows (rows csv-map)
         decorated-rows (map #(decorate-row % headers) rows)]
   (map #(select-cols % all-headers) decorated-rows)))

(defn merge-csv-contents [csv-map1 csv-map2]
  (let [header1 (header csv-map1)
         header2 (header csv-map2)
         all-headers (merge-headers header1 header2)]
         (concat 
            (list all-headers)
            (order-rows csv-map1 all-headers)
            (order-rows csv-map2 all-headers))))

(defn merge-csv [filename1 filename2]
  (let [file1 (fetch-csv filename1)
         file2 (fetch-csv filename2)]
         (merge-csv-contents file1 file2)))

(defn write [filename csv-map]
  (with-open [out-file (io/writer filename)]
    (csv/write-csv out-file csv-map)))

(defn merge-and-write-to [input1-filename input2-filename output-filename]
  (write output-filename (merge-csv input1-filename input2-filename)))

(defn -main [& args]
  (let [[output input1 input2] args]
  (merge-and-write-to input1 input2 output)))

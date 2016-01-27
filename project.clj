(defproject csv-merger "0.0.1-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]
                           [org.clojure/data.csv "0.1.3"]]
  :main csv-merger.merge-csv
  :aot [csv-merger.merge-csv]
  :profiles
    {:uberjar {:aot :all}})

# csv-merger

This library merges ``csv`` files.

For the time being, follows this strategy:

> union of headers, union of rows. add blank to non-overlapping columns

## Usage

```lisp
(use 'csv-merger.merge-csv)
csv-merger.merge-csv=> (fetch-csv "dev-resources/a1.csv")
;;(
;; ["id" " common1" " common2" " different1_1" " different1_2"]
;; ["0a" " 1a" " 2a" " 1_1a" " 1_2a"]
;; ["0b" " 1b" " 2b" " 1_1b" " 1_2b"]
;;)
csv-merger.merge-csv=> (fetch-csv "dev-resources/a2.csv")
;;(
;; ["id" " common1" " common2" " different2_1" " different2_2"]
;; ["0a" " 1a" " 2a" " 1_1a" " 1_2a"]
;; ["0c" " 1c" " 2c" " 1_1c" " 1_2c"]
;;)
(merge-csv "dev-resources/a1.csv" "dev-resources/a2.csv")
;; (
;;   ("id"  " common1"  " common2"  " different1_1"  " different1_2"  " different2_1"  " different2_2")
;;   ("0a" " 1a" " 2a" " 1_1a" " 1_2a" "" "")
;;   ("0b" " 1b" " 2b" " 1_1b" " 1_2b" "" "")
;;   ("0a" " 1a" " 2a" "" "" " 1_1a" " 1_2a")
;;   ("0c" " 1c" " 2c" "" "" " 1_1c" " 1_2c")
;; )
```
## Possible improvements

  * return a [file] seq, making it lazy
  * use a stream way of reading, so the program is not limited by memory resources (and more efficient)
  * use an interface closer to [xml-seq](https://clojuredocs.org/clojure.core/xml-seq)


## Ideas

  * Why ``with-open`` and not ``slurp``? Investigate

### References

  * [File Seq](https://clojuredocs.org/clojure.core/file-seq)

(ns rhymes.cmudict
  (:require [clojure.string :refer [split
                                    split-lines]]))

(defn- line->kv
  [line]
  (let [words (split line #"\s")]
    [(first words)
     (into [] (rest words))]))

(defn- lines->dict
  [lines]
  (reduce (fn [m line]
            (let [[k v] (line->kv line)]
              (assoc m k v)))
          {}
          lines))

(def d 
  (-> (slurp "cmudict/cmudict.dict")
      split-lines
      lines->dict))

(def vowels
  (-> (slurp "cmudict/cmudict.vowels")
      split-lines
      set))

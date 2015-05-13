(ns rhymes.cmudict
  (:require [clojure.string :refer [split
                                    split-lines]]))

(def f (slurp "cmudict/cmudict.dict"))

(def fs (split-lines f))

(defn line->kv
  [line]
  (let [words (split line #"\s")]
    [(first words)
     (into [] (rest words))]))

(defn fs->d
  [fs]
  (reduce (fn [m line]
            (let [[k v] (line->kv line)]
              (assoc m k v)))
          {}
          fs))

(def d (fs->d fs))

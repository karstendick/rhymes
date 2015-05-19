(ns rhymes.rhyme
  (:require [rhymes.cmudict :refer [d
                                    vowels]]))

(defn vowel?
  [phone]
  (contains? vowels phone))

(defn last-vowel-index
  [word]
  (let [phones (get d word)
        indexed-phones (map-indexed vector phones)
        indexed-vowels (filter #(vowel? (second %)) indexed-phones)]
    (first (last indexed-vowels))))

(defn alliteration?
  [w1 w2]
  (let [first-phone1 (get-in d [w1 0])
        first-phone2 (get-in d [w2 0])]
    (= first-phone1 first-phone2)))

(defn perfect-rhyme?
  [w1 w2]
  (let [p1 (get d w1)
        p2 (get d w2)
        idx1 (last-vowel-index w1)
        idx2 (last-vowel-index w2)]
    (= (subvec p1 idx1)
       (subvec p2 idx2))))

; a.k.a. assonance
(defn slant-rhyme?
  [w1 w2]
  (let [p1 (get d w1)
        p2 (get d w2)
        idx1 (last-vowel-index w1)
        idx2 (last-vowel-index w2)]
    (= (get p1 idx1)
       (get p2 idx2))))

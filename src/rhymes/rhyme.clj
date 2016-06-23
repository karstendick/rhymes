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

(defn last-syllable
  "Get all the phones from the last vowel to the end of the word."
  [word]
  (let [p (get d word)
        idx (last-vowel-index word)]
    (subvec p idx)))

(defn last-vowel
  [word]
  (let [p (get d word)
        idx (last-vowel-index word)]
    (get p idx)))

(defn first-phone
  [word]
  (get-in d [word 0]))

(defn apply-equals?
  [f x y]
  (= (f x)
     (f y)))

(defn alliteration?
  [w1 w2]
  (apply-equals? first-phone w1 w2))

(defn perfect-rhyme?
  [w1 w2]
  (apply-equals? last-syllable w1 w2))

; a.k.a. assonance
(defn slant-rhyme?
  [w1 w2]
  (apply-equals? last-vowel w1 w2))

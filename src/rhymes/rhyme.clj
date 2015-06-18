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

(defn alliteration?
  [w1 w2]
  (let [[first-phone1 first-phone2] (map first-phone [w1 w2])]
    (= first-phone1 first-phone2)))

(defn perfect-rhyme?
  [w1 w2]
  (let [[last-syllable1 last-syllable2] (map last-syllable [w1 w2])]
    (= last-syllable1 last-syllable2)))

; a.k.a. assonance
(defn slant-rhyme?
  [w1 w2]
  (let [[last-vowel1 last-vowel2] (map last-vowel [w1 w2])]
    (= last-vowel1 last-vowel2)))

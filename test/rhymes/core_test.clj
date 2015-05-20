(ns rhymes.core-test
  (:require [midje.sweet :refer :all]
            [rhymes.core :refer :all]))

(def lyrics-string
  (str "Some say the blacker the berry, the sweeter the juice \n"
       "I say the darker the flesh then the deeper the roots \n"
       "I give a holler to my sisters on welfare \n"
       "Tupac cares, if don't nobody else care"))
  
(facts "lyrics are converted into a vector of vector of phones"
     (let [phones (-> lyrics-string
                      clean-lyrics-string
                      lyrics-string->lyrics
                      lyrics->phones)]
       phones => vector?
       (first phones) => vector?
       (ffirst phones) => vector?
       (first (ffirst phones)) => string?
       (count phones) => 4 ; 4 lines of lyrics
       (count (first phones)) => 10 ; 10 words in the first line
       (count (ffirst phones)) => 3 ; 3 phones in the first word
       (get-in phones [0 0]) => ["S" "AH1" "M"]
       (get-in phones [0 0 0]) => "S"))

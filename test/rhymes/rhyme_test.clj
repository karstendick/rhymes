(ns rhymes.rhyme-test
  (:require [midje.sweet :refer :all]
            [rhymes.rhyme :refer :all]))

(facts "about `last-vowel-index`"
       (facts "works"
              (last-vowel-index "february") => 8
              (last-vowel-index "streets") => 3))

(facts "about `alliteration?`"
       (facts "works"
              (alliteration? "bat" "bag") => true
              (alliteration? "bat" "cat") => false)
       (fact "only compares first phoneme"
             (alliteration? "bat" "brat") => true))

(facts "about `perfect-rhyme?`"
       (facts "works"
              (perfect-rhyme? "cat" "hat") => true
              (perfect-rhyme? "cat" "car") => false
              (perfect-rhyme? "streets" "beets") => true
              (perfect-rhyme? "streets" "street") => false))

(facts "about `slant-rhyme?`"
       (facts "works"
              (slant-rhyme? "bred" "end") => true
              (slant-rhyme? "cat" "cap") => true
              (slant-rhyme? "cat" "coop") => false)
       (fact "true for all perfect rhymes, too"
             (slant-rhyme? "cat" "hat") => true))

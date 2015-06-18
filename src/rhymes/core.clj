(ns rhymes.core
  (:require [rhymes.cmudict :as cmudict :refer [d]]
            [rhymes.lyrics :refer [fetch-lyrics
                                   lyrics->phones]]
            [rhymes.rhyme])
  (:gen-class))



(def l (fetch-lyrics "Eminem" "My Name Is"))
(def windows (partition 4 1 l))
(def window (nth windows 11))

(defn -main
  [& args]
  (let [l (fetch-lyrics "Eminem" "My Name Is")
        windows (partition 4 1 l)
        window (nth windows 11)]
    (println l)))

(ns rhymes.core
  (:require [net.cgrand.enlive-html :as html]
            [clojure.string :as string :refer [split-lines]]
            [rhymes.cmudict :as cmudict :refer [d]]
            [rhymes.rhyme])
  (:gen-class))

(defn- fetch-url [url]
  (html/html-resource (java.net.URL. url)))

(defn- parse-html
  [html]
  (-> html
      (html/select [:div.lyricbox])
      (html/at [:script] nil)
      (html/at [:comment] nil)
      (html/at [:br] #(assoc % :content ["\n"]))))

(defn- fetch-lyrics-string
  [url]
  (->> url
       fetch-url
       parse-html
       (map html/text)
       first))

(defn clean-lyrics-string
  [s]
  (-> s
      ; remove everything between parens
      (string/replace #"\(.*\)" "")
      string/lower-case
      ; remove punctuation, except single quote
      (string/replace #"[^a-z '\n]" "")
      ; remove double spaces
      (string/replace #" +" " ")))

(defn string->words
  [s]
  (string/split s #"\s+"))

(defn lyrics-string->lyrics-words
  [s]
  (->> s
       split-lines
       (mapv string/trim)
       (mapv string->words)))

(defn fetch-lyrics
  [artist song]
  (let [formatted-artist (string/replace artist #" " "_")
        formatted-song (string/replace song #" " "_")
        url (format "http://lyrics.wikia.com/%s:%s" formatted-artist formatted-song)]
    (-> url
        fetch-lyrics-string
        clean-lyrics-string
        lyrics-string->lyrics-words)))

(defn- lyric-line->phones
  [lyric-line]
  (mapv (partial get d) lyric-line))

(defn lyrics->phones
  "lyrics is a vector of strings"
  [lyrics]
  (mapv lyric-line->phones lyrics))

; (fetch-lyrics "Eminem" "My Name Is")

(defn -main
  [& args]
  (let [l (fetch-lyrics "Eminem" "My Name Is")
        p (lyrics->phones l)]
    (println l)))

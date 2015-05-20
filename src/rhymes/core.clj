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
      ; remove punctuation
      (string/replace #"[^a-z '\n]" "")
      ; remove double spaces
      (string/replace #" +" " ")))

(defn lyrics-string->lyrics
  [s]
  (->> s
       split-lines
       (map string/trim)))

(defn fetch-lyrics
  [artist song]
  (let [formatted-artist (string/replace artist #" " "_")
        formatted-song (string/replace song #" " "_")
        url (format "http://lyrics.wikia.com/%s:%s" formatted-artist formatted-song)]
    (-> url
        fetch-lyrics-string
        clean-lyrics-string
        lyrics-string->lyrics)))

(defn- lyric-line->phones
  [lyric-line]
  (let [words (string/split lyric-line #"\s+")]
    (mapv (partial get d) words)))

(defn lyrics->phones
  "lyrics is a vector of strings"
  [lyrics]
  (mapv lyric-line->phones lyrics))

; (fetch-lyrics "Eminem" "My Name Is")

(defn -main
  [& args]
  (println "Hello, World!"))

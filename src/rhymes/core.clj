(ns rhymes.core
  (:require [net.cgrand.enlive-html :as html]
            [clojure.string :as string :refer [split-lines]]
            [rhymes.cmudict :as cmudict :refer [d]]
            [rhymes.rhyme])
  (:gen-class))

(defn fetch-url [url]
  (html/html-resource (java.net.URL. url)))

(defn parse-html
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

; TODO: lowercase all letters and remove punctuation

(defn fetch-lyrics
  [artist song]
  (let [formatted-artist (string/replace artist #" " "_")
        formatted-song (string/replace song #" " "_")
        url (format "http://lyrics.wikia.com/%s:%s" formatted-artist formatted-song)]
    (-> url
        fetch-lyrics-string
        (string/replace #"\(.*\)" "")
        split-lines)))

; (fetch-lyrics "Eminem" "My Name Is")

(defn -main
  [& args]
  (println "Hello, World!"))

(ns rhymes.core
  (:require [net.cgrand.enlive-html :as html]
            [clojure.string :refer [split-lines]]
            [rhymes.cmudict :as cmudict]))

(def url "http://lyrics.wikia.com/Eminem:My_Name_Is")

(defn fetch-url [url]
  (html/html-resource (java.net.URL. url)))

(def h (fetch-url url))

(def lyricbox (html/select h [:div.lyricbox]))

(def lyrics-text (-> lyricbox
                     (html/at [:script] nil)
                     (html/at [:comment] nil)
                     (html/at [:br] #(assoc % :content ["\n"]))))

(def lyrics (first (map html/text lyrics-text)))

(def lyrics-lines (split-lines lyrics))



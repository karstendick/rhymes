(ns rhymes.core
  (:require [net.cgrand.enlive-html :as html]))

(def url "http://www.azlyrics.com/lyrics/eminem/mynameis.html")

(defn fetch-url [url]
  (html/html-resource (java.net.URL. url)))
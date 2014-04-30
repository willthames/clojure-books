;; Print the titles of all books by Eva Corets
(ns books.core
  (:gen-class :main true))

(require '[clojure.xml :as xml])

(defn parsefile [s] (xml/parse (clojure.java.io/file s)))

;; returns all sub elements named <tag>
;; of an element with :tag, :attr, :content
(defn elt-get-tags-named [elt tag]
  (filter 
    (fn [x] (= (get x :tag) tag))
    (get elt :content)
  ))

;; returns true if an element has a tag named <tag>
;; with content (e.g. <tag>content</tag>
(defn elt-has-tag-with-content [elt tag content]
  (some 
     (fn [x] (= (get x :content) [content])) 
     (elt-get-tags-named elt tag))
  )

(def catalog (parsefile "resources/books.xml"))

(def books (get catalog :content))

;; eva is all the books with author Eva Corets
(def eva 
  (filter 
    (fn [x] (elt-has-tag-with-content x :author "Corets, Eva")) 
    books))

;; print the content of the first title element for each book in eva
(defn -main [& args]
  (println 
    (map 
      (fn[x] (get (first (elt-get-tags-named x :title)) :content)) 
      eva)))
  

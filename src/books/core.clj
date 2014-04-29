;; we want to the price of the books by author XYZ
(ns books.core)

(require '[clojure.xml :as xml])

(defn parsefile [s] (xml/parse (clojure.java.io/file s)))

;; returns all tags named <tag>
(defn elt-get-tags-named [elt tag]
  (filter (fn [x] (= (get elt :tag) tag))))

;; returns true if an element has a tag named <tag>
;; with content (e.g. <tag>content</tag>
(defn elt-has-tag-with-content [elt tag content]
  (some 
     (fn [x] (= (get x :content) content)) 
     (elt-get-tags-named elt tag))
  )

(def catalog (parsefile "resources/books.xml"))

(def books (get catalog :content))

(def eva 
  (filter 
    (fn [x] (elt-has-tag-with-content (get x :content) :author "Corets, Eva")) 
    books))


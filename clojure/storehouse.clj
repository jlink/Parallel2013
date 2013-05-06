(ns storehouse)

(def a (ref 1))

(defn pv [a b c d] (println (list a b c d)))

(add-watch a 'x pv)

(dosync
  (alter a inc)
  (alter a inc)
  (dosync (alter a dec))
  )




(ns clojure-tic-tac-toe.ai
  (:require [clojure-tic-tac-toe.core :refer [place-symbol opponent-symbol win? full?]]))

(declare negamax compare-scores get-best-score)

(defn computer-play [board]
    (if (every? number? board)
        (place-symbol board (rand-int 8) "O")
        (place-symbol board (get-best-score board "O") "O")))


(defn assign-scores [score index]
    {:score score :index index})


(defn negatize [score]
    (- (:score score)))


(defn filter-blank [board]
    (filter number? board))


(defn play-each-empty-square [board symbol depth]
    (map #(assign-scores
            (negatize (negamax
                        (assoc board % symbol)
                        (opponent-symbol symbol)
                        depth))
            %)
            (filter-blank board)))


(def memo-play-empty-squares (memoize play-each-empty-square))


(defn negamax [board symbol depth]
    (cond
        (win? board) {:score (+ -10 depth)}
        (full? board) {:score 0}
        :else (compare-scores
            (memo-play-empty-squares board symbol (inc depth)))))


(defn compare-scores
    [results]
    (apply max-key :score (flatten results)))


(defn get-best-score [board symbol]
    (:index (negamax board symbol 0)))

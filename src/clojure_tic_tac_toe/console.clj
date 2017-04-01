(ns clojure-tic-tac-toe.console
  (:require [clojure-tic-tac-toe.ai :refer [computer-play]]
            [clojure-tic-tac-toe.core :refer [place-symbol check-positions win? full?]]))

(declare take-turn)

(defn get-winner [board]
    (first (filter string? (check-positions board))))


(defn game-over? [board]
    (cond
        (win? board) (str (get-winner board) " wins")
        (full? board) "Cat's game"
        :else false))


(defn print-board [board]
   (println (str (board 0) " | " (board 1) " | " (board 2)))
   (println "----------")
   (println (str (board 3) " | " (board 4) " | " (board 5)))
   (println "----------")
   (println (str (board 6) " | " (board 7) " | " (board 8))))


(defn play-game [board human-player?]
   (print-board board)
   (if-let [game-over-message (game-over? board)]
       (println (str game-over-message))
       (do (println "Make your move")
         (if human-player?
         (take-turn board)
         (play-game (computer-play board) true)))))


(defn take-turn [board]
   (let [input (Integer. (read-line))]
       (if-let [temp-board (place-symbol board input "X")]
           (play-game temp-board false)
           (play-game board true))))


(def valid-range? (and (partial <= 0) (partial >= 9)))

(def check-for-number (partial re-matches #"([0-9])"))

(defn check-input [input]
   (if (check-for-number input)
       (if (valid-range? (Integer. input))
           (Integer. input))))

(defn get-input []
   (if-let [input (check-input (read-line))]
       input))

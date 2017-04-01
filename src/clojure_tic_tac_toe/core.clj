(ns clojure-tic-tac-toe.core)

(declare three-in-row)


(def new-board (into [] (take 9 (iterate inc 0))))


(defn get-positions [board]
    (concat (partition-all 3 board) ; rows
    (concat (apply map vector (partition 3 board)) ; columns
    (list
        (take-nth 4 board); (0 4 8) diagonal
        (take 3 (take-nth 2 (drop 2 board)) ; (2 4 6) diagonal
        )))))


(defn check-positions [board]
    (map #(three-in-row %) (get-positions board)))


(defn opponent-symbol [symbol]
    (if (= symbol "X") "O" "X"))


(defn can-place? [board square]
    (number? (get board square)))


(defn place-symbol [board square symbol]
    (if (can-place? board square)
        (assoc board square symbol)
        (println "Square is occupied")))


(defn win? [board]
    (true? (some string? (check-positions board))))


(defn three-in-row [row]
    (if (and (every? string? row) (every? #(= (first row) %) row))
        (first row)))


(defn full? [board]
    (not (some number? board)))


; (play-game new-board true)


(defn -main
    [& args]
    (println "Goodbye"))

(ns clojure-tic-tac-toe.core)

(declare three-in-row take-turn computer-play negamax compare-scores get-best-score)


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
          (computer-play board)))))


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


(defn computer-play [board]
    (if (every? number? board)
      (play-game (place-symbol board (rand-int 8) "O") true)
      (play-game (place-symbol board (get-best-score board "O") "O") true)))


(defn filter-blank [board]
    (filter number? board))


(defn assign-scores [score index]
    {:score score :index index})


(defn negatize [score]
    (- (:score score)))


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


; (play-game new-board true)


(defn -main
    [& args]
    (println "Goodbye"))

(ns clojure-tic-tac-toe.core)

(def new-board (into [] (take 9 (iterate inc 0))))

(defn can-place? [board square]
  (number? (get board square)))

    (defn place-symbol [board square symbol]
      (if (can-place? board square)
        (assoc board square symbol)
        (throw (Exception. "Square is occupied"))))

(defn grab-positions [board]
  (concat (partition-all 3 board)
  (list
    (take-nth 3 board) ; (0 3 6)
    (take-nth 3 (drop 1 board)) ;(1 4 7)
    (take-nth 3 (drop 2 board)) ;(2 5 8)
    (take-nth 4 board);(0 4 8)
    (take 3 (take-nth 2 (drop 2 board)) ;(2 4 6)
    ))))

(defn three-in-row [row symbol]
  (and (every? keyword? row) (every? #(= (first row) %) row)))

(defn win? [board]
  (true? (some true? (map #(three-in-row % symbol) (grab-positions board)))))

  (defn full? [board]
    (not (some number? board)))

  (defn filter-blank [board]
    (filter #(number? %) board))

  (defn opponent-symbol [symbol]
    (if (= symbol :X)
      :O
      :X))

  (declare memo-empty-squares)

  (defn compare-scores
    [results]
    (:index (apply max-key :score results)))

  (defn negamax [board symbol index depth]
    (let [new-board (place-symbol board index symbol)]
      (cond
        (win? new-board) {:score (- 10 depth) :index index}
        (full? new-board) {:score 0 :index index}
        :else (memo-empty-squares
                  new-board
                  (opponent-symbol symbol)
                  (inc depth)))))


  (defn play-each-empty-square [board symbol depth]
    (flatten (map #(negamax board symbol % depth) (filter-blank board))))

  (defn get-best-score [board symbol depth]
    (compare-scores (play-each-empty-square board symbol depth)))

  (def memo-empty-squares (memoize play-each-empty-square))

(defn -main
  [& args]
  (println "Hello World"))

(ns clojure-tic-tac-toe.core)


(declare three-in-row minimax assign-board-score)


(def new-board (into [] (take 9 (iterate inc 0))))


(defn grab-positions [board]
  (concat (partition-all 3 board) ; rows
  (concat (apply map vector (partition 3 board)) ; columns
  (list
    (take-nth 4 board); (0 4 8) diagonal
    (take 3 (take-nth 2 (drop 2 board)) ; (2 4 6) diagonal
    )))))


(defn filter-blank [board]
  (filter #(number? %) board))


(defn opponent-symbol [symbol]
  (if (= symbol :X)
    :O
    :X))


(defn can-place? [board square]
  (number? (get board square)))


(defn place-symbol [board square symbol]
  (if (can-place? board square)
    (assoc board square symbol)
    (throw (Exception. "Square is occupied"))))


(defn win? [board]
  (true? (some true? (map #(three-in-row % symbol) (grab-positions board)))))


(defn three-in-row [row symbol]
  (and (every? keyword? row) (every? #(= (first row) %) row)))


(defn full? [board]
  (not (some number? board)))


(defn play-each-empty-square [board symbol depth]
    (flatten (map #(minimax board symbol % depth) (filter-blank board))))


(def memo-empty-squares (memoize play-each-empty-square))


(defn minimax [board symbol index depth]
    (let [new-board (place-symbol board index symbol)]
      (cond
        (win? new-board) (assign-board-score symbol index depth)
        (full? new-board) {:score 0 :index index}
        :else (memo-empty-squares
                  new-board
                  (opponent-symbol symbol)
                  (inc depth)))))


(defn assign-board-score [symbol index depth]
  (if (= symbol :X)
    {:score (- depth 10) :index index}
    {:score (- 10 depth) :index index}))


(defn compare-scores
  [results]
  (:index (apply max-key :score results)))


(defn get-best-score [board symbol depth]
  (compare-scores (play-each-empty-square board symbol depth)))


(defn -main
  [& args]
  (println "Hello World"))

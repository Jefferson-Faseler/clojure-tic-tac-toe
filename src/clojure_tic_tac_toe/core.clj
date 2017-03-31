(ns clojure-tic-tac-toe.core)


(declare three-in-row computer-play minimize maximize minimax assign-board-score get-best-score)


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


(defn print-board [board]
  (println (str (board 0) " | " (board 1) " | " (board 2)))
  (println "----------")
  (println (str (board 3) " | " (board 4) " | " (board 5)))
  (println "----------")
  (println (str (board 6) " | " (board 7) " | " (board 8))))


(defn play-game [board]
  (print-board board)
  (if (or (win? board) (full? board))
    (println "Game over!")
    (do (println "Make your move human...")
    (let [input (Integer. (read-line))]
      (if-let [new-board (place-symbol board input :X)]
        (computer-play new-board)
        (play-game board))))))


(defn computer-play [board]
  (play-game (place-symbol board (get-best-score board :O) :O)))


; (defn play-each-empty-square [board symbol depth]
;     (if (nil? (first (filter-blank board)))
;       (println "HERE"))
;     (flatten (map #(minimax board symbol % depth) (filter-blank board))))


; (defn find-thing [best acc]
;   (loop [a best b (first acc)]
;     (recur (if (< a b) a b) (rest acc))))



; (def memo-empty-squares (memoize play-each-empty-square))

(defn compare-scores
  [results]
    (apply max-key :score (flatten results)))

    ; (defn minimax [board symbol depth]
    ;   (for [x (filter-blank board)]
    ;     (let [new-board (assoc board x symbol)]
    ;       (cond
    ;         (win? new-board) (- 10 depth)
    ;         (full? new-board) 0
    ;         :else (- (first (minimax (assoc board x symbol)
    ;                             (opponent-symbol symbol)
    ;                             (inc depth))))))


    (defn minimax [board symbol depth]
          (cond
            (win? board) {:score (+ -10 depth)}
            (full? board) {:score 0}
            :else (compare-scores (for [x (filter-blank board)]
                      (let [tempboard (assoc board x symbol)]
                      {:score (- (:score (minimax
                      tempboard
                      (opponent-symbol symbol)
                      (inc depth)
                      )))
                      :index x})
                      ))))
                  ; (compare-scores (map #(* -1 (:score %)) (memo-empty-squares
                  ;           new-board
                  ;           (opponent-symbol symbol)
                  ;           (inc depth)
                  ;           )))
                  ; ))


; (defn minimize [board symbol index depth]
;     (let [new-board (place-symbol board index symbol)]
;       (cond
;         (win? new-board) {:score (- depth 10) :index index}
;         (full? new-board) {:score 0 :index index}
;         :else (compare-scores (memo-empty-squares
;                   new-board
;                   (opponent-symbol symbol)
;                   (inc depth)
;                   true) false))))



; (defn assign-board-score [symbol index depth]
;   (if (= symbol :X)
;     {:score (- depth 10) :index index}
;     {:score (- 10 depth) :index index}))
;
;
; (defn get-best-score [board symbol depth]
;   (:index (compare-scores (play-each-empty-square board symbol depth true) true)))

(defn get-best-score [board symbol]
  (:index (minimax board symbol 0)))


(play-game new-board)


(defn -main
  [& args]
  (println "Hello World"))

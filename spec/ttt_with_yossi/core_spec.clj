(ns ttt-with-yossi.core-spec
  (:require [speclj.core :refer :all]
            [ttt-with-yossi.core :refer :all]))

(describe "Makes a board"

          (def new-board (into [] (take 9 (iterate inc 0))))

  (it "as a vector"
    (should= [0 1 2 3 4 5 6 7 8] new-board)))



(describe "Places a symbol on the board"

          (defn can-place? [board square]
            (number? (get board square)))

  (describe "should check if the square is available"
    (it "returns true if the symbol can be played"
      (should= true (can-place? new-board 5)))
    (it "returns false is the space is occupied"
      (should= false (can-place? [0 1 2 3 4 5 :X 7 8] 6))))

      (describe "should place the mark if the square is available"

              (defn place-symbol [board square symbol]
                (if (can-place? board square)
                  (assoc board square symbol)
                  (throw (Exception. "Square is occupied"))))

        (it "should return the board for a valid placement"
          (should= [0 1 2 3 :O 5 6 7 8] (place-symbol new-board 4 :O)))

        (it "should throw exception that square is already occupied"
          (should-throw Throwable "Square is occupied"
            (place-symbol [0 1 2 3 :O 5 6 7 8] 4 :X)))))

(describe "check for win"

          (defn grab-positions [board]
            (concat (partition-all 3 board)
            (list
              (take-nth 3 board) ; (0 3 6)
              (take-nth 3 (drop 1 board)) ;(1 4 7)
              (take-nth 3 (drop 2 board)) ;(2 5 8)
              (take-nth 4 board);(0 4 8)
              (take 3 (take-nth 2 (drop 2 board)) ;(2 4 6)
              ))))

  (it "creates a list of possible winning positions"
    (should= '((0 1 2) (3 4 5) (6 7 8) (0 3 6) (1 4 7) (2 5 8) (0 4 8) (2 4 6))
     (grab-positions new-board)))

  (describe "checks each set of positions for three in row"

          (defn three-in-row [row]
            (and (every? keyword? row) (every? #(= (first row) %) row)))

          (defn win? [board]
            (true? (some true? (map #(three-in-row %) (grab-positions board)))))

    (it "returns true for match"
      (should= true (win? [:X :X :X
                            3  4  5
                            6  7  8]))
      (should= true (win? [:X :O :O
                           :X :O :O
                           :X  7  8]))
      (should= true (win? [:O :X :X
                           :X :O :X
                           :X :X :O]))
      (should= true (win? [:O :O :X
                           :O :X :O
                           :X :O :O])))
    (it "returns false otherwise"
      (should= false (win? new-board))
      (should= false (win? [:X :O :X
                            :X :X :O
                             6 :O  8]))))

  (describe "checks for full board"

            (defn full? [board]
              (not (some number? board)))

    (it "returns true if board is full with no winner"
      (should= true (full? [:X :O :O
                            :O :X :X
                            :X :O :O])))

    (it "returns false if board is not full"
      (should= false (full? new-board)))))

(describe "unbeatable AI"

            (defn filter-blank [board]
              (filter #(number? %) board))

  (it "finds the indexes of each empty square"
    (should= [2 5 6 7] (filter-blank [:X :O  2
                                      :X :O  5
                                       6  7 :X])))

            (defn placement-score [board index depth]
              (cond
                (win? board) {:score (- 10 depth) :index index}
                (full? board) {:score 0 :index index}))

  (it "returns a score based on depth and returns an index for won board"
    (should= {:score 9 :index 6}
            (placement-score [:X :O  2
                              :X :O  5
                              :X  7 :X] 6 1)))

  (it "returns a score of 0 and index for draw game"
    (should= {:score 0 :index 8} (placement-score [:X :O :O
                                                   :O :X :X
                                                   :X :O :O] 8 3)))

  (it "returns nil if board is not won or full"
    (should= nil (placement-score new-board 0 0))))



(run-specs)

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
            (place-symbol [0 1 2 3 :O 5 6 7 8] 4 :X))))

(describe "grab positions"

          (defn grab-positions [board]
            (concat (partition-all 3 board)
            (list
              (take-nth 3 board) ; (0 3 6)
              (take-nth 3 (drop 1 board)) ;(1 4 7)
              (take-nth 3 (drop 2 board)) ;(2 5 8)
              (take-nth 4 board);(0 4 8)
              (take 3 (take-nth 2 (drop 2 board)))))))

  (it "creates a list of possible winning positions"
    (should= '((0 1 2) (3 4 5) (6 7 8) (0 3 6) (1 4 7) (2 5 8) (0 4 8) (2 4 6))
     (grab-positions new-board))))

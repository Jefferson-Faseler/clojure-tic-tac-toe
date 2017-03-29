(ns clojure-tic-tac-toe.core-spec
  (:require [speclj.core :refer :all]
            [clojure-tic-tac-toe.core :refer :all]))

(describe "Makes a board"

  (it "as a vector"
    (should= [0 1 2 3 4 5 6 7 8] new-board)))



(describe "Places a symbol on the board"


  (describe "should check if the square is available"
    (it "returns true if the symbol can be played"
      (should= true (can-place? new-board 5)))
    (it "returns false is the space is occupied"
      (should= false (can-place? [0 1 2 3 4 5 :X 7 8] 6))))

      (describe "should place the mark if the square is available"


        (it "should return the board for a valid placement"
          (should= [0 1 2 3 :O 5 6 7 8] (place-symbol new-board 4 :O)))

        (it "should throw exception that square is already occupied"
          (should-throw Throwable "Square is occupied"
            (place-symbol [0 1 2 3 :O 5 6 7 8] 4 :X)))))

(describe "check for win"


  (it "creates a list of possible winning positions"
    (should= '((0 1 2) (3 4 5) (6 7 8) (0 3 6) (1 4 7) (2 5 8) (0 4 8) (2 4 6))
     (grab-positions new-board)))

  ; (describe "checks each set of positions for three in row"
  ;
  ;
  ;   (it "returns true for match"
  ;     (should= true (win? [:X :X :X
  ;                           3  4  5
  ;                           6  7  8] :X))
  ;     (should= true (win? [:X :O :O
  ;                          :X :O :O
  ;                          :X  7  8] :X))
  ;     (should= true (win? [:O :X :X
  ;                          :X :O :X
  ;                          :X :X :O] :O))
  ;     (should= true (win? [:O :O :X
  ;                          :O :X :O
  ;                          :X :O :O] :X)))
  ;   (it "returns false otherwise"
  ;     (should= false (win? new-board :X))
  ;     (should= false (win? [:O :O :X
  ;                          :O :X :O
  ;                          :X :O :O] :O))
  ;     (should= false (win? [:X :O :X
  ;                           :X :X :O
  ;                            6 :O  8] :O))))

  (describe "checks for full board"


    (it "returns true if board is full with no winner"
      (should= true (full? [:X :O :O
                            :O :X :X
                            :X :O :O])))

    (it "returns false if board is not full"
      (should= false (full? new-board)))))

(describe "unbeatable AI"


  (it "finds the indexes of each empty square"
    (should= [2 5 6 7] (filter-blank [:X :O  2
                                      :X :O  5
                                       6  7 :X])))



  (describe "negamax"
    (describe "returns placement score for instant win move"

      (it "wins with only one possible move"
        (should= 4 (get-best-score [:X :O :X
                                    :X  4 :O
                                     6 :O :X] :O 0)))

      (it "one move to win"
        (should= 6 (get-best-score [:X :O  2
                                    :X :O  5
                                     6  7 :X] :X 0))))

    (describe "new board"
      (it "returns index of the best move"
        (should= 2 (get-best-score new-board :X 0))))


  ) ;; negamax
  ) ;; unbeatable AI



(run-specs)

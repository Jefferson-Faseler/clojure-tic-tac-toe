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

  (describe "checks each set of positions for three in row"


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
    (describe "always wins or draws"
      (it "wins with only one possible move"
        (should= 4 (get-best-score [:X :O :X
                                    :X  4 :O
                                     6 :O :X] :O)))

       (it "chooses double trap over double block"
         (should= 4 (get-best-score [:X :O :X
                                     :O  4  5
                                      6  7  8] :O)))

      (it "one move to win and not block"
        (should= 7 (get-best-score [:X :O  2
                                    :X :O  5
                                     6  7 :X] :O)))

      (it "will block when it needs to"
        (should= 5 (get-best-score [0  1 :O
                                   :X  4  5
                                    6 :X :O] :O)))

      (it "draw when it has to"
        (should= 1 (get-best-score [:X  1 :O
                                    :O :O :X
                                    :X :X :O] :X)))))

    ; (describe "new board"
    ;   (it "returns index of the best move"
    ;     (should= 8 (get-best-score new-board :X))))


  ) ;; unbeatable AI



(run-specs)

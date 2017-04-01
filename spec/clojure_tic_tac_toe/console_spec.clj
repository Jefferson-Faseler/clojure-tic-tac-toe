(ns clojure-tic-tac-toe.console-spec
  (:require [speclj.core :refer :all]
            [clojure-tic-tac-toe.console :refer :all]
            [clojure-tic-tac-toe.core :refer [new-board]]))


(describe "User interface"
  (describe "User input"
    (describe "checks if input is a number and is within range"
      (it "returns the input if a number within range"
        (should= 5 (check-input "5")))

      (it "returns logical false if the input is not a number"
        (should-be-nil (check-input "world")))

      (it "returns logical false if the input is a number, but out of range"
        (should-be-nil (check-input "101"))))

    (describe "gets the user input and runs it through checks"
      (it "returns a number if it meets all checks"
        (should= 7 (with-in-str "7" (get-input))))

      (it "returns logical false if it does not meet all checks"
        (should-be-nil (with-in-str "hello world" (get-input))))))

  (describe "Retrieving winning symbol"
    (it "returns winning symbol if win"
      (should= "X" (get-winner ["X" "X" "X"
                                 3   4   5
                                 6   7   8])))

    (it "returns nil if no win"
      (should-be-nil (get-winner new-board))))

  (describe "Checks if the game is over"
    (it "returns winning symbol if there is a winner"
      (should= "O wins" (game-over? ["O" "X" "X"
                                     "X" "O" "X"
                                     "X" "X" "O"])))

    (it "returns draw message if the board is full with no winner"
      (should= "Cat's game" (game-over? ["X" "O" "O"
                                         "O" "X" "X"
                                         "X" "O" "O"])))

    (it "returns false if the game is not won or a draw"
      (should= false (game-over? new-board)))))

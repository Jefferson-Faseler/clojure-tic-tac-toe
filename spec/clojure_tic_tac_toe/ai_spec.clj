(ns clojure-tic-tac-toe.ai-spec
  (:require [speclj.core :refer :all]
            [clojure-tic-tac-toe.ai :refer :all]))

(describe "unbeatable AI"


(it "finds the indexes of each empty square"
  (should= [2 5 6 7] (filter-blank ["X" "O"  2
                                    "X" "O"  5
                                     6   7  "X"])))

(describe "negamax"
  (describe "always wins or draws"
    (it "wins with only one possible move"
      (should= 4 (get-best-score ["X" "O" "X"
                                  "X"  4  "O"
                                   6  "O" "X"] "O")))

     (it "chooses double trap over double block"
       (should= 4 (get-best-score ["X" "O" "X"
                                   "O"  4   5
                                    6   7   8] "O")))

    (it "one move to win and not block"
      (should= 7 (get-best-score ["X" "O"  2
                                  "X" "O"  5
                                   6   7  "X"] "O")))

    (it "will block when it needs to"
      (should= 5 (get-best-score [0   1  "O"
                                 "X"  4   5
                                  6  "X" "O"] "O")))

    (it "draw when it has to"
      (should= 1 (get-best-score ["X"  1 "O"
                                  "O" "O" "X"
                                  "X" "X" "O"] "X"))))))

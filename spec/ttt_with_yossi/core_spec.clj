(ns ttt-with-yossi.core-spec
  (:require [speclj.core :refer :all]
            [ttt-with-yossi.core :refer :all]))

(describe "Makes a board"
  (it "as a vector"
    (should= [0 1 2 3 4 5 6 7 8] (range 0 9))))

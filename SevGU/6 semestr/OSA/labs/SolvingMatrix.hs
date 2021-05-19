module SolvingMatrix where

import Data.List (elemIndex)

type Raw = [Double]
type Matrix = [Raw]
type Task = (Raw, [Matrix])

multiply :: Raw -> Raw -> Raw
multiply = zipWith (*)

normalize :: Raw -> Raw
normalize raw = map (/ sum raw) raw

calcLevel :: Raw -> Matrix -> Raw
calcLevel raw matrix = normalize level
  where level = map (sum . multiply raw) matrix

calcInfluence :: Task -> (Maybe Int, Double)
calcInfluence (start, matrix) = (index, most)
  where raw = foldl calcLevel start matrix
        most = maximum raw
        index = (+ 1) <$> elemIndex most raw


main :: IO ()
main = print $ calcInfluence input


input :: Task
input = (a, [ab, bc, cd])

a :: Raw
a = [0.4, 0.2, 0.3, 0.1]

ab :: Matrix
ab = [ [ 0.4, 0.2, 0.4, 0   ]
     , [ 0.4, 0.4, 0,   0.2 ]
     , [ 0,   0,   1.0, 0   ]
     , [ 0.3, 0,   0,   0.7 ]
     , [ 0.4, 0.2, 0.1, 0.3 ]
     ]

bc :: Matrix
bc = [ [ 0.4, 0,   0.2, 0.4, 0   ]
     , [ 0,   0.4, 0.2, 0.3, 0.1 ]
     , [ 0.5, 0,   0,   0.1, 0.4 ]
     , [ 0.2, 0.7, 0.1, 0,   0   ]
     ]

cd :: Matrix
cd = [ [ 0,   0.3, 0,   0.7 ]
     , [ 0.6, 0.3, 0.1, 0   ]
     , [ 0.1, 0.2, 0.3, 0.4 ]
     ]

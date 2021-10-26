module TMatrix
  ( tMatrixTest1
  , tMatrixTest2
  ) where

import           Matrix (Matrix, rotate)

input :: Matrix Int
input =
  [ [ 1, 2, 3, 4 ]
  , [ 5, 6, 7, 8 ]
  , [ 9, 0, 1, 2 ]
  , [ 3, 4, 5, 6 ]
  ]

output :: Matrix Int
output =
  [ [ 6, 5, 4, 3 ]
  , [ 2, 1, 0, 9 ]
  , [ 8, 7, 6, 5 ]
  , [ 4, 3, 2, 1 ]
  ]

runTest :: (Show a, Eq a) => Matrix a -> Matrix a -> IO ()
runTest test expected = do
  let content = "Expected:\n" ++ show expected ++ "\nGot:\n" ++ show test
  if test == expected
  then putStrLn "Passed"
  else putStrLn "Failed"
  writeFile "log.txt" content

tMatrixTest1, tMatrixTest2 :: IO ()
tMatrixTest1 = runTest (rotate 1 input) output
tMatrixTest2 = runTest (rotate 2 input) input

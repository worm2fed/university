module TMatrix3a
  ( tMatrix3aTest1
  ) where

import           AChecker (check3a)
import           Matrix   (Matrix, rotate)

input :: Matrix Char
input =
  [ "aaa"
  , "bbb"
  , "ccc"
  ]

output :: Matrix Char
output =
  [ "ccc"
  , "bbb"
  , "aaa"
  ]

runTest :: Matrix Char -> Matrix Char -> IO ()
runTest test expected = do
  let content = "Expected:\n" ++ show expected ++ "\nGot:\n" ++ show test
  if test == expected
  then putStrLn "Passed"
  else putStrLn "Failed"
  writeFile "log.txt" content

tMatrix3aTest1 :: IO ()
tMatrix3aTest1 = runTest test output
  where test = if check3a $ head input then rotate 1 input else input

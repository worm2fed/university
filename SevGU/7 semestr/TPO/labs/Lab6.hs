module Main where

myConcat :: [[a]] -> [a]
-- myConcat = {-# SCC "concat" #-} foldl (++) []
myConcat = {-# SCC "concat" #-} foldr (++) []

test :: Double
test = last . myConcat $ map return [1 .. 1e5]

main :: IO ()
main = print test

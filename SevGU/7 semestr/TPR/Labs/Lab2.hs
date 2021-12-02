import           Data.List (intersect)

type Row a = [a]
type Matrix a = [Row a]

type RowI a = [(Int, a)]
type MatrixI a = [(Int, RowI a)]

input :: Matrix Bool
input =
  [ [ False, False, False, False, False, False, False ]
  , [ True,  False, True,  False, False, False, False ]
  , [ False, False, False, True,  False, False, True  ]
  , [ False, True,  False, False, True,  True,  False ]
  , [ False, True,  False, False, False, False, False ]
  , [ True,  False, False, True,  False, False, False ]
  , [ False, False, True,  False, False, True,  False ]
  ]

getColumn :: Matrix a -> Int -> Row a
getColumn matrix n = map (!! (n - 1)) matrix

getRow :: Matrix a -> Int -> Row a
getRow matrix n = matrix !! (n - 1)

calcX :: (Matrix Bool -> Int -> Row Bool) -> Matrix Bool -> Int -> [Int]
calcX getter matrix n
  | n < 1 || n > length matrix = []
  | otherwise = foldr (\(i, e) res -> if e then i : res else res) [] elements
  where
    elements = zip [1..] . take n . getter matrix $ n

calcXplus :: Matrix Bool -> Int -> [Int]
calcXplus = calcX getColumn

calcXminus :: Matrix Bool -> Int -> [Int]
calcXminus = calcX getRow

calcXplusAndMinus :: Matrix Bool -> Int -> [Int]
calcXplusAndMinus matrix n = calcXplus matrix n `intersect` calcXminus matrix n

checkXplusEmpty :: Matrix Bool -> Int -> Bool
checkXplusEmpty matrix = null . calcXplus matrix

checkXminusEmpty :: Matrix Bool -> Int -> Bool
checkXminusEmpty matrix = null . calcXminus matrix

checkXplusAndXminusEmpty :: Matrix Bool -> Int -> Bool
checkXplusAndXminusEmpty matrix = null . calcXplusAndMinus matrix

calcU :: Matrix Bool -> Int -> Double
calcU matrix n
  | plusEmpty && not minusEmpty = realToFrac 1
  | minusEmpty && not plusEmpty = realToFrac $ x' - 1
  | not plusEmpty && not minusEmpty && bothEmpty = realToFrac (x' + x'') / 2
  | not plusEmpty && not minusEmpty && not bothEmpty = anyX
  | otherwise = 0
  where
    plusEmpty = checkXplusEmpty matrix n
    minusEmpty = checkXminusEmpty matrix n
    bothEmpty = checkXplusAndXminusEmpty matrix n

    x' = calcU matrix . minimum $ calcXplus matrix n
    x'' = calcU matrix . maximum $ calcXminus matrix n
    anyX = calcU matrix . head $ calcXplusAndMinus matrix n

findSolution :: Matrix Bool -> [(Double, Int)]
findSolution matrix = zip (map (calcU matrix) [1 .. length matrix]) [1..]

findBestSolution :: Matrix Bool -> (Double, Int)
findBestSolution = maximum . findSolution

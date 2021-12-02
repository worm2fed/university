import           Control.Applicative (ZipList (..))
import           Data.List           (delete)

type Row a = [a]
type Matrix a = [Row a]

input :: Matrix Int
input =
  [ [ 3, 2 ]
  , [ 4, 5 ]
  , [ 5, 3 ]
  , [ 8, 3 ]
  , [ 6, 2 ]
  , [ 3, 8 ]
  , [ 6, 4 ]
  , [ 2, 5 ]
  , [ 6, 4 ]
  , [ 2, 5 ]
  ]

calcP :: Matrix Int -> Row Int
calcP matrix = calculate (zip [1..] matrix) [1 .. length matrix]
  where
    check a b = all (== True) $ zipWith (>=) a b

    calculate [] p = p
    calculate ((i, m):mx) p = calculate mx $ foldr (\(j, row) result ->
        if      check m row then delete j result
        else if check row m then delete i result
        else result
      ) p mx

type Point = (Int, Int)

transpose :: Matrix Int -> Matrix Int
transpose = getZipList . traverse ZipList

calcMax :: Matrix Int -> Point
calcMax matrix = calculate $ transpose matrix
  where
    calculate (f1:f2:_) = (maximum f1, maximum f2)
    calculate _         = (0, 0)

calcR :: Point -> Point -> Double
calcR (p1_max, p2_max) (p1, p2) = sqrt . fromIntegral $
  (p1_max - p1) ^ 2 + (p2_max - p2) ^ 2

row2point :: Row Int -> Point
row2point (f1:f2:_) = (f1, f2)
row2point _         = (0, 0)

findSolution :: Matrix Int -> (Double, Int)
findSolution matrix = minimum $ zip rs [1 ..]
  where
    rs = map (calcR ideal . row2point) matrix
    ideal = calcMax matrix

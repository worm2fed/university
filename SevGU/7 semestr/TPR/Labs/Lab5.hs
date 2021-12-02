import           Data.List (delete, nub)

type Row a = [a]
type Matrix a = [Row a]

input :: Matrix Int
input =
  [ [ 3, 5, 5, 4, 4 ]
  , [ 4, 4, 4, 5, 4 ]
  , [ 5, 4, 3, 3, 5 ]
  , [ 3, 5, 3, 5, 3 ]
  , [ 4, 2, 4, 5, 5 ]
  , [ 3, 5, 3, 5, 3 ]
  , [ 5, 3, 4, 3, 4 ]
  , [ 4, 5, 3, 4, 3 ]
  ]

inputA1 :: Matrix Bool
inputA1 =
  [ [ False, True,  False, False, False ]
  , [ False, False, False, False, False ]
  , [ False, False, False, False, False ]
  , [ False, False, False, False, True  ]
  , [ False, False, False, False, False ]
  ]

inputA2 :: Matrix Bool
inputA2 =
  [ [ False, False, False, False, False ]
  , [ False, False, True,  False, False ]
  , [ False, False, False, True,  False ]
  , [ False, False, False, False, False ]
  , [ False, False, False, False, False ]
  ]

get :: Row a -> Int -> a
get s i = s !! (i - 1)

replace :: Int -> a -> [a] -> [a]
replace _ _ []       = []
replace 1 val (_:xs) = val : xs
replace n val (x:xs) = x : replace (n - 1) val xs

swap :: (Int, Int) -> Row a -> Row a
swap (i, j) x = replace j (get x i) $ replace i (get x j) x

checkDominated :: Ord a => Row a -> Row a -> Bool
checkDominated a b = all (== True) (zipWith (>=) a b) && or (zipWith (>) a b)

cleanDominated :: Matrix Int -> Row Int
cleanDominated matrix = calculate (zip [1..] matrix) [1 .. length matrix]
  where
    calculate [] p = p
    calculate ((i, m):ms) p = calculate ms $
      foldr (\(l, row) result ->
        if checkDominated m row
        then delete l result
        else result
      ) p ms

getOmega :: Matrix Bool -> Row (Int, Int)
getOmega = foldr (\(i, row) result ->
    if or row
    then filter snd (zip [1..] row) >>= \j -> (i, fst j) : result
    else result
  ) [] . zip [1..]

cleanPreferable :: Matrix Int -> Matrix Bool -> Row Int
cleanPreferable matrix a1 = calculate (getOmega a1) $ cleanDominated matrix
  where
    matrixI = zip [1..] matrix

    calculate [] p = p
    calculate (s@(j, _):ss) p = calculate ss $
      foldr (\i result ->
        let x = get matrix i; x' = swap s x
        in if get x j > get x' j
           then foldr (delete . fst) result $
              filter (\(i', y) -> i /= i' && checkDominated x' y) matrixI
           else result
      ) p p

findSolution :: Matrix Int -> Matrix Bool -> Matrix Bool -> Row Int
findSolution matrix a1 a2 = calculate (getOmega a2) $ cleanPreferable matrix a1
  where
    matrixI = zip [1..] matrix

    calculate [] p = p
    calculate (s@(j, _):ss) p = calculate ss $
      foldr (\i result ->
        let x = swap s $ get matrix i
        in foldr (delete . fst) result $
            filter (\(i', y) -> i /= i' && checkDominated x y) matrixI
      ) p p

import           Control.Monad (guard)

type Row a = [a]
type Matrix a = [Row a]

type RowI a = [(Int, a)]
type MatrixI a = [(Int, RowI a)]

addIndexes :: Matrix a -> MatrixI a
addIndexes matrix = zip [1..] $ zip [1..] <$> matrix

withRelation :: MatrixI Bool -> RowI Int
withRelation matrix = do
  (i, row) <- matrix
  (j, column) <- row
  guard column
  pure (i, j)

replace :: Int -> a -> [a] -> [a]
replace _ _ []       = []
replace 0 val (_:xs) = val : xs
replace n val (x:xs) = x : replace (n - 1) val xs

get :: Row a -> Int -> a
get s i = s !! (i - 1)

get2 :: Matrix a -> Int -> Int -> a
get2 s i = get (get s i)

getMaxR :: Matrix Bool -> Row Bool
getMaxR matrix = foldr selector maxR . withRelation . addIndexes $ matrix
  where
    maxR = replicate (length matrix) True

    selector (i, j) res
      | not $ get2 matrix j i = replace (j - 1) False res
      | otherwise = if not $ get res i then replace (j - 1) False res else res

-- >>> getMaxR input
-- [True,True,False,True,False,True]
input :: Matrix Bool
input =
  [ [ False, True,  False, False, False, False ]
  , [ True,  False, True,  True,  False, False ]
  , [ False, False, False, False, False, False ]
  , [ False, True,  False, False, True,  False ]
  , [ False, False, False, False, False, False ]
  , [ False, False, False, False, True,  False ]
  ]

-- >>> getMaxR input7a
-- [True,True,False,False,True]
input7a :: Matrix Bool
input7a =
  [ [ False, False, False, True,  False ]
  , [ False, False, True,  False, False ]
  , [ False, False, False, True,  True  ]
  , [ False, False, True,  False, False ]
  , [ False, False, True,  False, False ]
  ]

-- >>> getMaxR input7b
-- [True,True,False,False,False]
input7b :: Matrix Bool
input7b =
  [ [ False, True,  False, True,  False ]
  , [ True,  False, True,  False, False ]
  , [ False, False, False, True,  True  ]
  , [ False, False, True,  False, False ]
  , [ False, False, False, False, False ]
  ]

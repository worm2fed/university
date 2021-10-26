-- Задача 1.
-- Дана квадратная матрица.
-- Выполнить поворот этой матрицы на 180×k градусов, где k − целое число.
type Row a = [a]
type Matrix a = [Row a]

rotate :: Int -> Matrix a -> Matrix a
rotate i m
  | i > 0 = rotate (i - 1) (reverse $ map reverse m)
  | otherwise = m

-- Задача 2.
-- Дана строка.
-- Выяснить, верно ли, что в строке имеются три идущих подряд буквы 'а'.
check3a :: String -> Bool
check3a (x1:x2:x3:_) = all (== 'a') [x1, x2, x3]
check3a _            = False

-- Задача 3.
-- Программа, которая подсчитывает количество непустых строк в текстовом файле.
countLines :: FilePath -> IO Int
countLines fileName = do
  content <- lines <$> readFile fileName
  return . length $ filter (not . null) content

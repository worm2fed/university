import numpy as np

matrix = np.loadtxt('equations.in', delimiter=' ', skiprows=1)

A = matrix[:,:(matrix.shape[1] - 1)]
b = matrix[:,(matrix.shape[1] - 1)]

result = str(np.linalg.solve(A, b))
result.replace(',', '')

file = open('equations.out', 'w')
file.write(result[1:-1])
file.close()
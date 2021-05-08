import numpy as np

file = open('equations.in', 'r')
line = file.readline()
size = int(line)

line = file.readline()
coef_a = []
coef_b = []

while line:
	tmp = [float(item) for item in line.split()]
	coef_b.append(tmp.pop())
	coef_a.append(tmp)

	line = file.readline()

file.close()
a = np.array(coef_a)
b = np.array(coef_b)

result = str(np.linalg.solve(a, b))
result.replace(',', '')

file = open('equations.out', 'w')
file.write(result[1:-1])
file.close()
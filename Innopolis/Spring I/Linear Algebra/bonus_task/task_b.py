import numpy as np

with open('data.in', 'r') as file:
	line = file.readline()

N, S = line.split()
x, y = np.loadtxt('data.in', skiprows=1, delimiter=' ', unpack=True)

i = 1
while True:
	A = np.polyfit(x, y, i)
	y_ = np.polyval(A, x)

	if float((1 / (int(N) - 1)) * np.sum(np.power(np.subtract(y_, y), 2))) <= float(S):
		break

	i += 1

with open('data.out', 'w') as file:
	file.write(str(A[::-1])[1:-1])
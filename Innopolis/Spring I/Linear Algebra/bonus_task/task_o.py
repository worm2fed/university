file = open('plus.in', 'r')
numbers = [int(s) for s in file.read().split() if s.lstrip('-').isdigit()]
file.close()

file = open('plus.out', 'w')
file.write(str(int(numbers[0] + numbers[1])))
file.close()
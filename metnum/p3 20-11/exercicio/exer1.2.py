num =354

vetor = []
while(True):
    x = num % 2
    num = int(num / 2)
    vetor.append(x)

    if(num == 1):
        vetor.append(num)
        break

vetor.reverse()
print(vetor)
import random,math
from tabuleiro import *
#Tempera Simulada para problema das 8 rainhas

# Têmpera simulada (simulated annealing)
def tempera_simulada(tabuleiro):
    atual = tabuleiro

    # escolhe a temperatura atual
    t = 500 * len(tabuleiro["rainhas"])
    e = math.exp(1)
    while True:
        random.seed()

        # cria lista de sucessores
        lista_novos_estados(atual)

        # seleciona números aleatórios para decidir o próximo estado a ser selecionado
        aleatorio1 = random.randint(0, len(atual["sucessores"]) - 1)
        aleatorio2 = random.randint(0, len(atual["sucessores"]) - 1)

        # ao atingir a temperatura mínima, o algoritmo retorna o último estado analisado
        # o mesmo quando encontra o estado ótimo
        if t == 0 or atual["conflitos"] == 0:
            return atual

        # seleciona um sucessor aleatório e atualiza o valor de detal E
        proximo = atual["sucessores"][aleatorio1]
        delta_e = proximo["conflitos"] - atual["conflitos"]

        # caso delta E seja menor que zero, atualiza o estado para ser analisado
        if delta_e < 0:
            atual = proximo

        # caso contrário, o valor E é checado e se este for menor que e elevado a (delta E/ t)
        # se escolhe um novo valor aleatório para atualizar o estado a ser analisado
        # se não, o valor da temperatura diminui
        else:
            if e < e ** (delta_e / t):
                while aleatorio1 == aleatorio2:
                    aleatorio2 = random.randint(0, len(atual["sucessores"]) - 1)
                atual = atual["sucessores"][aleatorio2]
            else:
                t = t - 1


#exexuções: têmpera simulada (Simulated Annealing)
def exeTS(n,qtd):
    print("Têmpera Simulada:")
    for i in range(n):
        rainhas = distribuir_rainhas(qtd)
        print(f"{rainhas}")
        tabuleiro = cria_estado(rainhas)

        print("Estado inicial")
        print("Distribuição inicial:", rainhas)
        estado_inicial = montar_tabuleiro(rainhas)
        printTabuleiro(estado_inicial)
        print("Número de conflitos inicialmente:", tabuleiro["conflitos"])

        estado_final = tempera_simulada(tabuleiro)

        r1 = montar_tabuleiro(estado_final["rainhas"])
        # 
        if estado_final["conflitos"] == 0:
            print("Solução encontrada!")
        else:
            print("Solução não encontrada.")
            print("Número de conflitos na distribuição:", conflitos(estado_final["rainhas"]))
        
        print("Distribuição das rainhas", estado_final["rainhas"])
        print("Tabuleiro:")
        printTabuleiro(r1)
        print("=======================================================\n")    

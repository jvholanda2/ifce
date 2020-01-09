import random

# rainhas no tabuleiro
def distribuir_rainhas(n):
    rainhas = [i for i in range(n)]#[0,1,2,3,4,5,6,7]
    random.shuffle(rainhas)#embaralha
    return rainhas

# cria estado do tabuleiro de acordo com a distribuição das rainhas
def cria_estado(rainhas):
    #                                                                  sucessores da atual distribuição
    tabuleiro = {"rainhas": rainhas, "conflitos": conflitos(rainhas), "sucessores": []}
    return tabuleiro


# cria lista de sucessores(Novos estados) para o estado atual
def lista_novos_estados(tabuleiro):
    for i in range(len(tabuleiro["rainhas"]) - 1):
        for j in range(i + 1, len(tabuleiro["rainhas"])):
            sucessor = cria_estado(tabuleiro["rainhas"][:])
            aux = sucessor["rainhas"][i]
            sucessor["rainhas"][i] = sucessor["rainhas"][j]
            sucessor["rainhas"][j] = aux
            sucessor["conflitos"] = conflitos(sucessor["rainhas"])
            tabuleiro["sucessores"].append(sucessor)


# monta o tabuleiro de acordo com a distribuição das rainhas
def montar_tabuleiro(rainhas):
    #criar tabuleiro zerado
    tabuleiro = [[0 for i in range(len(rainhas))] for j in range(len(rainhas))]
    coluna = 0
    for rainha in rainhas:
        tabuleiro[rainha][coluna] = coluna + 1#ajuste de indice
        coluna += 1
    return tabuleiro


# função para o cáculo da heurística
def conflitos(rainhas):#peso
    h = 0
    for i in range(len(rainhas)):
        j = 1
        while j < len(rainhas) - i:
            # caso alguma das rainhas das próximas colunas esteja na mesma diagonal (principal ou secundária)
            # da rainha indexada, contabiliza-se um conflito
            if (rainhas[i] == rainhas[i + j] + j) or (rainhas[i] == rainhas[i + j] - j):
                h += 1
            j += 1
    return h

def printTabuleiro(estado):
    # printTabuleiro
    for linha in estado:
        print(linha)
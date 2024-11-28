from itertools import permutations
import math

# Função para calcular a distância Euclidiana entre duas cidades
def calcular_distancia(cidade1, cidade2):
    x1, y1 = cidade1
    x2, y2 = cidade2
    return math.sqrt((x2 - x1)**2 + (y2 - y1)**2)

# Função para calcular a distância total de uma determinada rota
def calcular_distancia_total(rota, cidades):
    distancia_total = 0
    for i in range(len(rota) - 1):
        distancia_total += calcular_distancia(cidades[rota[i]], cidades[rota[i + 1]])
    # Retornar à cidade de origem
    distancia_total += calcular_distancia(cidades[rota[-1]], cidades[rota[0]])
    return distancia_total

# Função para resolver o problema do Caixeiro Viajante utilizando força bruta
def caixeiro_viajante(cidades):
    n = len(cidades)
    cidades_indices = list(range(n))
     
    menor_distancia = float('inf')
    melhor_rota = []

    # Gerar todas as permutações possíveis das cidades
    for rota in permutations(cidades_indices):
        distancia_atual = calcular_distancia_total(rota, cidades)
        if distancia_atual < menor_distancia:
            menor_distancia = distancia_atual
            melhor_rota = rota

    return melhor_rota, menor_distancia

# Coordenadas de 10 cidades (x, y)
cidades = [
    (0, 0), (2, 3), (5, 2), (6, 6), (8, 3),
    (3, 7), (7, 5), (1, 8), (4, 4), (9, 0)
]

melhor_rota, menor_distancia = caixeiro_viajante(cidades)

# Printar a solução
print("Melhor rota:", [i + 1 for i in melhor_rota]) 
print("Distância total:", menor_distancia)

# Printar a sequência de cidades e a distância entre cada uma
print("\nCadeia de cidades e distâncias:")
for i in range(len(melhor_rota)):
    cidade_atual = melhor_rota[i]
    proxima_cidade = melhor_rota[(i + 1) % len(melhor_rota)]
    distancia = calcular_distancia(cidades[cidade_atual], cidades[proxima_cidade])
    print(f"Cidade {cidade_atual + 1} -> Cidade {proxima_cidade + 1}: {distancia:.2f}")
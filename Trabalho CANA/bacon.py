import json
import pandas as pd
from collections import deque

def carregar_dados(caminho_csv):
    dados = pd.read_csv(caminho_csv)
    return dados

def construir_grafo(dados):
    grafo = {}
    
    for _, linha in dados.iterrows():
        try:
            cast = json.loads(linha['cast'])
            atores = [ator['name'] for ator in cast]
            
            for ator in atores:
                if ator not in grafo:
                    grafo[ator] = set()
                for colega in atores:
                    if colega != ator:
                        grafo[ator].add((colega, linha['title']))
        except (KeyError, json.JSONDecodeError):
            continue 

    return grafo

def encontrar_cadeia(grafo, ator_inicial, ator_destino):
    """Encontra a menor cadeia de conexões entre dois atores usando BFS."""
    if ator_inicial not in grafo or ator_destino not in grafo:
        return None

    visitados = set()
    fila = deque([(ator_inicial, [])])  # (ator_atual, cadeia_atual)

    while fila:
        ator_atual, cadeia_atual = fila.popleft()
        if ator_atual in visitados:
            continue
        visitados.add(ator_atual)

        for colega, filme in grafo[ator_atual]:
            nova_cadeia = cadeia_atual + [(ator_atual, filme)]
            if colega == ator_destino:
                return nova_cadeia + [(colega, "")]
            fila.append((colega, nova_cadeia))

    return None

# Exemplo de uso:
caminho_csv = 'tmdb_5000_movies.csv'
dados = carregar_dados('tmdb_5000_credits.csv')
dados['cast'] = dados['cast'].fillna('[]')
grafo = construir_grafo(dados)

ator1 = "Leonardo DiCaprio"
ator2 = "Ben Affleck"
cadeia = encontrar_cadeia(grafo, ator1, ator2)

if cadeia:
    print("Cadeia encontrada:")
    for ator, filme in cadeia:
        if filme:
            print(f"{ator} atuou em '{filme}'")
        else:
            print(f"{ator} é o destino.")
else:
    print("Nenhuma cadeia encontrada entre os atores.")

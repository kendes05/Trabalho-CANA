
import json
import pandas as pd
from collections import deque


def carregar_dados(caminho_csv):
    dados = pd.read_csv(caminho_csv) #Custo Tempo da função  = O(n*m)
    return dados

#Complexidade de tempo = O(n*m)
#Complexidade de espaço = O(n*m)


def construir_grafo(dados):
    grafo = {} # Custo Tempo  = O(1)
               # Custo Espaço  = O(1)
    
    for _, linha in dados.iterrows(): # Custo Tempo = O(n) -> Número de linhas nos dados (Verifica filme por filme)
        try:
            cast = json.loads(linha['cast']) # CUSTO Tempo = O(k) -> Função com custo constante

            #Custo de espaço = O(n*a) -> O custo é "a", sendo "a" a quantidade de atores de um filme; executa n vezes(n = número de filmes)
            

            atores = [ator['name'] for ator in cast] # CUSTO Tempo = O(a) -> Depende do numero de atores em cada filme
            
             #Custo de espaço = O(n*a) -> O custo é "a", sendo "a" a quantidade de atores de um filme; executa n vezes(n = número de filmes)
            
            for ator in atores: # Custo Tempo = O(n * a) -> Depende do numero de atores

                if ator not in grafo: # Custo Tempo = O(1) -> A verificação de chave em um dicionário é constante em termos de tempo

                    grafo[ator] = set() # Custo Tempo = O(1)

                # O custo de espaço alocado da linha acima depende do número de atores que ainda não estão no grafo; este custo será O(d), sendo "d" o número de atores distintos.

                # Portanto, o custo de espaço será O(d + n*a), pois aqui se trata a verificação dentro do laço de filmes, dentro do laço de atores, vericação estão que analisa quais atores não estão no grafo(variável "d").
                                        
                for colega in atores: # Custo Tempo = O(n * a^2) - > Isto pois estamos percorrendo os filmes, ao mesmo tempo que já percorremos os atores e iremos percorrer os atores novamente, sendo, a(atores)*a(atores) = a^2 e "n" o número de filmes percorridos
                
                    if colega != ator: # Custo Tempo = O(1)
                        grafo[ator].add((colega, linha['title'])) # Custo Tempo = O(1)

                        # Custo de espaço = O(n * a^2)

        except (KeyError, json.JSONDecodeError): # Custo Tempo = O(1)
            continue 

    return grafo

# Complexidade de tempo = O(n*a^2) é o dominante
# Complexidade de espaço = O(n*a^2) é o dominante



def encontrar_cadeia(grafo, ator_inicial, ator_destino):
    """Encontra a menor cadeia de conexões entre dois atores usando BFS."""
    if ator_inicial not in grafo or ator_destino not in grafo:
        return None

    visitados = set() # Custo de espaço = O(n). No pior caso, todos os atores podem ser visitados 

    fila = deque([(ator_inicial, [])])  # (ator_atual, cadeia_atual)

    # Custo de espaço = O(n). No pior caso, todos os atores podem ser visitados e armazenados na fila, caso não haja ciclo, caso não hajam conexões.

    while fila: # Custo de tempo = O(n). "n" sendo: Enquanto houver elementos na fila

        ator_atual, cadeia_atual = fila.popleft()

        # Custo de espaço = O(n). Este valor aumenta exponecialmente de acordo com o número de atores visitados na fila.

        if ator_atual in visitados:
            continue
        visitados.add(ator_atual)  # Custo de espaço = O(n) no pior caso.


        for colega, filme in grafo[ator_atual]: 
            
            # Custo de tempo = O(n*k), sendo "k" o número de atores que atuaram juntos.


            nova_cadeia = cadeia_atual + [(ator_atual, filme)]
            if colega == ator_destino:
                return nova_cadeia + [(colega, "")]
            fila.append((colega, nova_cadeia))

            # O custo de espaço desta linha pode ser O(n^2); pois no pior caso todos os atores podem ser armazenados nos parâmetros que esta fila recebe.

    return None

# Complexidade de tempo = O(n*k) é o dominante  
# Complexidade de espaço = O(n^2) é o dominante

# Exemplo de uso:
caminho_csv = 'tmdb_5000_movies.csv'  # Substitua pelo caminho correto
dados = carregar_dados('tmdb_5000_credits.csv')
dados['cast'] = dados['cast'].fillna('[]')  # Lida com valores nulos
grafo = construir_grafo(dados)

# Verificar conexão entre dois atores
ator1 = "Sam Worthington"
ator2 = "Robert Downey Jr."
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





def mochila(memorizar,peso,valor,nomes,n,max):

    if(n<0 or max==0):
        return 0
    if memorizar[n][max-1] !=0:
        return memorizar[n][max-1]
    if(peso[n]>max):
        return mochila(memorizar,peso,valor,nomes,n-1,max)
    else:
        nao = mochila(memorizar,peso,valor,nomes,n-1,max)
        sim = valor[n] + mochila(memorizar,peso,valor,nomes,n-1,max-peso[n])

        if sim>nao:
            memorizar[n][max-1] =  sim
        else:
            memorizar[n][max-1] = nao
        return memorizar[n][max-1]
    

def getnomes(memorizar):
    guardar = []
    pos = 1
    for i in range(len(memorizar)-1):
        atual = memorizar[len(memorizar)-i-1][len(memorizar[0])-pos]
        proximo = memorizar[len(memorizar)-i-2][len(memorizar[0])-pos]
        if(atual!=proximo):
            guardar.append(nomes[len(memorizar)-1-i])
            pos += peso[len(memorizar)-1-i]
        
            
    if pos+peso[0]<=len(memorizar[0]):
        guardar.append(nomes[0])
    return guardar



def problema(peso:list,valor:list,nomes:list,max):
    memorizar = [[0 for i in range(max)] for j in peso]
    result = mochila(memorizar,peso,valor,nomes,len(peso)-1,max)
    itens = getnomes(memorizar)
    print(result)
    print(itens)



peso = [1,3,4,5]
valor = [1,4,5,7]
nomes = ["1","2","3","4"]
max = 7



problema(peso,valor,nomes,max)








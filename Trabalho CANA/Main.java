public class Main {
    public static void main(String[] args) {
        //caso mude o tamanho do tabuleiro ou queira mudar a quantidade de damas, trocar na linha 57 "if(contador == x)"
        int tamanhotabuleiro = 8;
        int linhas = tamanhotabuleiro;
        int colunas = tamanhotabuleiro;

        Tabuleiro mendes = new Tabuleiro();
        mendes.TabuleiroFeito(linhas, colunas);
    }
}

class Tabuleiro {

    private int contador = 0;
    private int linhas;
    private int colunas;
    
    String[][] tabuleiro;

    // Inicializando o tabuleiro com "." (vazio)
    public void TabuleiroFeito(int linhas, int colunas) {

        this.linhas = linhas;
        this.colunas = colunas;
        this.tabuleiro = new String[linhas][colunas];

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                tabuleiro[i][j] = ".";
            }
        }

        Damas(0);
    }

    public void PrintarTabuleiro() {
        contador = 0;
        for (int i = 0; i < this.tabuleiro.length; i++) {
            for (int j = 0; j < this.tabuleiro[i].length; j++) {
                if (this.tabuleiro[i][j].equals("D")) {
                    contador++;
                }
                System.out.print(this.tabuleiro[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println("Damas colocadas: " + contador);
        System.out.println();
    }


    public void Damas(int linha) {
        if (linha == linhas) {
            //mudar o contador caso mude a quantidade de damas
            if (contador == 8) {
                System.out.println("DEU CERTO!");
                PrintarTabuleiro();
                System.exit(0);  // Encerra o programa após encontrar a solução
            }
            return;
        }

        // Tentar colocar uma dama em cada coluna da linha atual
        for (int coluna = 0; coluna < colunas; coluna++) {
            if (Correto(linha, coluna)) {  // Usando a função 'Correto' para verificar posição válida
                tabuleiro[linha][coluna] = "D";  // Coloca uma dama
                System.out.println("Tentativa: colocando dama na posição (" + linha + ", " + coluna + ")");
                PrintarTabuleiro();

                Damas(linha + 1);  // Tenta colocar a próxima dama na próxima linha (função recurssiva)

                tabuleiro[linha][coluna] = ".";  // Desfaz a jogada (backtrack) no final
                // caso uma linha nao possa ter dama, ele apaga o da linha de cima e coloca na proxima posicao válida, e caso nao tenha, ele apaga o de cima novamente.
                System.out.println("Backtracking: removendo dama da posição (" + linha + ", " + coluna + ")");
                PrintarTabuleiro();
            }else{
                //Printa o codigo tentando colocar em um posicao inválida, em seguida ele remove.
                tabuleiro[linha][coluna] = "D";
                System.out.println("Tentativa: colocando dama na posição (" + linha + ", " + coluna + ")");
                PrintarTabuleiro();
                tabuleiro[linha][coluna] = ".";
                System.out.println("Backtracking: removendo dama da posição (" + linha + ", " + coluna + ")");
                PrintarTabuleiro();

            }
        }
    }

    public boolean Correto(int linha, int coluna) {

        for (int i = 0; i < linha; i++) {
            if (tabuleiro[i][coluna].equals("D")) {
                System.out.println("NAO PODE AOS LADOS");
                return false;
            }
        }

        for (int j = 0; j < tabuleiro.length; j++) {
            if (tabuleiro[linha][j].equals("D")) {
                System.out.println("NAO PODE AOS LADOS");
                return false;
            }
        }

        for (int i = linha, j = coluna; i < tabuleiro.length && j < tabuleiro.length; i++, j++) {
            if (tabuleiro[i][j].equals("D") && !(i == linha && j == coluna)) { // Exclui o elemento central
                System.out.println("DIAGONAL PRINCIPAL");
                return false;
            }
        }

        for (int i = linha - 1, j = coluna - 1; i >= 0 && j >= 0; i--, j--) {
            if (tabuleiro[i][j].equals("D")) {
                System.out.println("DIAGONAL PRINCIPAL");
                return false;
            }
        }

        for (int i = linha, j = coluna; i < tabuleiro.length && j >= 0; i++, j--) {
            if (tabuleiro[i][j].equals("D") && !(i == linha && j == coluna)) { // Exclui o elemento central
                System.out.println("DIAGONAL SECUNDARIA");
                return false;
            }
        }

        for (int i = linha - 1, j = coluna + 1; i >= 0 && j < tabuleiro.length; i--, j++) {
            if (tabuleiro[i][j].equals("D")) {
                System.out.println("DIAGONAL SECUNDARIA");
                return false;
            }
        }

        return true;
    }
}

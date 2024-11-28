import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Arquivo {
    private String nome;
    private int paginas;
    private int tamanho;

    public Arquivo(String nome, int paginas, int tamanho) {
        this.nome = nome;
        this.paginas = paginas;
        this.tamanho = tamanho;
    }

    public String getNome() {
        return nome;
    }

    public int getPaginas() {
        return paginas;
    }

    public int getTamanho() {
        return tamanho;
    }
}

public class Analisejg {

    public static List<Arquivo> selecionarArquivos(int capacidade, List<Arquivo> arquivos) { // espaço n
        System.out.println("\nIniciando o processo de seleção de arquivos..."); // custo 0 repetições 1
        System.out.println("Capacidade total do pendrive: " + capacidade + "MB"); // custo 0 repetições 1

        arquivos.sort(new Comparator<Arquivo>() { // custo 1 repetições n log n (pior caso) <-- esse sort é o TimSort segundo o GPT
            @Override
            public int compare(Arquivo a1, Arquivo a2) {
                double eficienciaA1 = (double) a1.getPaginas() / a1.getTamanho(); // custo 1 repetições n log n -1 // espaço 1
                double eficienciaA2 = (double) a2.getPaginas() / a2.getTamanho(); // custo 1 repetições n log n -1 // espaço 1
                return Double.compare(eficienciaA2, eficienciaA1); // custo 1 repetições n log n -1
            }
        });

        List<Arquivo> selecionados = new ArrayList<>(); // custo 0 repetições 1 // espaço n
        int espacoDisponivel = capacidade; // custo 0 repetições 1 // espaço 1

        System.out.println("\nArquivos disponíveis após ordenação pela eficiência:"); // custo 0 repetições 1
        for (Arquivo arq : arquivos) { //custo 1 repetições n
            double eficiencia = (double) arq.getPaginas() / arq.getTamanho(); // custo 1 repetições n-1 // espaço 1
            System.out.printf("- %s (Páginas: %d, Tamanho: %dMB, Eficiência: %.2f páginas/MB)\n", // custo 0 repetições n-1
                    arq.getNome(), arq.getPaginas(), arq.getTamanho(), eficiencia);
        }

        for (Arquivo arquivo : arquivos) { //custo 1 repetições n
            System.out.println("\nAnalisando o arquivo: " + arquivo.getNome()); //custo 0 repetições n-1
            System.out.println("Espaço restante no pendrive: " + espacoDisponivel + "MB"); //custo 0 repetições n-1

            if (arquivo.getTamanho() <= espacoDisponivel) { //custo 1 repetições n-1
                selecionados.add(arquivo); //custo 0 repetições n-1
                espacoDisponivel -= arquivo.getTamanho(); //custo 0 reepetições n-1
                System.out.println(">> Arquivo adicionado: " + arquivo.getNome()); //custo 0 repetições n-1
            } else {
                System.out.println(">> Arquivo ignorado: não cabe no pendrive."); //custo 0 repetições n - k vezes gpt
            }
        }

        return selecionados; // custo 0 repetições 1
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // espaço 1

        System.out.println("Bem-vindo ao programa de seleção de arquivos para o pendrive!"); //custo 0 repetições 1
        System.out.print("Informe a capacidade do pendrive em MB: "); //custo 0 repetições 1
        int capacidade = scanner.nextInt(); //custo 0 repetições 1 // espaço 1

        List<Arquivo> listaDeArquivos = new ArrayList<>(); //custo 0 repetições 1  // espaço n


        System.out.println("\nAgora, vamos adicionar os arquivos disponíveis."); //custo 0 repetições 1
        System.out.print("Quantos arquivos você deseja adicionar? "); //custo 0 repetições 1
        int quantidade = scanner.nextInt(); //custo 0 repetições 1 // espaço 1
        scanner.nextLine(); //custo 0 repetições 1
        for (int i = 1; i <= quantidade; i++) {//custo 1 repetições n
            System.out.println("\nAdicionando o arquivo " + i + ":");//custo 0 repetições n-1
            System.out.print("Nome do arquivo: ");//custo 0 repetições n-1
            String nome = scanner.nextLine();//custo 0 repetições n-1
            System.out.print("Número de páginas: ");//custo 0 repetições n-1
            int paginas = scanner.nextInt();//custo 0 repetições n-1 // espaço 1
            System.out.print("Tamanho em MB: ");//custo 0 repetições n-1
            int tamanho = scanner.nextInt();//custo 0 repetições n-1 // espaço 1
            scanner.nextLine();//custo 0 repetições n-1

            listaDeArquivos.add(new Arquivo(nome, paginas, tamanho));//custo 0 repetições n-1
        }

        System.out.println("\nLista de arquivos adicionados:"); //custo 0 repetições 1
        for (Arquivo arq : listaDeArquivos) { //custo 1 repetições n
            System.out.println("- " + arq.getNome() + " (Páginas: " + arq.getPaginas() + ", Tamanho: " + arq.getTamanho() + "MB)");//custo 0 repetições n-1
        }



        List<Arquivo> resultado = selecionarArquivos(capacidade, listaDeArquivos);//custo 0 repetições 1 // espaço n




        System.out.println("\n### Arquivos selecionados para o pendrive ###");//custo 0 repetições 1
        for (Arquivo arq : resultado) {//custo 1 repetições n
            System.out.println("- " + arq.getNome());//custo 0 repetições n-1
        }
    }
}
//Feito a análise do codigo, determinamos que a complexidade de tempo do algoritmo se dar pela soma de:
//0+0+(n log n)+ 3(nlogn-1)+0+0+0+(n)+(n-1)+0+(n)+0+0+(n-1)+0+0+0+0+0+0+0+0+0+0+0+0+0+(n)+0+0+0+0+0+0+0+0+0+(n)+0+0+0+(n)+0
//simplificando --> 4(n log n) +7n -5
//Termo dominante n log n logo
//Complexidade de tempo é O(nlogn)


//Para determinar a Complexidade de Espaço, precisamos levar em consideração todas as vaiaveis criadas, sendo elas : capacidade,espacoDisponivel,quantidade,nome,paginas e tamanho. Essas
//como so admitem 1 valor por vez, sao constantes, ou seja O(1)
//Porém, existe listas como: resultado e ListaDeArquivos onde ambas contém N objetos do tipo arquivo, sendo assim, a complexidade de espaço de cada uma é O(n)
//A complexidade de espaço se dar por 1+1+1+1+1+1+n+n --> 2n + 6
// Sendo assim o termo dominante é "n", logo
//Complexidade de espaço é: O(n)
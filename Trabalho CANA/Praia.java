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

public class Praia {

    public static List<Arquivo> selecionarArquivos(int capacidade, List<Arquivo> arquivos) {
        System.out.println("\nIniciando o processo de seleção de arquivos...");
        System.out.println("Capacidade total do pendrive: " + capacidade + "MB");

        arquivos.sort(new Comparator<Arquivo>() {
            @Override
            public int compare(Arquivo a1, Arquivo a2) {
                double eficienciaA1 = (double) a1.getPaginas() / a1.getTamanho();
                double eficienciaA2 = (double) a2.getPaginas() / a2.getTamanho();
                return Double.compare(eficienciaA2, eficienciaA1);
            }
        });


        List<Arquivo> selecionados = new ArrayList<>();
        int espacoDisponivel = capacidade;

        System.out.println("\nArquivos disponíveis após ordenação pela eficiência:");
        for (Arquivo arq : arquivos) {
            double eficiencia = (double) arq.getPaginas() / arq.getTamanho();
            System.out.printf("- %s (Páginas: %d, Tamanho: %dMB, Eficiência: %.2f páginas/MB)\n",
                    arq.getNome(), arq.getPaginas(), arq.getTamanho(), eficiencia);
        }

        for (Arquivo arquivo : arquivos) {
            System.out.println("\nAnalisando o arquivo: " + arquivo.getNome());
            System.out.println("Espaço restante no pendrive: " + espacoDisponivel + "MB");

            if (arquivo.getTamanho() <= espacoDisponivel) {
                selecionados.add(arquivo);
                espacoDisponivel -= arquivo.getTamanho();
                System.out.println(">> Arquivo adicionado: " + arquivo.getNome());
            } else {
                System.out.println(">> Arquivo ignorado: não cabe no pendrive.");
            }
        }

        return selecionados;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao programa de seleção de arquivos para o pendrive!");
        System.out.print("Informe a capacidade do pendrive em MB: ");
        int capacidade = scanner.nextInt();

        List<Arquivo> listaDeArquivos = new ArrayList<>();
        System.out.println("\nAgora, vamos adicionar os arquivos disponíveis.");
        System.out.print("Quantos arquivos você deseja adicionar? ");
        int quantidade = scanner.nextInt();
        scanner.nextLine(); 
        for (int i = 1; i <= quantidade; i++) {
            System.out.println("\nAdicionando o arquivo " + i + ":");
            System.out.print("Nome do arquivo: ");
            String nome = scanner.nextLine();
            System.out.print("Número de páginas: ");
            int paginas = scanner.nextInt();
            System.out.print("Tamanho em MB: ");
            int tamanho = scanner.nextInt();
            scanner.nextLine();

            listaDeArquivos.add(new Arquivo(nome, paginas, tamanho));
        }

        System.out.println("\nLista de arquivos adicionados:");
        for (Arquivo arq : listaDeArquivos) {
            System.out.println("- " + arq.getNome() + " (Páginas: " + arq.getPaginas() + ", Tamanho: " + arq.getTamanho() + "MB)");
        }

        List<Arquivo> resultado = selecionarArquivos(capacidade, listaDeArquivos);

        System.out.println("\n### Arquivos selecionados para o pendrive ###");
        for (Arquivo arq : resultado) {
            System.out.println("- " + arq.getNome());
        }
    }
}



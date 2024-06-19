import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class EmpresaInteressada {
    private String id;
    private int quantidade;
    private int valor;

    public EmpresaInteressada(String id, int quantidade, int valor) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public int getValor() {
        return valor;
    }
}

class MelhorResultado {
    private int lucroMaximo;
    private List<EmpresaInteressada> melhorSelecao;
    private int energiaSobrando;

    public MelhorResultado() {
        this.lucroMaximo = 0;
        this.melhorSelecao = new ArrayList<>();
        this.energiaSobrando = 0;
    }

    public int getLucroMaximo() {
        return lucroMaximo;
    }

    public void setLucroMaximo(int lucroMaximo) {
        this.lucroMaximo = lucroMaximo;
    }

    public List<EmpresaInteressada> getMelhorSelecao() {
        return melhorSelecao;
    }

    public void setMelhorSelecao(List<EmpresaInteressada> melhorSelecao) {
        this.melhorSelecao = melhorSelecao;
    }

    public int getEnergiaSobrando() {
        return energiaSobrando;
    }

    public void setEnergiaSobrando(int energiaSobrando) {
        this.energiaSobrando = energiaSobrando;
    }
}

public class LeilaoDinamico {

    public MelhorResultado resolverLeilao(int quantidadeDisponivel, List<EmpresaInteressada> lances) {
        int n = lances.size();
        int[][] dp = new int[n + 1][quantidadeDisponivel + 1];
        boolean[][] solucao = new boolean[n + 1][quantidadeDisponivel + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= quantidadeDisponivel; w++) {
                EmpresaInteressada lance = lances.get(i - 1);
                if (lance.getQuantidade() <= w) {
                    if (lance.getValor() + dp[i - 1][w - lance.getQuantidade()] > dp[i - 1][w]) {
                        dp[i][w] = lance.getValor() + dp[i - 1][w - lance.getQuantidade()];
                        solucao[i][w] = true;
                    } else {
                        dp[i][w] = dp[i - 1][w];
                    }
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        List<EmpresaInteressada> selecao = new ArrayList<>();
        for (int i = n, w = quantidadeDisponivel; i > 0; i--) {
            if (solucao[i][w]) {
                EmpresaInteressada lance = lances.get(i - 1);
                selecao.add(lance);
                w -= lance.getQuantidade();
            }
        }

        MelhorResultado resultado = new MelhorResultado();
        resultado.setLucroMaximo(dp[n][quantidadeDisponivel]);
        resultado.setMelhorSelecao(selecao);
        resultado.setEnergiaSobrando(quantidadeDisponivel - selecao.stream().mapToInt(EmpresaInteressada::getQuantidade).sum());
        return resultado;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite a quantidade disponível de energia (em MW):");
        int quantidadeDisponivel = scanner.nextInt();

        System.out.println("Digite o tamanho do conjunto inicial (T):");
        int T = scanner.nextInt();

        System.out.println("Digite o número de testes a serem executados para cada tamanho de conjunto:");
        int numTestes = scanner.nextInt();

        Random random = new Random();
        long tempoTotalInicio = System.nanoTime();

        for (int tamanhoConjunto = T; tamanhoConjunto <= 10 * T; tamanhoConjunto += T) {
            int somaLucro = 0;
            long somaTempo = 0;
            for (int teste = 0; teste < numTestes; teste++) {
                List<EmpresaInteressada> lances = new ArrayList<>();
                for (int i = 0; i < tamanhoConjunto; i++) {
                    String id = "I" + (i + 1);
                    int quantidade = random.nextInt(quantidadeDisponivel / 2) + 1; // Gera quantidade entre 1 e quantidadeDisponivel/2
                    int valor = random.nextInt(1000) + 1; // Gera valor entre 1 e 1000
                    lances.add(new EmpresaInteressada(id, quantidade, valor));
                }

                LeilaoDinamico solver = new LeilaoDinamico();
                long inicio = System.nanoTime();
                MelhorResultado resultado = solver.resolverLeilao(quantidadeDisponivel, lances);
                long fim = System.nanoTime();
                somaTempo += (fim - inicio);
                somaLucro += resultado.getLucroMaximo();
            }
            double mediaLucro = (double) somaLucro / numTestes;
            double mediaTempo = (double) somaTempo / numTestes / 1_000_000; // Converter para milissegundos
            System.out.println("Tamanho do conjunto: " + tamanhoConjunto + ", Lucro médio: " + mediaLucro + ", Tempo médio: " + mediaTempo + " ms");
        }

        long tempoTotalFim = System.nanoTime();
        double tempoTotalExecucao = (tempoTotalFim - tempoTotalInicio) / 1_000_000_000.0; // Converter para segundos
        System.out.println("Tempo total de execução: " + tempoTotalExecucao + " s");

        scanner.close();
    }
}

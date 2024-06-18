import java.util.ArrayList;
import java.util.List;
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

        System.out.println("Digite o número de lances:");
        int numeroDeLances = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha restante

        List<EmpresaInteressada> lances = new ArrayList<>();
        for (int i = 0; i < numeroDeLances; i++) {
            System.out.println("Digite o ID, a quantidade e o valor do lance " + (i + 1) + " (ex: I1 500 500):");
            String id = scanner.next();
            int quantidade = scanner.nextInt();
            int valor = scanner.nextInt();
            lances.add(new EmpresaInteressada(id, quantidade, valor));
        }

        LeilaoDinamico solver = new LeilaoDinamico();
        MelhorResultado resultado = solver.resolverLeilao(quantidadeDisponivel, lances);

        System.out.println("Melhor lucro: " + resultado.getLucroMaximo());
        System.out.println("Energia sobrando: " + resultado.getEnergiaSobrando() + " MW");
        System.out.println("Lances selecionados:");
        for (EmpresaInteressada lance : resultado.getMelhorSelecao()) {
            System.out.println("ID: " + lance.getId() + ", Quantidade: " + lance.getQuantidade() + ", Valor: " + lance.getValor());
        }

        scanner.close();
    }
}

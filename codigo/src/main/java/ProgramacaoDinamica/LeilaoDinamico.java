import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Classe para representar uma empresa interessada
class EmpresaInteressada {
    private String id; // ID da empresa
    private int quantidade; // Quantidade de energia desejada
    private int valor; // Valor oferecido pela energia

    // Construtor para inicializar os atributos da empresa
    public EmpresaInteressada(String id, int quantidade, int valor) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    // Métodos getter para acessar os atributos da empresa
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

// Classe para armazenar o melhor resultado do leilão
class MelhorResultado {
    private int lucroMaximo; // Maior lucro obtido
    private List<EmpresaInteressada> melhorSelecao; // Lista de empresas selecionadas
    private int energiaSobrando; // Quantidade de energia que restou

    // Construtor para inicializar os atributos com valores padrão
    public MelhorResultado() {
        this.lucroMaximo = 0;
        this.melhorSelecao = new ArrayList<>();
        this.energiaSobrando = 0;
    }

    // Métodos getter e setter para acessar e modificar os atributos
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

// Classe principal para resolver o problema do leilão usando programação dinâmica
public class LeilaoDinamico {

    // Método para resolver o problema do leilão
    public MelhorResultado resolverLeilao(int quantidadeDisponivel, List<EmpresaInteressada> lances) {
        int n = lances.size(); // Número de lances
        int[][] dp = new int[n + 1][quantidadeDisponivel + 1]; // Tabela de programação dinâmica
        boolean[][] solucao = new boolean[n + 1][quantidadeDisponivel + 1]; // Tabela para rastrear a solução

        // Preenchimento da tabela de programação dinâmica
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

        // Recuperação da melhor seleção de lances a partir da tabela de solução
        List<EmpresaInteressada> selecao = new ArrayList<>();
        for (int i = n, w = quantidadeDisponivel; i > 0; i--) {
            if (solucao[i][w]) {
                EmpresaInteressada lance = lances.get(i - 1);
                selecao.add(lance);
                w -= lance.getQuantidade();
            }
        }

        // Criação do objeto MelhorResultado para armazenar o resultado final
        MelhorResultado resultado = new MelhorResultado();
        resultado.setLucroMaximo(dp[n][quantidadeDisponivel]);
        resultado.setMelhorSelecao(selecao);
        resultado.setEnergiaSobrando(quantidadeDisponivel - selecao.stream().mapToInt(EmpresaInteressada::getQuantidade).sum());
        return resultado;
    }

    // Método principal para executar o programa
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Solicita ao usuário a quantidade disponível de energia
        System.out.println("Digite a quantidade disponível de energia (em MW):");
        int quantidadeDisponivel = scanner.nextInt();

        // Solicita ao usuário o número de lances
        System.out.println("Digite o número de lances:");
        int numeroDeLances = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha restante

        // Cria uma lista para armazenar os lances das empresas
        List<EmpresaInteressada> lances = new ArrayList<>();
        for (int i = 0; i < numeroDeLances; i++) {
            // Solicita ao usuário o ID, a quantidade e o valor de cada lance
            System.out.println("Digite o ID, a quantidade e o valor do lance " + (i + 1) + " (ex: I1 500 500):");
            String id = scanner.next();
            int quantidade = scanner.nextInt();
            int valor = scanner.nextInt();
            lances.add(new EmpresaInteressada(id, quantidade, valor));
        }

        LeilaoDinamico solver = new LeilaoDinamico();
        long tempoTotalInicio = System.nanoTime(); // Captura o tempo inicial de execução
        MelhorResultado resultado = solver.resolverLeilao(quantidadeDisponivel, lances);
        long tempoTotalFim = System.nanoTime(); // Captura o tempo final de execução
        
        double tempoTotalExecucao = (tempoTotalFim - tempoTotalInicio) / 1_000_000_000.0; // Converter para segundos

        // Exibe o melhor lucro, a energia sobrando e os lances selecionados
        System.out.println("Melhor lucro: " + resultado.getLucroMaximo());
        System.out.println("Energia sobrando: " + resultado.getEnergiaSobrando() + " MW");
        System.out.println("Lances selecionados:");
        for (EmpresaInteressada lance : resultado.getMelhorSelecao()) {
            System.out.println("ID: " + lance.getId() + ", Quantidade: " + lance.getQuantidade() + ", Valor: " + lance.getValor());
        }

        // Exibe o tempo total de execução
        System.out.println("Tempo total de execução: " + tempoTotalExecucao + " s");
        scanner.close(); // Fecha o scanner
    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Classe representando uma empresa interessada em comprar energia
class EmpresaInteressada {
    private String id;
    private int quantidade;
    private int valor;

    // Construtor da classe
    public EmpresaInteressada(String id, int quantidade, int valor) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    // Métodos getters para acessar os atributos privados
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

// Classe para armazenar o melhor resultado encontrado pelo algoritmo de backtracking
class MelhorResultado {
    private int lucroMaximo;
    private List<EmpresaInteressada> melhorSelecao;
    private int energiaSobrando;

    // Construtor inicializando os atributos
    public MelhorResultado() {
        this.lucroMaximo = 0;
        this.melhorSelecao = new ArrayList<>();
        this.energiaSobrando = 0;
    }

    // Métodos getters e setters para acessar e modificar os atributos privados
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

// Classe principal que implementa o algoritmo de leilão usando backtracking
public class LeilaoBacktracking {

    // Método para resolver o leilão e encontrar a melhor combinação de lances
    public MelhorResultado resolverLeilao(int quantidadeDisponivel, List<EmpresaInteressada> lances) {
        MelhorResultado resultado = new MelhorResultado();
        backtrack(lances, new ArrayList<>(), 0, quantidadeDisponivel, 0, resultado);
        resultado.setEnergiaSobrando(quantidadeDisponivel - resultado.getMelhorSelecao().stream().mapToInt(EmpresaInteressada::getQuantidade).sum());
        return resultado;
    }

    // Método de backtracking para explorar todas as combinações possíveis de lances
    private void backtrack(List<EmpresaInteressada> lances, List<EmpresaInteressada> selecaoAtual, int indiceAtual, int quantidadeDisponivel, int lucroAtual, MelhorResultado resultado) {
        if (indiceAtual == lances.size()) {
            // Se chegou ao fim da lista de lances, verifica se o lucro atual é o melhor encontrado
            if (lucroAtual > resultado.getLucroMaximo()) {
                resultado.setLucroMaximo(lucroAtual);
                resultado.setMelhorSelecao(new ArrayList<>(selecaoAtual));
            }
            return;
        }

        EmpresaInteressada lanceAtual = lances.get(indiceAtual);

        // Tentar incluir o lance atual na seleção
        if (lanceAtual.getQuantidade() <= quantidadeDisponivel) {
            selecaoAtual.add(lanceAtual);
            backtrack(lances, selecaoAtual, indiceAtual + 1, quantidadeDisponivel - lanceAtual.getQuantidade(), lucroAtual + lanceAtual.getValor(), resultado);
            selecaoAtual.remove(selecaoAtual.size() - 1);
        }

        // Tentar excluir o lance atual da seleção
        backtrack(lances, selecaoAtual, indiceAtual + 1, quantidadeDisponivel, lucroAtual, resultado);
    }

    // Método principal para executar o programa
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Entrada de dados do usuário
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

        // Capturar o tempo inicial
        long startTime = System.nanoTime();

        LeilaoBacktracking solver = new LeilaoBacktracking();
        MelhorResultado resultado = solver.resolverLeilao(quantidadeDisponivel, lances);

        // Capturar o tempo final
        long endTime = System.nanoTime();

        // Calcular o tempo de execução em segundos
        double duration = (endTime - startTime) / 1_000_000_000.0;

        // Exibir o resultado
        System.out.println("Melhor lucro: " + resultado.getLucroMaximo());
        System.out.println("Energia sobrando: " + resultado.getEnergiaSobrando() + " MW");
        System.out.println("Lances selecionados:");
        for (EmpresaInteressada lance : resultado.getMelhorSelecao()) {
            System.out.println("ID: " + lance.getId() + ", Quantidade: " + lance.getQuantidade() + ", Valor: " + lance.getValor());
        }

        // Exibir o tempo de execução
        System.out.println("Tempo de execução: " + duration + " segundos");

        scanner.close();
    }
}

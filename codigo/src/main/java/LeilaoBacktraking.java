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

public class LeilaoBacktracking {

    public MelhorResultado resolverLeilao(int quantidadeDisponivel, List<EmpresaInteressada> lances) {
        MelhorResultado resultado = new MelhorResultado();
        backtrack(lances, new ArrayList<>(), 0, quantidadeDisponivel, 0, resultado);
        resultado.setEnergiaSobrando(quantidadeDisponivel - resultado.getMelhorSelecao().stream().mapToInt(EmpresaInteressada::getQuantidade).sum());
        return resultado;
    }

    private void backtrack(List<EmpresaInteressada> lances, List<EmpresaInteressada> selecaoAtual, int indiceAtual, int quantidadeDisponivel, int lucroAtual, MelhorResultado resultado) {
        if (indiceAtual == lances.size()) {
            if (lucroAtual > resultado.getLucroMaximo()) {
                resultado.setLucroMaximo(lucroAtual);
                resultado.setMelhorSelecao(new ArrayList<>(selecaoAtual));
            }
            return;
        }

        EmpresaInteressada lanceAtual = lances.get(indiceAtual);

        // Tentar incluir o lance atual
        if (lanceAtual.getQuantidade() <= quantidadeDisponivel) {
            selecaoAtual.add(lanceAtual);
            backtrack(lances, selecaoAtual, indiceAtual + 1, quantidadeDisponivel - lanceAtual.getQuantidade(), lucroAtual + lanceAtual.getValor(), resultado);
            selecaoAtual.remove(selecaoAtual.size() - 1);
        }

        // Tentar excluir o lance atual
        backtrack(lances, selecaoAtual, indiceAtual + 1, quantidadeDisponivel, lucroAtual, resultado);
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

        LeilaoBacktracking solver = new LeilaoBacktracking();
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

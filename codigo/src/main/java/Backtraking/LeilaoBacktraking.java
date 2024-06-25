import java.util.ArrayList;
import java.util.List;

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

// Classe principal para resolver o problema do leilão usando programação
// dinâmica
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
        resultado.setEnergiaSobrando(
                quantidadeDisponivel - selecao.stream().mapToInt(EmpresaInteressada::getQuantidade).sum());
        return resultado;
    }

    // Método principal para executar o programa
    public static void main(String[] args) {
        // Definição dos dados de entrada (lances pré-definidos)
        int quantidadeDisponivel = 8000;
        List<EmpresaInteressada> lances = new ArrayList<>();
        lances.add(new EmpresaInteressada("I1", 430, 1043));
        lances.add(new EmpresaInteressada("I2", 428, 1188));
        lances.add(new EmpresaInteressada("I3", 410, 1565));
        lances.add(new EmpresaInteressada("I4", 385, 1333));
        lances.add(new EmpresaInteressada("I5", 399, 1214));
        lances.add(new EmpresaInteressada("I6", 382, 1498));
        lances.add(new EmpresaInteressada("I7", 416, 1540));
        lances.add(new EmpresaInteressada("I8", 436, 1172));
        lances.add(new EmpresaInteressada("I9", 416, 1386));
        lances.add(new EmpresaInteressada("I10", 423, 1097));
        lances.add(new EmpresaInteressada("I11", 400, 1463));
        lances.add(new EmpresaInteressada("I12", 406, 1353));
        lances.add(new EmpresaInteressada("I13", 403, 1568));
        lances.add(new EmpresaInteressada("I14", 390, 1228));
        lances.add(new EmpresaInteressada("I15", 387, 1542));
        lances.add(new EmpresaInteressada("I16", 390, 1206));
        lances.add(new EmpresaInteressada("I17", 430, 1175));
        lances.add(new EmpresaInteressada("I18", 397, 1492));
        lances.add(new EmpresaInteressada("I19", 392, 1293));
        lances.add(new EmpresaInteressada("I20", 393, 1533));
        lances.add(new EmpresaInteressada("I21", 439, 1149));
        lances.add(new EmpresaInteressada("I22", 403, 1277));
        lances.add(new EmpresaInteressada("I23", 415, 1624));
        lances.add(new EmpresaInteressada("I24", 387, 1280));
        lances.add(new EmpresaInteressada("I25", 417, 1330));
        lances.add(new EmpresaInteressada("I26", 250, 1200));
        lances.add(new EmpresaInteressada("I27", 350, 1500));
        lances.add(new EmpresaInteressada("I28", 450, 2200));
        lances.add(new EmpresaInteressada("I29", 550, 3000));
        lances.add(new EmpresaInteressada("I30", 650, 2400));
        lances.add(new EmpresaInteressada("I31", 275, 1300));
        lances.add(new EmpresaInteressada("I32", 375, 1600));
        lances.add(new EmpresaInteressada("I33", 475, 2300));
        lances.add(new EmpresaInteressada("I34", 575, 3100));
        lances.add(new EmpresaInteressada("I35", 675, 2500));
        lances.add(new EmpresaInteressada("I36", 300, 1400));
        lances.add(new EmpresaInteressada("I37", 400, 1700));
        lances.add(new EmpresaInteressada("I38", 500, 2600));
        lances.add(new EmpresaInteressada("I39", 600, 3200));
        lances.add(new EmpresaInteressada("I40", 700, 2800));
        lances.add(new EmpresaInteressada("I41", 325, 1500));
        lances.add(new EmpresaInteressada("I42", 425, 1800));
        lances.add(new EmpresaInteressada("I43", 525, 2700));
        lances.add(new EmpresaInteressada("I44", 625, 3300));
        lances.add(new EmpresaInteressada("I45", 725, 2900));
        lances.add(new EmpresaInteressada("I46", 350, 1600));
        lances.add(new EmpresaInteressada("I47", 450, 1900));
        lances.add(new EmpresaInteressada("I48", 550, 2800));
        lances.add(new EmpresaInteressada("I49", 650, 3400));
        lances.add(new EmpresaInteressada("I50", 750, 3000));
        lances.add(new EmpresaInteressada("I51", 375, 1700));
        lances.add(new EmpresaInteressada("I52", 475, 2000));
        lances.add(new EmpresaInteressada("I53", 575, 2900));
        lances.add(new EmpresaInteressada("I54", 675, 3500));
        lances.add(new EmpresaInteressada("I55", 775, 3100));
        lances.add(new EmpresaInteressada("I56", 400, 1800));
        lances.add(new EmpresaInteressada("I57", 500, 2100));
        lances.add(new EmpresaInteressada("I58", 600, 3000));
        lances.add(new EmpresaInteressada("I59", 700, 3600));
        lances.add(new EmpresaInteressada("I60", 800, 3200));
        lances.add(new EmpresaInteressada("I61", 425, 1900));
        lances.add(new EmpresaInteressada("I62", 525, 2200));
        lances.add(new EmpresaInteressada("I63", 625, 3100));
        lances.add(new EmpresaInteressada("I64", 725, 3700));
        lances.add(new EmpresaInteressada("I65", 825, 3300));
        lances.add(new EmpresaInteressada("I66", 450, 2000));
        lances.add(new EmpresaInteressada("I67", 550, 2300));
        lances.add(new EmpresaInteressada("I68", 650, 3200));
        lances.add(new EmpresaInteressada("I69", 750, 3800));
        lances.add(new EmpresaInteressada("I70", 850, 3400));
        lances.add(new EmpresaInteressada("I71", 475, 2100));
        lances.add(new EmpresaInteressada("I72", 575, 2400));
        lances.add(new EmpresaInteressada("I73", 675, 3300));
        lances.add(new EmpresaInteressada("I74", 775, 3900));
        lances.add(new EmpresaInteressada("I75", 875, 3500));
        lances.add(new EmpresaInteressada("I76", 500, 2200));
        lances.add(new EmpresaInteressada("I77", 600, 2500));
        lances.add(new EmpresaInteressada("I78", 700, 3400));
        lances.add(new EmpresaInteressada("I79", 800, 4000));
        lances.add(new EmpresaInteressada("I80", 900, 3600));
        lances.add(new EmpresaInteressada("I71", 475, 2100));
        lances.add(new EmpresaInteressada("I72", 575, 2400));
        lances.add(new EmpresaInteressada("I73", 675, 3300));
        lances.add(new EmpresaInteressada("I74", 775, 3900));
        lances.add(new EmpresaInteressada("I75", 875, 3500));
        lances.add(new EmpresaInteressada("I76", 500, 2200));
        lances.add(new EmpresaInteressada("I77", 600, 2500));
        lances.add(new EmpresaInteressada("I78", 700, 3400));
        lances.add(new EmpresaInteressada("I79", 800, 4000));
        lances.add(new EmpresaInteressada("I80", 900, 3600));
        lances.add(new EmpresaInteressada("I81", 525, 2300));
        lances.add(new EmpresaInteressada("I82", 625, 2600));
        lances.add(new EmpresaInteressada("I83", 725, 3500));
        lances.add(new EmpresaInteressada("I84", 825, 4100));
        lances.add(new EmpresaInteressada("I85", 925, 3700));
        lances.add(new EmpresaInteressada("I86", 550, 2400));
        lances.add(new EmpresaInteressada("I87", 650, 2700));
        lances.add(new EmpresaInteressada("I88", 750, 3600));
        lances.add(new EmpresaInteressada("I89", 850, 4200));
        lances.add(new EmpresaInteressada("I90", 950, 3800));
        lances.add(new EmpresaInteressada("I91", 575, 2500));
        lances.add(new EmpresaInteressada("I92", 675, 2800));
        lances.add(new EmpresaInteressada("I93", 775, 3700));
        lances.add(new EmpresaInteressada("I94", 875, 4300));
        lances.add(new EmpresaInteressada("I95", 975, 3900));
        lances.add(new EmpresaInteressada("I96", 600, 2600));
        lances.add(new EmpresaInteressada("I97", 700, 2900));
        lances.add(new EmpresaInteressada("I98", 800, 3800));
        lances.add(new EmpresaInteressada("I99", 900, 4400));
        lances.add(new EmpresaInteressada("I100", 1000, 4000));
        lances.add(new EmpresaInteressada("I101", 625, 2700));
        lances.add(new EmpresaInteressada("I102", 725, 3000));
        lances.add(new EmpresaInteressada("I103", 825, 3900));
        lances.add(new EmpresaInteressada("I104", 925, 4500));
        lances.add(new EmpresaInteressada("I105", 1025, 4100));
        lances.add(new EmpresaInteressada("I106", 650, 2800));
        lances.add(new EmpresaInteressada("I107", 750, 3100));
        lances.add(new EmpresaInteressada("I108", 850, 4000));
        lances.add(new EmpresaInteressada("I109", 950, 4600));
        lances.add(new EmpresaInteressada("I110", 1050, 4200));
        // 10T * 30 = 300 com quantidades e valores aleatórios
        lances.add(new EmpresaInteressada("I111", 800, 3500));
        lances.add(new EmpresaInteressada("I112", 700, 2900));
        lances.add(new EmpresaInteressada("I113", 900, 3700));
        lances.add(new EmpresaInteressada("I114", 1000, 4300));
        lances.add(new EmpresaInteressada("I115", 1100, 4700));
        lances.add(new EmpresaInteressada("I116", 675, 2600));
        lances.add(new EmpresaInteressada("I117", 775, 3200));
        lances.add(new EmpresaInteressada("I118", 875, 3800));
        lances.add(new EmpresaInteressada("I119", 975, 4400));
        lances.add(new EmpresaInteressada("I120", 1075, 4500));
        lances.add(new EmpresaInteressada("I121", 600, 2500));
        lances.add(new EmpresaInteressada("I122", 700, 2700));
        lances.add(new EmpresaInteressada("I123", 800, 3300));
        lances.add(new EmpresaInteressada("I124", 900, 4100));
        lances.add(new EmpresaInteressada("I125", 1000, 3900));
        lances.add(new EmpresaInteressada("I126", 1100, 4800));
        lances.add(new EmpresaInteressada("I127", 650, 2600));
        lances.add(new EmpresaInteressada("I128", 750, 3200));
        lances.add(new EmpresaInteressada("I129", 850, 4000));
        lances.add(new EmpresaInteressada("I130", 950, 4500));
        lances.add(new EmpresaInteressada("I131", 1050, 4300));
        lances.add(new EmpresaInteressada("I132", 700, 2800));
        lances.add(new EmpresaInteressada("I133", 800, 3600));
        lances.add(new EmpresaInteressada("I134", 900, 4200));
        lances.add(new EmpresaInteressada("I135", 1000, 4600));
        lances.add(new EmpresaInteressada("I136", 1100, 4900));
        lances.add(new EmpresaInteressada("I137", 625, 2500));
        lances.add(new EmpresaInteressada("I138", 725, 3100));
        lances.add(new EmpresaInteressada("I139", 825, 3700));
        lances.add(new EmpresaInteressada("I140", 925, 4400));
        lances.add(new EmpresaInteressada("I141", 1025, 4100));
        lances.add(new EmpresaInteressada("I142", 650, 2700));
        lances.add(new EmpresaInteressada("I143", 750, 3300));
        lances.add(new EmpresaInteressada("I144", 850, 3900));
        lances.add(new EmpresaInteressada("I145", 950, 4600));
        lances.add(new EmpresaInteressada("I146", 1050, 4700));
        lances.add(new EmpresaInteressada("I147", 700, 2800));
        lances.add(new EmpresaInteressada("I148", 800, 3500));
        lances.add(new EmpresaInteressada("I149", 900, 4200));
        lances.add(new EmpresaInteressada("I150", 1000, 4600));
        lances.add(new EmpresaInteressada("I151", 650, 2900));
        lances.add(new EmpresaInteressada("I152", 750, 3100));
        lances.add(new EmpresaInteressada("I153", 850, 3700));
        lances.add(new EmpresaInteressada("I154", 950, 4300));
        lances.add(new EmpresaInteressada("I155", 1050, 4700));
        lances.add(new EmpresaInteressada("I156", 600, 2500));
        lances.add(new EmpresaInteressada("I157", 700, 2800));
        lances.add(new EmpresaInteressada("I158", 800, 3400));
        lances.add(new EmpresaInteressada("I159", 900, 4000));
        lances.add(new EmpresaInteressada("I160", 1000, 4200));
        lances.add(new EmpresaInteressada("I161", 1100, 4600));
        lances.add(new EmpresaInteressada("I162", 675, 2600));
        lances.add(new EmpresaInteressada("I163", 775, 3000));
        lances.add(new EmpresaInteressada("I164", 875, 3800));
        lances.add(new EmpresaInteressada("I165", 975, 4100));
        lances.add(new EmpresaInteressada("I166", 1075, 4500));
        lances.add(new EmpresaInteressada("I167", 625, 2700));
        lances.add(new EmpresaInteressada("I168", 725, 2900));
        lances.add(new EmpresaInteressada("I169", 825, 3500));
        lances.add(new EmpresaInteressada("I170", 925, 3900));
        lances.add(new EmpresaInteressada("I171", 1025, 4300));
        lances.add(new EmpresaInteressada("I172", 650, 2800));
        lances.add(new EmpresaInteressada("I173", 750, 3100));
        lances.add(new EmpresaInteressada("I174", 850, 3700));
        lances.add(new EmpresaInteressada("I175", 950, 4200));
        lances.add(new EmpresaInteressada("I176", 1050, 4600));
        lances.add(new EmpresaInteressada("I177", 700, 2500));
        lances.add(new EmpresaInteressada("I178", 800, 2900));
        lances.add(new EmpresaInteressada("I179", 900, 3400));
        lances.add(new EmpresaInteressada("I180", 1000, 4100));
        lances.add(new EmpresaInteressada("I181", 1100, 4700));
        lances.add(new EmpresaInteressada("I182", 675, 2600));
        lances.add(new EmpresaInteressada("I183", 775, 3000));
        lances.add(new EmpresaInteressada("I184", 875, 3600));
        lances.add(new EmpresaInteressada("I185", 975, 4000));
        lances.add(new EmpresaInteressada("I186", 1075, 4500));
        lances.add(new EmpresaInteressada("I187", 625, 2500));
        lances.add(new EmpresaInteressada("I188", 725, 2900));
        lances.add(new EmpresaInteressada("I189", 825, 3400));
        lances.add(new EmpresaInteressada("I190", 925, 3800));
        lances.add(new EmpresaInteressada("I191", 1025, 4100));
        lances.add(new EmpresaInteressada("I192", 650, 2700));
        lances.add(new EmpresaInteressada("I193", 750, 3000));
        lances.add(new EmpresaInteressada("I194", 850, 3600));
        lances.add(new EmpresaInteressada("I195", 950, 4000));
        lances.add(new EmpresaInteressada("I196", 1050, 4500));
        lances.add(new EmpresaInteressada("I197", 700, 2600));
        lances.add(new EmpresaInteressada("I198", 800, 3100));
        lances.add(new EmpresaInteressada("I199", 900, 3500));
        lances.add(new EmpresaInteressada("I200", 1000, 4200));
        lances.add(new EmpresaInteressada("I201", 650, 2900));
        lances.add(new EmpresaInteressada("I202", 750, 3100));
        lances.add(new EmpresaInteressada("I203", 850, 3700));
        lances.add(new EmpresaInteressada("I204", 950, 4300));
        lances.add(new EmpresaInteressada("I205", 1050, 4700));
        lances.add(new EmpresaInteressada("I206", 600, 2500));
        lances.add(new EmpresaInteressada("I207", 700, 2800));
        lances.add(new EmpresaInteressada("I208", 800, 3400));
        lances.add(new EmpresaInteressada("I209", 900, 4000));
        lances.add(new EmpresaInteressada("I210", 1000, 4200));
        lances.add(new EmpresaInteressada("I211", 1100, 4600));
        lances.add(new EmpresaInteressada("I212", 675, 2600));
        lances.add(new EmpresaInteressada("I213", 775, 3000));
        lances.add(new EmpresaInteressada("I214", 875, 3800));
        lances.add(new EmpresaInteressada("I215", 975, 4100));
        lances.add(new EmpresaInteressada("I216", 1075, 4500));
        lances.add(new EmpresaInteressada("I217", 625, 2700));
        lances.add(new EmpresaInteressada("I218", 725, 2900));
        lances.add(new EmpresaInteressada("I219", 825, 3500));
        lances.add(new EmpresaInteressada("I220", 925, 3900));
        lances.add(new EmpresaInteressada("I221", 1025, 4300));
        lances.add(new EmpresaInteressada("I222", 650, 2800));
        lances.add(new EmpresaInteressada("I223", 750, 3100));
        lances.add(new EmpresaInteressada("I224", 850, 3700));
        lances.add(new EmpresaInteressada("I225", 950, 4200));
        lances.add(new EmpresaInteressada("I226", 1050, 4600));
        lances.add(new EmpresaInteressada("I227", 700, 2500));
        lances.add(new EmpresaInteressada("I228", 800, 2900));
        lances.add(new EmpresaInteressada("I229", 900, 3400));
        lances.add(new EmpresaInteressada("I230", 1000, 4100));
        lances.add(new EmpresaInteressada("I231", 1100, 4700));
        lances.add(new EmpresaInteressada("I232", 675, 2600));
        lances.add(new EmpresaInteressada("I233", 775, 3000));
        lances.add(new EmpresaInteressada("I234", 875, 3600));
        lances.add(new EmpresaInteressada("I235", 975, 4000));
        lances.add(new EmpresaInteressada("I236", 1075, 4500));
        lances.add(new EmpresaInteressada("I237", 625, 2500));
        lances.add(new EmpresaInteressada("I238", 725, 2900));
        lances.add(new EmpresaInteressada("I239", 825, 3400));
        lances.add(new EmpresaInteressada("I240", 925, 3800));
        lances.add(new EmpresaInteressada("I241", 1025, 4100));
        lances.add(new EmpresaInteressada("I242", 650, 2700));
        lances.add(new EmpresaInteressada("I243", 750, 3000));
        lances.add(new EmpresaInteressada("I244", 850, 3600));
        lances.add(new EmpresaInteressada("I245", 950, 4000));
        lances.add(new EmpresaInteressada("I246", 1050, 4500));
        lances.add(new EmpresaInteressada("I247", 700, 2600));
        lances.add(new EmpresaInteressada("I248", 800, 3100));
        lances.add(new EmpresaInteressada("I249", 900, 3500));
        lances.add(new EmpresaInteressada("I250", 1000, 4200));
        lances.add(new EmpresaInteressada("I251", 1100, 4600));
        lances.add(new EmpresaInteressada("I252", 675, 2700));
        lances.add(new EmpresaInteressada("I253", 775, 2900));
        lances.add(new EmpresaInteressada("I254", 875, 3600));
        lances.add(new EmpresaInteressada("I255", 975, 4000));
        lances.add(new EmpresaInteressada("I256", 1075, 4500));
        lances.add(new EmpresaInteressada("I257", 625, 2500));
        lances.add(new EmpresaInteressada("I258", 725, 2900));
        lances.add(new EmpresaInteressada("I259", 825, 3400));
        lances.add(new EmpresaInteressada("I260", 925, 3800));
        lances.add(new EmpresaInteressada("I261", 1025, 4100));
        lances.add(new EmpresaInteressada("I262", 650, 2700));
        lances.add(new EmpresaInteressada("I263", 750, 3000));
        lances.add(new EmpresaInteressada("I264", 850, 3600));
        lances.add(new EmpresaInteressada("I265", 950, 4000));
        lances.add(new EmpresaInteressada("I266", 1050, 4500));
        lances.add(new EmpresaInteressada("I267", 700, 2600));
        lances.add(new EmpresaInteressada("I268", 800, 3100));
        lances.add(new EmpresaInteressada("I269", 900, 3500));
        lances.add(new EmpresaInteressada("I270", 1000, 4200));
        lances.add(new EmpresaInteressada("I271", 1100, 4600));
        lances.add(new EmpresaInteressada("I272", 675, 2700));
        lances.add(new EmpresaInteressada("I273", 775, 2900));
        lances.add(new EmpresaInteressada("I274", 875, 3600));
        lances.add(new EmpresaInteressada("I275", 975, 4000));
        lances.add(new EmpresaInteressada("I276", 1075, 4500));
        lances.add(new EmpresaInteressada("I277", 625, 2500));
        lances.add(new EmpresaInteressada("I278", 725, 2900));
        lances.add(new EmpresaInteressada("I279", 825, 3400));
        lances.add(new EmpresaInteressada("I280", 925, 3800));
        lances.add(new EmpresaInteressada("I281", 1025, 4100));
        lances.add(new EmpresaInteressada("I282", 650, 2700));
        lances.add(new EmpresaInteressada("I283", 750, 3000));
        lances.add(new EmpresaInteressada("I284", 850, 3600));
        lances.add(new EmpresaInteressada("I285", 950, 4000));
        lances.add(new EmpresaInteressada("I286", 1050, 4500));
        lances.add(new EmpresaInteressada("I287", 700, 2600));
        lances.add(new EmpresaInteressada("I288", 800, 3100));
        lances.add(new EmpresaInteressada("I289", 900, 3500));
        lances.add(new EmpresaInteressada("I290", 1000, 4200));
        lances.add(new EmpresaInteressada("I291", 1100, 4600));
        lances.add(new EmpresaInteressada("I292", 675, 2700));
        lances.add(new EmpresaInteressada("I293", 775, 2900));
        lances.add(new EmpresaInteressada("I294", 875, 3600));
        lances.add(new EmpresaInteressada("I295", 975, 4000));
        lances.add(new EmpresaInteressada("I296", 1075, 4500));
        lances.add(new EmpresaInteressada("I297", 625, 2500));
        lances.add(new EmpresaInteressada("I298", 725, 2900));
        lances.add(new EmpresaInteressada("I299", 825, 3400));
        lances.add(new EmpresaInteressada("I300", 925, 3800));

        LeilaoDinamico solver = new LeilaoDinamico();
        long startTime = System.nanoTime(); // Captura o tempo inicial de execução
        MelhorResultado resultado = solver.resolverLeilao(quantidadeDisponivel, lances);
        long endTime = System.nanoTime(); // Captura o tempo final de execução

        double duration = (endTime - startTime) / 1_000_000_000.0; // Converte para segundos

        // Exibe o melhor lucro, a energia sobrando e os lances selecionados
        System.out.println("Melhor lucro: " + resultado.getLucroMaximo());
        System.out.println("Energia sobrando: " + resultado.getEnergiaSobrando() + " MW");
        System.out.println("Lances selecionados:");
        for (EmpresaInteressada lance : resultado.getMelhorSelecao()) {
            System.out.println(
                    "ID: " + lance.getId() + ", Quantidade: " + lance.getQuantidade() + ", Valor: " + lance.getValor());
        }

        // Exibe o tempo total de execução
        System.out.println("Tempo total de execução: " + duration + " segundos");
    }
}

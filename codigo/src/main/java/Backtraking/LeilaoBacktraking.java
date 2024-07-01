import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AlgoritmoOtimizacao {
    private List<Oferta> melhorSelecao; // Lista para armazenar a melhor seleção de ofertas encontradas
    private int maiorValorTotal; // Atributo para armazenar o maior valor total

    // Construtor da classe
    public AlgoritmoOtimizacao() {
        this.melhorSelecao = new ArrayList<>(); // Inicializa a lista de melhor seleção
        this.maiorValorTotal = 0; // Inicializa o maior valor total como 0
    }

    // Método para resolver o problema de otimização
    public int calcularMelhorValor(List<Oferta> ofertas, int limiteEnergia) {
        maiorValorTotal = 0; // Reinicia o maior valor total a cada chamada
        melhorSelecao.clear(); // Limpa a melhor seleção anterior

        // Ordena as ofertas por energia (em ordem crescente)
        Collections.sort(ofertas, Comparator.comparingInt(Oferta::getEnergia));

        // Chama o método de backtracking para encontrar a melhor seleção
        buscarMelhorSelecao(ofertas, limiteEnergia, 0, 0, new ArrayList<>());
        return maiorValorTotal; // Retorna o maior valor total encontrado
    }

    // Método recursivo de backtracking com poda
    private void buscarMelhorSelecao(List<Oferta> ofertas, int energiaRestante, int valorAtual, int indiceAtual,
                                     List<Oferta> selecaoAtual) {
        // Condição de término: se a energia restante é menor que zero ou se percorreu todas as ofertas
        if (energiaRestante < 0 || indiceAtual >= ofertas.size()) {
            // Atualiza a melhor seleção e o maior valor total se encontrou uma melhor solução
            if (valorAtual > maiorValorTotal) {
                melhorSelecao = new ArrayList<>(selecaoAtual); // Copia a seleção atual para a melhor seleção
                maiorValorTotal = valorAtual; // Atualiza o maior valor total
            }
            return; // Retorna para encerrar a execução do método
        }

        // Poda: se a seleção atual já não pode superar a melhor encontrada, retorne
        if (valorAtual + estimarValorMaximo(ofertas, indiceAtual, energiaRestante) <= maiorValorTotal) {
            return;
        }

        // Não inclui a oferta atual e segue para a próxima
        buscarMelhorSelecao(ofertas, energiaRestante, valorAtual, indiceAtual + 1, selecaoAtual);

        // Inclui a oferta atual se a energia permitir
        Oferta oferta = ofertas.get(indiceAtual); // Obtém a oferta atual
        if (energiaRestante >= oferta.getEnergia()) {
            selecaoAtual.add(oferta); // Adiciona a oferta atual à seleção
            // Chama recursivamente para a próxima oferta com energia e valor atualizados
            buscarMelhorSelecao(ofertas, energiaRestante - oferta.getEnergia(), valorAtual + oferta.getValor(), indiceAtual + 1, selecaoAtual);
            selecaoAtual.remove(selecaoAtual.size() - 1); // Remove a oferta atual da seleção (backtrack)
        }
    }

    // Método auxiliar para estimar o valor máximo possível a partir de um ponto dado
    private int estimarValorMaximo(List<Oferta> ofertas, int indiceAtual, int energiaRestante) {
        int valorMaximo = 0;
        for (int i = indiceAtual; i < ofertas.size(); i++) {
            if (energiaRestante >= ofertas.get(i).getEnergia()) {
                energiaRestante -= ofertas.get(i).getEnergia();
                valorMaximo += ofertas.get(i).getValor();
            }
        }
        return valorMaximo;
    }

    // Método para imprimir a melhor seleção de ofertas encontrada
    public void exibirMelhorSelecao() {
        System.out.println("Ofertas Selecionadas:");
        if (melhorSelecao.isEmpty()) {
            System.out.println("Nenhuma oferta selecionada."); // Mensagem se nenhuma oferta foi selecionada
        } else {
            // Imprime cada oferta da melhor seleção
            for (Oferta oferta : melhorSelecao) {
                System.out.println("- Energia: " + oferta.getEnergia() + " MW, Valor: " + oferta.getValor() + " dinheiros");
            }
        }
    }

    // Método para obter a melhor seleção de ofertas
    public List<Oferta> getMelhorSelecao() {
        return melhorSelecao;
    }

    // Método para obter a energia total da melhor seleção
    public int getTotalEnergiaMelhorSelecao() {
        int energiaTotal = 0;
        for (Oferta oferta : melhorSelecao) {
            energiaTotal += oferta.getEnergia();
        }
        return energiaTotal;
    }

    // Método para obter o maior valor total
    public int getMaiorValor() {
        return maiorValorTotal;
    }

    public static void main(String[] args) {
        // Nosso caso de uso
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
        int limiteEnergia = 8000; // Quantidade de energia disponível

        AlgoritmoOtimizacao algoritmo = new AlgoritmoOtimizacao();
        int maiorValor = algoritmo.calcularMelhorValor(ofertas, limiteEnergia);
        algoritmo.exibirMelhorSelecao();
        System.out.println("Maior valor total: " + maiorValor);
    }
}
     

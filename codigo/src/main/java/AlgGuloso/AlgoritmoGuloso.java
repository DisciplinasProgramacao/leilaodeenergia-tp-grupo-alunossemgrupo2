package AlgGuloso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlgoritmoGuloso {
    public static void main(String[] args) {
        List<Oferta> ofertaTeste1 = new ArrayList<>(Arrays.asList(
            new Oferta("E1", 430, 1043),
            new Oferta("E2", 428, 1188),
            new Oferta("E3", 410, 1565),
            new Oferta("E4", 385, 1333),
            new Oferta("E5", 399, 1214),
            new Oferta("E6", 382, 1498),
            new Oferta("E7", 416, 1540),
            new Oferta("E8", 436, 1172),
            new Oferta("E9", 416, 1386),
            new Oferta("E10", 423, 1097),
            new Oferta("E11", 400, 1463),
            new Oferta("E12", 406, 1353),
            new Oferta("E13", 403, 1568),
            new Oferta("E14", 390, 1228),
            new Oferta("E15", 387, 1542),
            new Oferta("E16", 390, 1206),
            new Oferta("E17", 430, 1175),
            new Oferta("E18", 397, 1492),
            new Oferta("E19", 392, 1293),
            new Oferta("E20", 393, 1533),
            new Oferta("E21", 439, 1149),
            new Oferta("E22", 403, 1277),
            new Oferta("E23", 415, 1624),
            new Oferta("E24", 387, 1280),
            new Oferta("E25", 417, 1330)
        ));

        List<Oferta> ofertaTeste2 = new ArrayList<>(Arrays.asList(
            new Oferta("E1", 313, 1496),
            new Oferta("E2", 398, 1768),
            new Oferta("E3", 240, 1210),
            new Oferta("E4", 433, 2327),
            new Oferta("E5", 301, 1263),
            new Oferta("E6", 297, 1499),
            new Oferta("E7", 232, 1209),
            new Oferta("E8", 614, 2342),
            new Oferta("E9", 558, 2983),
            new Oferta("E10", 495, 2259),
            new Oferta("E11", 310, 1381),
            new Oferta("E12", 213, 961),
            new Oferta("E13", 213, 1115),
            new Oferta("E14", 346, 1552),
            new Oferta("E15", 385, 2023),
            new Oferta("E16", 240, 1234),
            new Oferta("E17", 483, 2828),
            new Oferta("E18", 487, 2617),
            new Oferta("E19", 709, 2328),
            new Oferta("E20", 358, 1847),
            new Oferta("E21", 467, 2038),
            new Oferta("E22", 363, 2007),
            new Oferta("E23", 279, 1311),
            new Oferta("E24", 589, 3164),
            new Oferta("E25", 476, 2480)
        ));
        List<Oferta> ofertasCopiaTest1 = new ArrayList<>(ofertaTeste1);
        List<Oferta> ofertasCopiaTest2 = new ArrayList<>(ofertaTeste2);
        int valorMaximizado = 0;
        int valorMaximizadoVM = 0;
        int energiaTotal = 8000;

        System.out.println("Guloso por maior valor");
         valorMaximizado += GulosoMaiorValor(ofertaTeste1, energiaTotal);
        System.out.println("Valor máximo obtido: " + valorMaximizado);

        System.out.println("Guloso por Valor/Megawatt");
         valorMaximizadoVM += GulosoMelhorCustoBeneficio(ofertasCopiaTest1, energiaTotal);
        System.out.println("Valor máximo obtido por Valor/Megawatt: " + valorMaximizadoVM);
    }

    public static int GulosoMaiorValor(List<Oferta> ofertas, int energiaTotal) {
        int valorTotal = 0;
        ofertas.sort((o1, o2) -> Integer.compare(o2.getValor(), o1.getValor()));
        for (Oferta oferta : ofertas) {
            if (energiaTotal - oferta.getMegawatts() >= 0) {
                energiaTotal -= oferta.getMegawatts();
                valorTotal += oferta.getValor();
                System.out.println("Selecionando oferta: " + oferta);
            }
        }
        return valorTotal;
    }

    public static int GulosoMelhorCustoBeneficio(List<Oferta> ofertas, int energiaTotal) {
        int valorTotal = 0;
        ofertas.sort((o1, o2) -> Double.compare((double)o2.getValor() / o2.getMegawatts(), (double)o1.getValor() / o1.getMegawatts()));
        for (Oferta oferta : ofertas) {
            if (energiaTotal - oferta.getMegawatts() >= 0) {
                energiaTotal -= oferta.getMegawatts();
                valorTotal += oferta.getValor();
                System.out.println("Selecionando oferta (VM): " + oferta);
            }
        }
        return valorTotal;
    }
}

class Oferta {
    private String empresa;
    private int megawatts;
    private int valor;

    public Oferta(String empresa, int megawatts, int valor) {
        this.empresa = empresa;
        this.megawatts = megawatts;
        this.valor = valor;
    }

    public int getMegawatts() {
        return megawatts;
    }

    public int getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return String.format("%s: %d MW, %d dinheiros", empresa, megawatts, valor);
    }
}

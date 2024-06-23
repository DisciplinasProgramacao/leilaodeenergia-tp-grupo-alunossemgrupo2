package AlgGuloso;

public class Oferta implements Comparable<Oferta> {
    private String empresa;
    private int megawatts;
    private int valor;

    public Oferta(String empresa, int megawatts, int valor) {
        this.empresa = empresa;
        this.megawatts = megawatts;
        this.valor = valor;
    }

    public String getEmpresa() {
        return empresa;
    }

    public int getMegawatts() {
        return megawatts;
    }

    public int getValor() {
        return valor;
    }

    // Método de comparação que pode ser ajustado para diferentes estratégias
    @Override
    public int compareTo(Oferta outra) {
        // Comparar por valor por megawatt (default)
        double atualVm = (double) this.valor / this.megawatts;
        double outraVm = (double) outra.valor / outra.megawatts;
        return Double.compare(outraVm, atualVm);
    }

    @Override
    public String toString() {
        return String.format("%s: %d MW por %d dinheiros", empresa, megawatts, valor);
    }
}

class Pessoa_Juridica extends Pessoa {

    private int workers;

    Pessoa_Juridica(double Anual_income, String name, int workers) {

        super(Anual_income, name);
        this.workers = workers;
    }

    public double tax() {

        double Anual_income = getIncome();
        double taxes = 0.16;
        if (this.workers > 10) {
            taxes = 0.14;
        }
        double totaltaxes = (Anual_income * taxes);

        return totaltaxes;
    }

    @Override
    public String toString() {

        return super.toString() + " Número de trabalhadores: " + workers + " Imposto a pagar: " + tax();
    }
}

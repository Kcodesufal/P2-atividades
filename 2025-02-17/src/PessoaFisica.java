class PessoaFisica extends Pessoa {

    private double health_expenses;

    PessoaFisica(double Anual_income, String name, double health_expenses) {

        super(Anual_income, name);
        this.health_expenses = health_expenses;
    }

    public double tax() {

        double taxes = 0.15;
        double Anual_income = getIncome();
        if (Anual_income > 20000) {
            taxes = 0.25;
        }
        double totaltaxes = (Anual_income * taxes) - (health_expenses * 0.5);
        return totaltaxes;
    }

    @Override
    public String toString() {

        return super.toString() + " Gastos com Saúde: " + health_expenses + " Imposto a pagar: " + tax();
    }
}

abstract class Pessoa {
    private double Anual_income;
    private String name;

    public Pessoa(double Anual_income, String name) {
        this.Anual_income = Anual_income;
        this.name = name;
    }

    public double getIncome() {
        return Anual_income;
    }

    public String getName() {
        return name;
    }

    abstract double tax();

    @Override
    public String toString() {

        return "Nome: " + name + " Renda anual: " + Anual_income;
    }
}

class Pessoa_Fisica extends Pessoa {

    private double health_expenses;

    Pessoa_Fisica(double Anual_income, String name, double health_expenses) {

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
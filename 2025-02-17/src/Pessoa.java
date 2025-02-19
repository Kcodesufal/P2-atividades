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


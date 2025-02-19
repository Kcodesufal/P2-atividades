class stove extends Product {

    private int burners;

    public stove(double price, String name, int burners) {
        super(price, name);
        this.burners = burners;
    }

    public int getBurners() {
        return this.burners;
    }

    @Override
    public String toString() {

        return "Tipo: stove " + super.toString() + " Burners: " + this.burners;
    }
}

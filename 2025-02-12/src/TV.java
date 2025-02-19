class TV extends Product {

    private int inches;

    public TV(double price, String name, int inches) {
        super(price, name);
        this.inches = inches;
    }

    public int getInches() {
        return this.inches;
    }

    @Override
    public String toString() {

        return "Tipo: TV " + super.toString() + " Polegadas: " + this.inches;
    }
}

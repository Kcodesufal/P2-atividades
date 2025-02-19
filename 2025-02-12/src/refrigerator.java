class refrigerator extends Product {

    private int size;

    public refrigerator(double price, String name, int size) {
        super(price, name);
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public String toString() {

        return "Tipo: refrigerator " + super.toString() + " tamanho: " + this.size + " centímetros";
    }
}

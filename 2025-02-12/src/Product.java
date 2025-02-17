
public class Product {
    private double price;
    private String Brand;

    public Product(double price, String name) {

        this.price = price;
        this.Brand = name;
    }

    public String getBrand() {
        return this.Brand;
    }

    public double getPrice() {

        return this.price;
    }

    @Override
    public String toString() {

        return "Marca do Produto: " + this.Brand + " Preço: " + this.price;

    }
}

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

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

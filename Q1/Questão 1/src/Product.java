

public class Product {
    private double price;
    private String name;

    public Product (double price, String name)
    {

        this.price = price;
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public double getPrice () {

        return this.price;
    }
    
    @Override
    public String toString() {

        return "Nome do Produto:" + this.name + "Preço:" + this.price;

    }
}

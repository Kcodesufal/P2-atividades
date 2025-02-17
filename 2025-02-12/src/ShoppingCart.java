import java.util.ArrayList;

public class ShoppingCart {

    private int customerID;
    private ArrayList<Product> product;

    public ShoppingCart(int customerID) {

        this.product = new ArrayList<>();
        this.customerID = customerID;
    }

    public void addProduct(Product prod) {
        

        product.add(prod);
    }

    public void removeProduct(int i) {

        product.remove(product.get(i));

    }

    public String getContents() {
        String content = "Lista de produtos:\n";
        for (int i = 0; i < product.size(); i++) {

            content = content +"ID do produto: " + i + " " + product.get(i).toString() + "\n";

        }
        return content;
    }

    public int getCustomerID() {
        return customerID;
    }
    public int getItemCont()
    {
        return product.size();
    }
    public double getTotalPrice()
    {
        double total = 0;
        for (int i = 0; i < product.size(); i++)
        {
            total += product.get(i).getPrice();
        }
        return total;
    }
}

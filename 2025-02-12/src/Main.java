import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        
    Scanner sc = new Scanner(System.in);
    String dir = "sair";
    System.out.println("Seja bem-vindo! Por favor, insira o ID:\n");
    int id = sc.nextInt();

    ShoppingCart mycart = new ShoppingCart(id);
    System.out.println("Certo. O que deseja realizar?");
    System.out.println("\nSelecione uma das opções:");
    System.out.println("add -> Adiciona um item novo");
    System.out.println("remove -> Remove um item");
    System.out.println("list -> Lista todos os itens");
    System.out.println("cont -> conta todos os itens");
    System.out.println("tprice -> informa o preço total");
    System.out.println("id -> informa o ID do carrinho");
    System.out.println("sair -> sai do programa");
    System.out.println("help -> revê este texto");
        do {

            
            System.out.println("Insira o comando");
            dir = sc.next();
            if (dir.equals("add")) {
                System.out.println("Por favor, informe o preço:");
                double price = sc.nextDouble();
                System.out.println("Por favor, informe a marca do produto:");
                String name = sc.next();
                System.out.println("Por favor informe o tipo do produto:");
                System.out.println("TV      refrigerator       stove");
                String typeproduct = sc.next();
                if (typeproduct.equals("TV"))
                {
                    System.out.println("Informe a quantia de polegadas: ");
                    int inches = sc.nextInt();
                   TV prod = new TV(price, name, inches);
                   mycart.addProduct(prod);
                }
                else if (typeproduct.equals("refrigerator"))
                {   
                    System.out.println("Informe o tamanho: ");
                    int size = sc.nextInt();
                    refrigerator prod = new refrigerator(price, name, size);
                    mycart.addProduct(prod);
                }
                else if (typeproduct.equals("stove")){
                    System.out.println("Informe o 'burners': ");
                int burners = sc.nextInt();
                stove prod = new stove(price, name, burners);
                mycart.addProduct(prod);
                }
            }
            else if (dir.equals("remove"))
            {
                System.out.println("Informe o número do produto a ser removido:");
                int i = sc.nextInt();
                mycart.removeProduct(i);
            }
            else if (dir.equals("list"))
            {
                System.out.println(mycart.getContents());
            }
            else if (dir.equals("cont"))
            {
                System.out.println("Foram adicionados " +mycart.getItemCont() + " produtos ao carrinho");
            }
            else if (dir.equals("tprice"))
            {
                System.out.println("Valor total dos produtos: " +mycart.getTotalPrice());
            }
            else if (dir.equals("id"))
            {
               System.out.println("Seu id é: " + mycart.getCustomerID());
            }
            else if (dir.equals("help"))
            {
                System.out.println("Certo. O que deseja realizar?");
                System.out.println("\nSelecione uma das opções:");
                System.out.println("add -> Adiciona um item novo");
                System.out.println("remove -> Remove um item");
                System.out.println("list -> Lista todos os itens");
                System.out.println("cont -> conta todos os itens");
                System.out.println("tprice -> informa o preço total");
                System.out.println("id -> informa o ID do carrinho");
                System.out.println("sair -> sai do programa");
                System.out.println("help -> revê este texto");
            }


        } while (!dir.equals("sair"));


        sc.close();

    }

}

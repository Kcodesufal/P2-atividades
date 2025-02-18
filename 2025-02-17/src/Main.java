import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        Clientes Client = new Clientes();
        System.out.println("Enter the number of tax payers:");
        int taxpayers = sc.nextInt();

        for (int i = 0; i < taxpayers; i++) {

            System.out.println("Tax payer #" + (i + 1) + " data:");
            System.out.println("Individual or company (i/c)?");
            String persontype = sc.next();
            System.out.println("Name: ");
            String name = sc.next();
            System.out.println("Anual income: ");
            double anual_income = sc.nextDouble();
            if (persontype.equals("i")) {
                System.out.println("Health expenditures: ");
                double health_expenses = sc.nextDouble();
                Pessoa p = new Pessoa_Fisica(anual_income, name, health_expenses);
                Client.addPessoa(p);

            } else if (persontype.equals("c")) {

                System.out.println("Number of employees: ");
                int workers = sc.nextInt();
                Pessoa p = new Pessoa_Juridica(anual_income, name, workers);
                Client.addPessoa(p);
            }

        }
        System.out.println(Client.get_tax_list());
        System.out.println(Client.get_all_taxes());
        sc.close();
    }
}
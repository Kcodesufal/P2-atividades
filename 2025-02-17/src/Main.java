import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        RegistrodePessoas taxpayerslist = new RegistrodePessoas();
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
                Pessoa p = new PessoaFisica(anual_income, name, health_expenses);
                taxpayerslist.addPessoa(p);

            } else if (persontype.equals("c")) {

                System.out.println("Number of employees: ");
                int workers = sc.nextInt();
                Pessoa p = new PessoaJuridica(anual_income, name, workers);
                taxpayerslist.addPessoa(p);
            }

        }
        System.out.println(taxpayerslist.GetTaxList());
        System.out.println(taxpayerslist.PrintAllTaxes());

        System.out.println();
        sc.close();
    }
}
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter account data:");
        System.out.print("Number:");
        int number = sc.nextInt();
        sc.nextLine();
        System.out.print("Holder:");
        String holder = sc.nextLine();
        System.out.print("Initial Balance:");
        double initial_balance = sc.nextDouble();
        System.out.print("Withdraw limit:");
        double withdrawLimit = sc.nextDouble();
        Account myAcc = new Account(number, holder, initial_balance, withdrawLimit);
        while (true) {
            System.out.println("Would you like to: 'Deposit', 'Withdraw' or 'Exit'?");
            String answer = sc.next();

            if (answer.equals("Exit")) {
                System.out.println("See you later!");
                break;
            } else if (answer.equals("Deposit")) {
                System.out.print("Enter amount for deposit: ");
                double amount = sc.nextDouble();
                myAcc.deposit(amount);
                System.out.println("new balance: " + myAcc.getBalance());
            } else if (answer.equals("Withdraw")) {
                System.out.print("Enter amount for Withdraw: ");
                double amount = sc.nextDouble();
                try {
                    myAcc.withdraw(amount);
                    System.out.println("new balance: " + myAcc.getBalance());
                } catch (Exception e) {
                    System.out.println("Withdraw error: " + e.getMessage());
                }
            }
        }

        sc.close();
    }
}
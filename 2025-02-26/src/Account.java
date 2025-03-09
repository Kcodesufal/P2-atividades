import Exceptions.*;

public class Account {

    private int number;
    private String holder;
    private double balance;
    private double withdrawLimit;

    public Account(int number, String holder, double balance, double withdrawLimit) {
        this.number = number;
        this.holder = holder;
        this.balance = balance;
        this.withdrawLimit = withdrawLimit;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getWithdrawLimit() {
        return withdrawLimit;
    }

    public void setWithdrawLimit(double withdrawLimit) {
        this.withdrawLimit = withdrawLimit;
    }

    public void deposit(double amount) {

        this.balance += amount;
    }

    public void withdraw(double amount) {
        if (amount > withdrawLimit){
            throw new WithdrawLimitExceededException("The amount exceeds withdraw limit");
        }
        if (balance - amount <= 0) {
            throw new InsufficientBalanceException("Not enough balance");
        }
        if (balance < 0) {
            throw new NegativeBalanceException("Balance is negative!");
        }
        this.balance -= amount;
    }

@Override
    public String toString() {
        return String.format("Account Number: %d Holder: %s Balance: %.2f Withdraw Limit: %.2f",
                number, holder, balance, withdrawLimit);
    }
    

    
}

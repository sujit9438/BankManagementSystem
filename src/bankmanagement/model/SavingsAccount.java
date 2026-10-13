package bankmanagement.model;

import bankmanagement.exception.InsufficientBalanceException;
import bankmanagement.exception.InvalidAmountException;

public class SavingsAccount extends Account {

    private double interestRate;
    private int withdrawalLimit;
    private int withdrawalCount;

    public SavingsAccount(String accountNumber, Customer customer,
                          double balance, String pin,
                          double interestRate, int withdrawalLimit) {

        super(accountNumber, customer, balance, pin, "Savings", 1000);

        this.interestRate = interestRate;
        this.withdrawalLimit = withdrawalLimit;
    }

    public double calculateInterest() {
        return balance * interestRate / 100;
    }

    public void addInterest() {
        balance += calculateInterest();
    }

    @Override
    public void withdraw(double amount)
            throws InvalidAmountException, InsufficientBalanceException {

        if (amount <= 0) {
            throw new InvalidAmountException("Invalid Amount");
        } else if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient Balance");
        } else if (withdrawalCount >= withdrawalLimit) {
            throw new InvalidAmountException("Withdrawal Limit Exceeded");
        } else {
            balance -= amount;
            withdrawalCount++;
        }
    }
}
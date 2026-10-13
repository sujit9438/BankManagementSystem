
package bankmanagement.service;

import bankmanagement.model.Account;
import bankmanagement.model.Customer;

public class Bank {

    private Account[] accounts;
    private int accountCount;

    public Bank(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive.");
        }

        accounts = new Account[capacity];
        accountCount = 0;
    }

    public void addAccount(Account account) {
        if (account == null) {
            System.out.println("Account cannot be null.");
            return;
        }

        if (accountCount >= accounts.length) {
            System.out.println("Bank account storage is full.");
            return;
        }

        if (searchByAccountNumber(account.getAccountNumber()) != null) {
            System.out.println("Account number already exists.");
            return;
        }

        accounts[accountCount] = account;
        accountCount++;

        System.out.println("Account added successfully.");
    }

    public Account searchByAccountNumber(String accountNumber) {
        if (accountNumber == null) {
            return null;
        }

        for (int i = 0; i < accountCount; i++) {
            if (accountNumber.equals(accounts[i].getAccountNumber())) {
                return accounts[i];
            }
        }

        return null;
    }

    public Account[] searchByCustomerName(String customerName) {
        if (customerName == null) {
            return new Account[0];
        }

        int matchCount = 0;

        for (int i = 0; i < accountCount; i++) {
            Customer customer = accounts[i].getCustomer();

            if (customer != null &&
                customerName.equalsIgnoreCase(customer.getCustomerName())) {
                matchCount++;
            }
        }

        Account[] matches = new Account[matchCount];
        int index = 0;

        for (int i = 0; i < accountCount; i++) {
            Customer customer = accounts[i].getCustomer();

            if (customer != null &&
                customerName.equalsIgnoreCase(customer.getCustomerName())) {
                matches[index] = accounts[i];
                index++;
            }
        }

        return matches;
    }

    public void displayAllAccounts() {
        if (accountCount == 0) {
            System.out.println("No accounts available.");
            return;
        }

        for (int i = 0; i < accountCount; i++) {
            System.out.println("\nAccount " + (i + 1));
            accounts[i].displayAccountDetails();
        }
    }

    public int getAccountCount() {
        return accountCount;
    }
    
}


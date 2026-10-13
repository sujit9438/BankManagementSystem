
package bankmanagement.main;

import java.util.Scanner;

import bankmanagement.model.Account;
import bankmanagement.model.Customer;
import bankmanagement.model.SavingsAccount;
import bankmanagement.model.CurrentAccount;
import bankmanagement.service.Bank;
import bankmanagement.exception.InvalidAccountException;
import bankmanagement.exception.InvalidAmountException;
import bankmanagement.exception.InvalidPinException;
import bankmanagement.exception.InsufficientBalanceException;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final Bank bank = new Bank(100);

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n========== BANK MANAGEMENT SYSTEM ==========");
            System.out.println("1. Create Savings Account");
            System.out.println("2. Create Current Account");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Transfer Money");
            System.out.println("6. Search Account");
            System.out.println("7. Search Customer");
            System.out.println("8. Display All Accounts");
            System.out.println("9. Change PIN");
            System.out.println("10. Calculate Savings Interest");
            System.out.println("11. Display Total Accounts");
            System.out.println("0. Exit");
            System.out.println("============================================");

            choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    createSavingsAccount();
                    break;

                case 2:
                    createCurrentAccount();
                    break;

                case 3:
                    depositMoney();
                    break;

                case 4:
                    withdrawMoney();
                    break;

                case 5:
                    transferMoney();
                    break;

                case 6:
                    searchAccount();
                    break;

                case 7:
                    searchCustomer();
                    break;

                case 8:
                    bank.displayAllAccounts();
                    break;

                case 9:
                    changePin();
                    break;

                case 10:
                    calculateSavingsInterest();
                    break;

                case 11:
                    System.out.println(
                        "Total accounts created: " +
                        Account.getTotalAccounts()
                    );
                    break;

                case 0:
                    System.out.println("Thank you for using our bank!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 0);

        sc.close();
    }

    // Create a customer
    private static Customer createCustomer() {

        System.out.println("\nEnter Customer Details");

        System.out.print("Customer ID: ");
        String customerId = sc.nextLine();

        System.out.print("Customer Name: ");
        String customerName = sc.nextLine();

        System.out.print("Phone Number: ");
        String phoneNumber = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        int age = readInt("Age: ");

        return new Customer(
            customerId,
            customerName,
            phoneNumber,
            email,
            age
        );
    }

    // Create a savings account
    private static void createSavingsAccount() {

        System.out.println("\n--- Create Savings Account ---");

        System.out.print("Account Number: ");
        String accountNumber = sc.nextLine();

        if (bank.searchByAccountNumber(accountNumber) != null) {
            System.out.println("Account number already exists.");
            return;
        }

        Customer customer = createCustomer();

        double balance = readDouble("Initial Balance: ");

        System.out.print("Create a 4-digit PIN: ");
        String pin = sc.nextLine();

        double interestRate = readDouble("Annual Interest Rate (%): ");

        int withdrawalLimit =
            readInt("Maximum number of withdrawals: ");

        if (balance < 1000 || interestRate < 0
                || withdrawalLimit <= 0) {
            System.out.println("Invalid account details.");
            System.out.println(
                "Initial balance must be at least 1000, " +
                "interest rate cannot be negative, and " +
                "withdrawal limit must be positive."
            );
            return;
        }

        if (!pin.matches("[0-9]{4}")) {
            System.out.println("PIN must contain exactly 4 digits.");
            return;
        }

        SavingsAccount account = new SavingsAccount(
            accountNumber,
            customer,
            balance,
            pin,
            interestRate,
            withdrawalLimit
        );

        bank.addAccount(account);
    }

    // Create a current account
    private static void createCurrentAccount() {

        System.out.println("\n--- Create Current Account ---");

        System.out.print("Account Number: ");
        String accountNumber = sc.nextLine();

        if (bank.searchByAccountNumber(accountNumber) != null) {
            System.out.println("Account number already exists.");
            return;
        }

        Customer customer = createCustomer();

        double balance = readDouble("Initial Balance: ");

        System.out.print("Create a 4-digit PIN: ");
        String pin = sc.nextLine();

        double overdraftLimit =
            readDouble("Overdraft Limit: ");

        if (balance < 1000 || overdraftLimit < 0) {
            System.out.println("Invalid balance or overdraft limit.");
            return;
        }

        if (!pin.matches("[0-9]{4}")) {
            System.out.println("PIN must contain exactly 4 digits.");
            return;
        }

        CurrentAccount account = new CurrentAccount(
            accountNumber,
            customer,
            balance,pin,"Current",1000,
            overdraftLimit
        );

        bank.addAccount(account);
    }

    // Deposit money
    private static void depositMoney() {

        System.out.println("\n--- Deposit Money ---");

        System.out.print("Account Number: ");
        String accountNumber = sc.nextLine();

        Account account =
            bank.searchByAccountNumber(accountNumber);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Enter your PIN: ");
        String pin = sc.nextLine();

        if (!account.validatePin(pin)) {
            System.out.println("Incorrect PIN.");
            return;
        }

        double amount = readDouble("Enter deposit amount: ");

        try {
            account.deposit(amount);
            System.out.println("Deposit successful.");
            System.out.println(
                "Updated Balance: " + account.getBalance()
            );
        } catch (InvalidAmountException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Withdraw money
    private static void withdrawMoney() {

        System.out.println("\n--- Withdraw Money ---");

        System.out.print("Account Number: ");
        String accountNumber = sc.nextLine();

        Account account =
            bank.searchByAccountNumber(accountNumber);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Enter your PIN: ");
        String pin = sc.nextLine();

        if (!account.validatePin(pin)) {
            System.out.println("Incorrect PIN.");
            return;
        }

        double amount = readDouble("Enter withdrawal amount: ");

        try {
            account.withdraw(amount);

            System.out.println("Withdrawal successful.");
            System.out.println(
                "Updated Balance: " + account.getBalance()
            );

        } catch (InvalidAmountException
                | InsufficientBalanceException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // Transfer money
    private static void transferMoney() {

        System.out.println("\n--- Transfer Money ---");

        System.out.print("Sender Account Number: ");
        String senderNumber = sc.nextLine();

        Account sender =
            bank.searchByAccountNumber(senderNumber);

        if (sender == null) {
            System.out.println("Sender account not found.");
            return;
        }

        System.out.print("Enter sender PIN: ");
        String pin = sc.nextLine();

        if (!sender.validatePin(pin)) {
            System.out.println("Incorrect PIN.");
            return;
        }

        System.out.print("Receiver Account Number: ");
        String receiverNumber = sc.nextLine();

        Account receiver =
            bank.searchByAccountNumber(receiverNumber);

        if (receiver == null) {
            System.out.println("Receiver account not found.");
            return;
        }

        if (sender == receiver) {
            System.out.println(
                "Sender and receiver must be different accounts."
            );
            return;
        }

        double amount = readDouble("Enter transfer amount: ");

        try {
            sender.transfer(receiver, amount);

            System.out.println("Transfer successful.");
            System.out.println(
                "Sender Balance: " + sender.getBalance()
            );
            System.out.println(
                "Receiver Balance: " + receiver.getBalance()
            );

        } catch (InvalidAccountException
                | InvalidAmountException
                | InsufficientBalanceException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // Search account by account number
    private static void searchAccount() {

        System.out.println("\n--- Search Account ---");

        System.out.print("Enter Account Number: ");
        String accountNumber = sc.nextLine();

        Account account =
            bank.searchByAccountNumber(accountNumber);

        if (account == null) {
            System.out.println("Account not found.");
        } else {
            account.displayAccountDetails();
        }
    }

    // Search accounts by customer name
    private static void searchCustomer() {

        System.out.println("\n--- Search Customer ---");

        System.out.print("Enter Customer Name: ");
        String customerName = sc.nextLine();

        Account[] matches =
            bank.searchByCustomerName(customerName);

        if (matches.length == 0) {
            System.out.println("No matching customer found.");
        } else {
            for (int i = 0; i < matches.length; i++) {
                System.out.println("\nMatching Account " + (i + 1));
                matches[i].displayAccountDetails();
            }
        }
    }

    // Change PIN
    private static void changePin() {

        System.out.println("\n--- Change PIN ---");

        System.out.print("Account Number: ");
        String accountNumber = sc.nextLine();

        Account account =
            bank.searchByAccountNumber(accountNumber);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Enter old PIN: ");
        String oldPin = sc.nextLine();

        System.out.print("Enter new 4-digit PIN: ");
        String newPin = sc.nextLine();

        if (!newPin.matches("[0-9]{4}")) {
            System.out.println("PIN must contain exactly 4 digits.");
            return;
        }

        try {
            account.changePin(oldPin, newPin);
            System.out.println("PIN changed successfully.");

        } catch (InvalidPinException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Calculate and add savings interest
    private static void calculateSavingsInterest() {

        System.out.println("\n--- Savings Account Interest ---");

        System.out.print("Account Number: ");
        String accountNumber = sc.nextLine();

        Account account =
            bank.searchByAccountNumber(accountNumber);

        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        if (!(account instanceof SavingsAccount)) {
            System.out.println(
                "Interest calculation is available only " +
                "for savings accounts."
            );
            return;
        }

        SavingsAccount savingsAccount =
            (SavingsAccount) account;

        double interest = savingsAccount.calculateInterest();

        System.out.println("Interest Amount: " + interest);

        System.out.print(
            "Do you want to add this interest to the balance? (yes/no): "
        );

        String answer = sc.nextLine();

        if (answer.equalsIgnoreCase("yes")) {
            savingsAccount.addInterest();

            System.out.println("Interest added successfully.");
            System.out.println(
                "Updated Balance: " +
                savingsAccount.getBalance()
            );
        }
    }

    // Read an integer safely
    private static int readInt(String message) {

        while (true) {
            System.out.print(message);
            String input = sc.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    // Read a decimal number safely
    private static double readDouble(String message) {

        while (true) {
            System.out.print(message);
            String input = sc.nextLine();

            try {
                double value = Double.parseDouble(input);

                if (Double.isNaN(value)
                        || Double.isInfinite(value)) {
                    System.out.println(
                        "Please enter a finite number."
                    );
                    continue;
                }

                return value;

            } catch (NumberFormatException e) {
                System.out.println(
                    "Please enter a valid numeric amount."
                );
            }
        }
    }
}
package bankmanagement.model;

import bankmanagement.exception.InsufficientBalanceException;
import bankmanagement.exception.InvalidAccountException;
import bankmanagement.exception.InvalidAmountException;
import bankmanagement.exception.InvalidPinException;

public class Account 
{
	private String accountNumber;
	private Customer customer;
	protected double balance;
	private String pin;
	protected String accountType;
	protected double minimumBalance;
	private static int totalAccounts;
	public Account(String accountNumber, Customer customer, double balance, String pin, String accountType,
			double minimumBalance) 
	{
		this.accountNumber = accountNumber;
		this.customer = customer;
		this.balance = balance;
		this.pin = pin;
		this.accountType = accountType;
		this.minimumBalance = minimumBalance;
		totalAccounts++;
	}
	public String getAccountNumber()
	{
		return accountNumber;
	}
	public double getBalance()
	{
		return balance;
	}
	public boolean validatePin(String enteredPin)
	{
		if(pin.equals(enteredPin))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public void displayAccountDetails()
	{
		System.out.println("Account number: "+accountNumber);
		System.out.println("Balance: "+balance);
		System.out.println("Account Type: "+accountType);
	}
	public void deposit(double amount) throws InvalidAmountException
	{
		if(amount<=0)
		{
			throw new InvalidAmountException("Invalid Amount");
		}
		else
		{
			balance+=amount;
		}
	}
	public void withdraw(double amount) throws InvalidAmountException,InsufficientBalanceException
	{
		if(amount<=0)
		{
			throw new InvalidAmountException("Invalid Amount");
		}
		else if(amount>balance)
		{
			throw new InsufficientBalanceException("Insufficent Balance");
		}
		else
		{
			balance-=amount;
		}
	}
	public void changePin(String oldPin, String newPin) throws InvalidPinException
	{
		if(oldPin.equals(pin))
		{
			if(newPin == null ||newPin.length()!=4)
			{
				throw new InvalidPinException("Invalid Newpin");
			}
			else
			{
				this.pin=newPin;
			}
		}
		else
		{
			throw new InvalidPinException("Wrong Pin");
		}
	}
	public void transfer(Account receiver, double amount) throws InvalidAccountException, InvalidAmountException, InsufficientBalanceException
	{
		if (receiver == null) {
	        throw new InvalidAccountException("Receiver account cannot be null.");
	    }
	    
	    // Step 2: Validate amount
	    if (amount <= 0) {
	        throw new InvalidAmountException("Transfer amount must be greater than zero.");
	    }
	    
	    // Step 3: Check sender balance
	    if (amount > this.balance) {
	        throw new InsufficientBalanceException("Insufficient balance for this transfer.");
	    }
	    
	    // Step 4: Perform the transfer
	    this.balance -= amount;
	    receiver.balance += amount;
	}
	public static int getTotalAccounts()
	{
		return totalAccounts;
	}
	public Customer getCustomer() {
	    return customer;
	}
	
	
}

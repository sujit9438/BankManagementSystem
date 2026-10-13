package bankmanagement.model;

import bankmanagement.exception.InsufficientBalanceException;
import bankmanagement.exception.InvalidAmountException;

public class CurrentAccount extends Account
{
	private double overdraftLimit;

	public CurrentAccount(String accountNumber, Customer customer, double balance, String pin, String accountType,
			double minimumBalance, double overdraftLimit) 
	{
		super(accountNumber, customer, balance, pin, accountType, minimumBalance);
		this.overdraftLimit = overdraftLimit;
	}
	public double getOverdraftLimit()
	{
		return overdraftLimit;
	}
	@Override
	public void withdraw(double amount) throws InvalidAmountException, InsufficientBalanceException
	{
		if(amount <=0)
		{
			throw new InvalidAmountException("Invalid Amount");
		}
		else if(amount > balance+overdraftLimit)
		{
			throw new InsufficientBalanceException("Insufficient Balance");
		}
		else
		{
			balance-=amount;
		}
	}
}

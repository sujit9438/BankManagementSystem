package bankmanagement.model;

public class Customer 
{
	private String customerId;
	private String customerName;
	private String phoneNumber;
	private String email;
	private int age;
	public Customer(String customerId, String customerName, String phoneNumber, String email, int age) {
	
		this.customerId = customerId;
		this.customerName = customerName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.age = age;
	}
	public String getCustomerId() {
		return customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public int getAge() {
		return age;
	}
	public void displayCustomerDetails()

	{

	System.out.println("Customer ID: "+customerId);

	System.out.println("Customer Name: "+customerName);

	System.out.println("Phone Number: "+phoneNumber);

	System.out.println("Email: "+email);

	System.out.println("Customer Age: "+age);

	}
	
}

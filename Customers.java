package librarymanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Customers {
	
	boolean customer_login;
	private String customer_first_name;
    private String customer_last_name;
    private String customer_state;
    private String customer_city;
    private String customer_street_address;
    private String customer_zip_code;
    private String customer_username;
    private String customer_password;
    private Scanner customerChoice;
    private String customer_id;
    private Connection conn;
    
    Customers(){
    	customer_login = false;
    	conn = Main.getConnection();
    	customerChoice = new Scanner(System.in);
    }

	public void addCustomer() {
		System.out.println("What is your first name?");
		customer_first_name = customerChoice.nextLine();
		System.out.println("What is your last name?");
		customer_last_name = customerChoice.nextLine();
		System.out.println("What would you like your username to be?");
		customer_username = customerChoice.nextLine();
		System.out.println("What would you like your password to be?");
		customer_password = customerChoice.nextLine();
		System.out.println("What state do you live in?");
		customer_state = customerChoice.nextLine();
		System.out.println("What city do you live in?");
		customer_city = customerChoice.nextLine();
		System.out.println("What is your street address?");
		customer_street_address = customerChoice.nextLine();
		System.out.println("What is your zip code?");
		customer_zip_code = customerChoice.nextLine();
		
		try{
			PreparedStatement addCustomer = conn.prepareStatement("INSERT INTO customers"
					+ "(customerfirstname, customerlastname, customerusername, customerpassword, customerstate, customercity, "
					+ "customerstreetaddress, customerzipcode) "
					+ "VALUES ('" + customer_first_name + "','" +  customer_last_name + "','" + customer_username + "','" 
					+ customer_password + "','" +  customer_state + "','" +  customer_city + "','" +  customer_street_address + "','" 
					+  customer_zip_code + "')");
			addCustomer.executeUpdate();
		} catch(Exception e) {System.out.println(e);}
	}
	
	public void customerId() {
		try {
			PreparedStatement getCustomerId = conn.prepareStatement("SELECT customerid FROM customers WHERE customerusername = '" + customer_username 
					+ "' AND customerpassword = '" + customer_password + "'");
			ResultSet result = getCustomerId.executeQuery();
			if(result.next()) {
				customer_id = result.getString("customerid");
			}
		} catch(Exception e) {System.out.println(e);}
		
		System.out.println(customer_id);
	}
	
	public void deleteCustomer() {
		try{
			PreparedStatement deleteCustomer = conn.prepareStatement("DELETE FROM customers WHERE customerfirstname = '" + customer_first_name + 
					"'&& customerlastname = '" + customer_last_name + "'&& customerusername = '" + customer_username + "'&& customerpassword = '" + 
					customer_password + "'&& customerstate = '" + customer_state + "'&& customercity = '" + customer_city + "'&& customerstreetaddress = '" + 
					customer_street_address + "'&& customerzipcode = '" + customer_zip_code + "'");
					
			deleteCustomer.executeUpdate();
		} catch(Exception e) {System.out.println(e);}
		logout();
	}
	
	public void login() {
		System.out.println("What is your username?");
		customer_username = customerChoice.nextLine();
		System.out.println("What is your password?");
		String entered_customer_password = customerChoice.nextLine();
		
		try{
			PreparedStatement customerLogin = conn.prepareStatement("SELECT * FROM customers WHERE customerusername = '" + customer_username + "'");
			ResultSet result = customerLogin.executeQuery();
			addCustomerInfo(result);
			if(entered_customer_password.equals(customer_password)) {
				customer_login = true;
				System.out.println("Successful");
			} else {
				customer_login = false;
				System.out.println("Unsuccesful");
			}
		} catch(Exception e) {System.out.println(e);}
	}
	
	public void logout() {
		customer_login = false;
		customer_first_name = null;
	    customer_last_name = null;
	    customer_state = null;
	    customer_city = null;
	    customer_street_address = null;
	    customer_zip_code = null;
	    customer_username = null;
	    customer_password = null;
	    customer_id = null;
	}
	
	public void addCustomerInfo(ResultSet result) {
		try {
			while(result.next()) {
				customer_password = result.getString("customerpassword");
				customer_first_name = result.getString("customerfirstname");
				customer_last_name = result.getString("customerlastname");
				customer_state = result.getString("customerstate");
				customer_city = result.getString("customercity");
				customer_street_address = result.getString("customerstreetaddress");
				customer_zip_code = result.getString("customerzipcode");
				customer_id = result.getString("customerid");
			}
		} catch(Exception e) {System.out.println(e);}
	}
	
	public void customerInfo() {
		System.out.println("customer first name: " + customer_first_name + "\n" + "customer last name: " + customer_last_name + "\n" + "customer state: " + customer_state + "\n" + "customer city: " + customer_city + "\n" 
		+ "customer street address: " + customer_street_address + "\n" + "customer zip code: " + customer_zip_code + "\n" + "customer username: " + customer_username + "\n" + "customer password: " + customer_password + "\n" 
				+ "customer id: " + customer_id + "\n");	
		try {
			PreparedStatement customersBooks = conn.prepareStatement("SELECT * FROM books WHERE customerid = '" + customer_id + "'");
			ResultSet result = customersBooks.executeQuery();
			Main.b.addBookInfo(result);
		} catch (Exception e) {System.out.println(e);}
	}
	
	public void setCustomerLogin(Boolean customer_login) {
		this.customer_login = customer_login;
	}
	public void setCustomerFirstName(String customer_first_name) {
		this.customer_first_name = customer_first_name;
	}
	public void setCustomerLastName(String customer_last_name) {
		this.customer_last_name = customer_last_name;
	}
	public void setCustomerUsername(String customer_username) {
		this.customer_username = customer_username;
	}
	public void setCustomerPassword(String customer_password) {
		this.customer_password = customer_password;
	}
	public void setCustomerState(String customer_state) {
		this.customer_state = customer_state;
	}
	public void setCustomerStreetAddress(String customer_street_address) {
		this.customer_street_address = customer_street_address;
	}
	public void setCustomerZipCode(String customer_zip_code) {
		this.customer_zip_code = customer_zip_code;
	}
	public void setCustomerCity(String customer_city) {
		this.customer_city = customer_city;
	}
	public void setCustomerId(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getCustomerFirstName() {
		return customer_first_name;
	}
	public String getCustomerLastName() {
		return customer_last_name;
	}
	public String getCustomerUsername() {
		return customer_username;
	}
	public String getCustomerPassword() {
		return customer_password;
	}
	public String getCustomerState() {
		return customer_state;
	}
	public String getCustomerStreetAddress() {
		return customer_street_address;
	}
	public String getCustomerZipCode() {
		return customer_zip_code;
	}
	public String getCustomerCity() {
		return customer_city;
	}
	public String getCustomerId() {
		return customer_id;
	}
	public boolean getCustomerLogin() {
		return customer_login;
	}
}

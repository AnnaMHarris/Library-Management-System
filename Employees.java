package librarymanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Employees {
	
	private boolean employee_login;
	private String employee_first_name;
    private String employee_last_name;
    private String employee_id;
    private String employee_username;
    private String employee_password;
	private Scanner employeeChoice;
	private Connection conn;
	
	Employees(){
		this.employee_login = false;
		employeeChoice = new Scanner(System.in);
		conn = Main.getConnection();
	}
	
	public void login(){
		System.out.println("What is your username?");
		employee_username = employeeChoice.nextLine();
		System.out.println("What is your password?");
		String entered_employee_password = employeeChoice.nextLine();
		
		try{
			PreparedStatement employeeLogin = conn.prepareStatement("SELECT * FROM employees WHERE employeeusername = '" + employee_username + "'");
			ResultSet result = employeeLogin.executeQuery();
			while(result.next()) {
				employee_password = result.getString("employeepassword");
				employee_first_name = result.getString("employeefirstname");
				employee_last_name = result.getString("employeelastname");
				employee_id = result.getString("employeeid");
			}
			
			if(entered_employee_password.equals(employee_password)) {
				employee_login = true;
				System.out.println("Successful");
			} else {
				employee_login = false;
				System.out.println("Unsuccesful");
			}
		} catch(Exception e) {System.out.println(e);}
	}

	public void logout() {
		employee_login = false;
		employee_first_name = null;
	    employee_last_name = null;
	    employee_id = null;
	    employee_username = null;
	    employee_password = null;
	}
	
	public void createEmployee() {
		System.out.println("What is your first name?");
		employee_first_name = employeeChoice.nextLine();
		System.out.println("What is your last name?");
		employee_last_name = employeeChoice.nextLine();
		System.out.println("What would you like your username to be?");
		employee_username = employeeChoice.nextLine();
		System.out.println("What would you like your password to be?");
		employee_password = employeeChoice.nextLine();
		System.out.println("What is your employee number?");
		employee_id = employeeChoice.nextLine();
		
		try{
			PreparedStatement createEmployee = conn.prepareStatement("INSERT INTO employees"
					+ "(employeefirstname, employeelastname, employeeusername, employeepassword, employeeid) "
					+ "VALUES ('" + employee_first_name + "','" +  employee_last_name + "','" + employee_username + "','" 
					+ employee_password + "','" + employee_id + "')");
			createEmployee.executeUpdate();
		} catch(Exception e) {System.out.println(e);}
		
	}
	
	public void deleteEmployee() {
		if(employee_login = false) {
			login();
		}
		try{
			PreparedStatement deleteEmployee = conn.prepareStatement("DELETE FROM employees WHERE employeefirstname = '" + employee_first_name + 
					"'&& employeelastname = '" + employee_last_name + "'&& employeeusername = '" + employee_username + "'&& employeepassword = '" + 
					employee_password + "'&& employeeid = '" + employee_id + "'");
			deleteEmployee.executeUpdate();
		} catch(Exception e) {System.out.println(e);}
		logout();
	}
	
	public void employeeInfo() {
		if(employee_login == false) {
			login();
		}
		System.out.println("employee first name: " + employee_first_name + "\n" + "employee last name: " + employee_last_name + "\n" + "employee id: " + employee_id + "\n" + "employee username: " + employee_username 
				+ "\n" + "employee password: " + employee_password + "\n");
	}
	
	public void customerInfo() {
		//enables employee to search up a customer and print out their profile
		System.out.println("What is their first name?");
		Main.c.setCustomerFirstName(employeeChoice.nextLine());
		System.out.println("What is their last name?");
		Main.c.setCustomerLastName(employeeChoice.nextLine());
		System.out.println("What is their username?");
		Main.c.setCustomerUsername(employeeChoice.nextLine());
	
		try{
			PreparedStatement customerInfo = conn.prepareStatement("SELECT * FROM customers WHERE customerfirstname = '" + Main.c.getCustomerFirstName() + 
					"'&& customerlastname = '" + Main.c.getCustomerLastName() + "'&& customerusername = '" + Main.c.getCustomerUsername() + "'");
			ResultSet result = customerInfo.executeQuery();
			Main.c.addCustomerInfo(result);
		} catch(Exception e) {System.out.println(e);}
		Main.c.customerInfo();
	}
	
	public void setEmployeelogin(Boolean employee_login) {
		this.employee_login = employee_login;
	}
	public boolean getEmployeeLogin() {
		return employee_login;
	}	
}

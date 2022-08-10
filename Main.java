package librarymanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class Main {
	private Scanner userChoice = new Scanner(System.in);
	Connection conn;
	static Books b;
	static Customers c;
	static Employees e;

	public static void main(String[] args){
		// TODO Auto-generated method stub
		b = new Books();
		c = new Customers();
		e = new Employees();
		Main m = new Main();
		m.mainMenu();
		getConnection();
	}
	
	public static Connection getConnection(){
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/myshop";
			String username = "sqluser";
			String password = "password";
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, username, password);
			return conn;
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return null;
	} 
	
	private void mainMenu() {
		boolean error = false;
		while(error == false) {
			//choose between either customer or employee
			System.out.println("Press 1 if you're a library employee");
			System.out.println("Press 2 if you're a library customer");
			System.out.println("Press x to exit\n");
			
			String userChoiceString = userChoice.nextLine();
			switch(userChoiceString) {
				case "x":
					System.exit(0);
					break;
				case "1":
					employeeStepOne();
					c.setCustomerLogin(false);
					break;
				case "2":
					customers();
					e.setEmployeelogin(false);
					break;
			}
			String valid_characters = "x12";
			error = error(userChoiceString, error, valid_characters);
		}
	}
	
	
	private void employeeStepOne(){
		String employeeChoiceStepOne = null;
		boolean error = false;
		while(error == false && employeeChoiceStepOne != "b") {
			System.out.println("Press 1 to login");
	        System.out.println("Press 2 to logout");
	        System.out.println("Press 3 to create staff user");
	        System.out.println("Press 4 to delete staff user");
	        System.out.println("Press b to go back");
	        System.out.println("Press x to exit\n");
	        
	        employeeChoiceStepOne = userChoice.nextLine();
			switch(employeeChoiceStepOne) {
				case "x":
					System.exit(0);
					break;
				case "1":
					e.login();
					if(e.getEmployeeLogin() == true) {
						employeeStepTwo();
					}
					break;
				case "2":
					//logout
					e.logout();
					mainMenu();
					break;
				case "3":
					e.createEmployee();
					break;
				case "4":
					e.deleteEmployee();
					System.out.println("delete employee");
					break;
				case "b":
					mainMenu();
					break;
			}
			String valid_characters = "x1234b";
			error(employeeChoiceStepOne, error, valid_characters);
		}
	}
	
	private void employeeStepTwo(){
		//Second part of employee menu that requires the user to login first
		boolean error = false;
		String employeeChoiceStepTwo = null;
		while(error == false && employeeChoiceStepTwo != "b") {
			System.out.println("Press 1 to add book");
	        System.out.println("Press 2 to check user info");
	        System.out.println("Press 3 to check customer info");
	        System.out.println("Press 4 to search for a book");
	        System.out.println("Press 5 to return a book");
	        System.out.println("Press b to go back");
	        System.out.println("Press x to exit\n");
	        
	        employeeChoiceStepTwo = userChoice.nextLine();
			switch(employeeChoiceStepTwo) {
				case "x":
					System.exit(0);
					break;
				case "1":
					b.addBook();
					break;
				case "2":
					e.employeeInfo();
					break;
				case "3":
					e.customerInfo();
					break;
				case "4":
					b.searchBook();
					break;
				case "5":
					b.returnBook();
					break;
				case "b":
					employeeStepOne();
					break;
					
			}
			String valid_characters = "x12345b";
			error(employeeChoiceStepTwo, error, valid_characters);
		}
	}
	
	private void customers() {
		e.setEmployeelogin(false);
		boolean error = false;
		while(error == false) {
			System.out.println("Press 1 to login");
			System.out.println("Press 2 to logout");
	        System.out.println("Press 3 to search for a book");
	        System.out.println("Press 4 to add user");
	        System.out.println("Press 5 to delete user");
	        System.out.println("Press b to go back");
	        System.out.println("Press x to exit\n");
	        
	        String customerChoiceString = userChoice.nextLine();
			switch(customerChoiceString) {
				case "x":
					System.exit(0);
					break;
				case "1":
					c.login();
					if(c.getCustomerLogin() == true) {
						customerStepTwo();
					}
					break;
				case "2":
					//logout
					c.logout();
					mainMenu();
					break;
				case "3":
					b.searchBook();
					break;
				case "4":
					c.addCustomer();
					break;
				case "5":
					c.deleteCustomer();
					break;
				case "b":
					mainMenu();
					break;
			}
			String valid_characters = "x12345b";
			error(customerChoiceString, error, valid_characters);
		}
	}
	
	public void customerStepTwo() {
		boolean error = false;
		while(error == false) {
			System.out.println("Press 1 to check user info");
			System.out.println("Press 2 to check out a book");
			System.out.println("Press b to go back");
			System.out.println("Press x to exit\n");
			
			String customerChoiceStepTwo = userChoice.nextLine();
			switch(customerChoiceStepTwo) {
				case "x":
					System.exit(0);
					break;
				case "1":
					c.customerInfo();
					break;
				case "2":
					b.checkOut();
					break;
				case "b":
					customers();
					break;
			}
			String valid_characters = "x12b";
			error(customerChoiceStepTwo, error, valid_characters);
		}
	}
		
	private boolean error(String choiceString, boolean error, String valid_characters) {
		if(!valid_characters.contains(choiceString)) {
			System.out.println("Invalid input, please try again\n");
			error = false;
		} else {
			error = true;
		}
		return error;
	}
	
}

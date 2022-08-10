package librarymanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Books {
	
	private boolean checked_out;
	private String book_id;
    private String book_title;
    private String author_first_name;
    private String author_last_name;
    private String customer_id;
    private String search;
    private Scanner bookChoice;
    private Connection conn;
    
    Books(){
    	bookChoice = new Scanner(System.in);
    	conn = Main.getConnection();
    }
    
    public void addBook() {
    	System.out.println("What is the title of the book?");
    	book_title = bookChoice.nextLine();
    	System.out.println("What is the author's first name?");
    	author_first_name = bookChoice.nextLine();
    	System.out.println("What is the author's last name?");
    	author_last_name = bookChoice.nextLine();
    	System.out.println("What is the book serial number?");
    	book_id = bookChoice.nextLine();
    	
    	try {
			PreparedStatement addBook = conn.prepareStatement("INSERT INTO books(booktitle, authorfirstname, authorlastname, bookid, checkedout) "
					+ "VALUES ('" + book_title + "','" + author_first_name + "','" + author_last_name + "','" + book_id + "', false)");
					
			addBook.executeUpdate();
		} catch(Exception e) {System.out.println(e);}
    	
	}
    
    public void checkOut() {
    	bookInfo();
    	if(book_title == null){
    		System.out.println("Invalid input, please try again");
    	} else {
	    	int numberOfBooks = checkNumBooks();
	        if(numberOfBooks < 3) {
	        	addToCheckedOut();
	        } else {
	        	System.out.println("Maximum number of books has been checked out, please return a book to check out more");
	        }
    	}
    }
    
    public void bookInfo() {
	    System.out.println("What is the serial number of the book you're checking out?\n");
	    book_id = bookChoice.nextLine();
	    try {
			PreparedStatement bookInfo = conn.prepareStatement("SELECT * FROM books WHERE bookid = '" + book_id + "'");
			ResultSet result = bookInfo.executeQuery();
			addBookInfo(result);
		} catch(Exception e) {System.out.println(e);}
    }
    
    public int checkNumBooks() {
    	int a = 0;
    	try {
			PreparedStatement checkNumBooks = conn.prepareStatement("SELECT COUNT(customerid) FROM books WHERE customerid = '" + Main.c.getCustomerId() + "'");
			ResultSet result = checkNumBooks.executeQuery();
			if(result.next()) {
				a = result.getInt("COUNT(customerid)");
			}
		} catch(Exception e) {System.out.println(e);}
    	return a;
    }
    
    public void addToCheckedOut() {
    	if(checked_out == false) {
		    try {
		    	PreparedStatement addToCheckedOut = conn.prepareStatement("UPDATE books SET customerid = '" + Main.c.getCustomerId() + "', checkedout = true WHERE bookid = '" 
		    + book_id + "'");
				addToCheckedOut.executeUpdate();
		    } catch(Exception e) {System.out.println(e);}
		    System.out.println("Successful");
		    
	    } else {
	    	System.out.println("Book has already been checked out, please pick a different book");
	    }
    }
    
    public void searchBook() {
    	System.out.println("What is the author's first name, author's last name, book title, or book serial number?\n");
    	search = bookChoice.nextLine();
    	try {
			PreparedStatement searchBook = conn.prepareStatement("SELECT * FROM books WHERE bookid = '" + search + "'OR booktitle = '" + search 
					+ "'OR authorfirstname= '" + search + "'OR authorlastname = '" + search + "'");
			ResultSet result = searchBook.executeQuery();
			addBookInfo(result);
		} catch(Exception e) {System.out.println(e);}
    }
    
    public void returnBook() {
    	//allows employees to enter the serial number of book to take it out of checked out books and remove from customer's profile
    	System.out.println("What is the serial number of the book being returned?\n");
    	book_id = bookChoice.nextLine();
    	
    	try {
        	PreparedStatement returnBook = conn.prepareStatement("UPDATE books SET customerid = null, checkedout = false WHERE bookid = '" + book_id + "'");
        	returnBook.executeUpdate();
        	System.out.println("Successful");
        } catch(Exception e) {System.out.println(e);}
    }
    
    public void addBookInfo(ResultSet result) {
    	try {
	    	while(result.next()) {
				book_title = result.getString("booktitle");
				author_first_name = result.getString("authorfirstname");
				author_last_name = result.getString("authorlastname");
				book_id = result.getString("bookid");
				checked_out = result.getBoolean("checkedout");
				customer_id = result.getString("customerid");
				System.out.println("book serial number: " + book_id + "\n" + "book title: " + book_title + "\n" + "author first name: " + author_first_name + "\n" + "author last name: " + author_last_name + "\n" + "customer id: " + customer_id);
				if(checked_out == false) {
					System.out.println("Book is available for checkout\n");
				} else {
					System.out.println("Book is currently unavailable\n");
				}
	    	}
    	}catch(Exception e) {System.out.println(e);}
    }
    
    public void setBookTitle(String book_title) {
    	this.book_title = book_title;
    }
    public void setAuthorLastName(String author_last_name) {
    	this.author_last_name = author_last_name;
    }
    public void setAuthorFirstName(String author_first_name) {
    	this.author_first_name = author_first_name;
    }
    public void setBookId(String book_id) {
    	this.book_id = book_id;
    }
    public String getBookTitle() {
    	return book_title;
    }
    public String getAuthorLastName() {
    	return author_last_name;
    }
    public String getAuthorFirstName() {
    	return author_first_name;
    }
    public String getBookId() {
    	return book_id;
    }

}

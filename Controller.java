/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author Francis Buendia Christine Tran
 */
public class Controller {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";
    /**
     * @param args the command line arguments
     */
    /**
 * Takes the input string and outputs "N/A" if the string is empty or null.
 * @param input The string to be mapped.
 * @return  Either the input string or "N/A" as appropriate.
 */
    public static String dispNull (String input) {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        //Prompt the user for the database name, and the credentials.
        //If your database has no credentials, you can update this code to
        //remove that from the connection string.
        Scanner in = new Scanner(System.in);
        
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        PreparedStatement myStmt = null;
        String displayFormat="%-5s%-15s%-15s%-15s\n";
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);

            //STEP 4: Execute a query
            String sql = "";
            ResultSet rs = null;
            switch(menu()){
                // List all writing groups
                case 1: sql = "SELECT * FROM WritingGroup";
                        rs = stmt.executeQuery(sql);
                    break;
                // List all the data for a specified group
                case 2: 
                    break;
                // List all publishers
                case 3: sql = "SELECT * FROM Publisher";
                        rs = stmt.executeQuery(sql);
                    break;
                // List all the data for a specified pubisher"
                case 4:
                    break;
                // List all book titles
                case 5: sql = "SELECT BookTitle FROM Book";
                        rs = stmt.executeQuery(sql);
                    break;
                // List all the data for a specified book
                case 6: 
                    break;
                // Insert a new book
                case 7: System.out.println("What is your book title?");
                        String bookTitle = in.nextLine();
                        System.out.println("What year was your book published?");
                        int yearPublished = checkInt();
                        System.out.println("How many pages is your book?");
                        int numPages = checkInt();
                        myStmt = conn.prepareStatement("INSERT INTO Book (BookTitle, YearPublished, NumberPages)"
                                + "VALUES(?,?,?)");
                        
                        myStmt.setString(1,bookTitle);
                        myStmt.setInt(2, yearPublished);
                        myStmt.setInt(3, numPages);
                        displayFormat="%-15s%-4d%-15ds%\n";
                    break;
                // Insert a new publisher
                case 8: System.out.println("What is the publisher name?");
                        String publisherName = in.nextLine();
                        System.out.println("What is the publisher's address?");
                        String publisherAddress = in.nextLine();
                        System.out.println("What is the publisher's phone number? Enter in (###)###-#### format.");
                        String publisherPhone = in.nextLine();
                        System.out.println("What is the publisher's email?");
                        String publisherEmail = in.nextLine();
                        myStmt = conn.prepareStatement("INSERT INTO Publisher (PublisherName, PublisherAddress, PublisherPhone, PublisherEmail)"
                                + "VALUES(?,?,?,?)");
                        myStmt.setString(1, publisherName);
                        myStmt.setString(2, publisherAddress);
                        myStmt.setString(3, publisherPhone);
                        myStmt.setString(4, publisherEmail);
                        displayFormat="%-20s%-40s%-20s%-20s%\n";
                    break;
                // Remove a book
                case 9: System.out.println("What is the book title?");
                        bookTitle = in.nextLine();
                        System.out.println("What is the book's writing group name?");
                        String groupName = in.nextLine();
                        myStmt = conn.prepareStatement("DELETE * FROM Book"
                                + "NATURAL JOIN WritingGroup"
                                + "WHERE bookTitle = ? AND groupName = ?");
                        myStmt.setString(1, bookTitle);
                        myStmt.setString(2, groupName);
                        displayFormat="%-20s%-20s%\n";
                    break;
                // Exit
                case 10: System.out.println("Goodbye!");
                         System.exit(0);
                    break;
                
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            myStmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }
    
    public static int menu(){
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to BOOKGROUPS! What would you like to do?");
        System.out.println("1. List all writing groups");
        System.out.println("2. List all the data for a specified group");
        System.out.println("3. List all publishers");
        System.out.println("4. List all the data for a specified pubisher");
        System.out.println("5. List all book titles");
        System.out.println("6. List all the data for a specified book");
        System.out.println("7. Insert a new book");
        System.out.println("8. Insert a new publisher");
        System.out.println("9. Remove a book");
        System.out.println("10. Exit");
        int userChoice = in.nextInt();
        while(userChoice < 1 || userChoice > 10){
           System.out.println("Invalid option. Please try again.");
           userChoice = in.nextInt();
        }
        return userChoice;
    }
    
    public static int checkInt(){
        Scanner sc = new Scanner(System.in);
        int number;
        do {
            while (!sc.hasNextInt()) {
                System.out.println("That's not a number!");
                sc.next(); // this is important!
            }
            number = sc.nextInt();
        } while (number <= 0);
        
        return number;
    }
}

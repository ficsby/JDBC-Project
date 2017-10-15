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
        String displayFormat="%-5s%-15s%-15s%-15s\n";
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            PreparedStatement myStmt;
            int userChoice = menu();
            ResultSet rs = null;
            switch(userChoice){
                case 1: //Christine
                    break;
                case 2: //Francis
                    //SELECT * FROM WritingGroups WHERE GroupName = ' (userInput) '
                    myStmt = conn.prepareStatement("SELECT * FROM WritingGroup WHERE GroupName = ?");
                    String userGroup = in.nextLine();
                    myStmt.setString(1, userGroup);
                    rs = myStmt.executeQuery();
                    displayFormat="%-15s%-15s%-4d%-15s\n";
                    break;
                case 3: //Christine
                    break;
                case 4: //Francis
                    myStmt = conn.prepareStatement("SELECT * FROM Publisher WHERE PublisherName = ?");
                    String publisherName = in.nextLine();
                    myStmt.setString(1, publisherName);
                    rs = myStmt.executeQuery();
                    displayFormat="%-15s%-15s%-15s%-15s\n";
                    break;
                case 5: //Christine
                    break;
                case 6: //Francis
                    myStmt = conn.prepareStatement("SELECT * FROM (Book NATURAL JOIN WritingGroup)  NATURAL JOIN Publisher WHERE BookTitle = ? ");
                    String bookName = in.nextLine();
                    myStmt.setString(1, bookName);
                    rs = myStmt.executeQuery();
                    displayFormat="%-15s%-15s%-15s%-15s\n";
                    break;
                case 7: //Christine
                    System.out.println("What is the group name?");
                    String groupName = in.nextLine();
                    System.out.println("What is your book title?");
                    String bookTitle = in.nextLine();
                    System.out.println("What is the publisher name?");
                    publisherName = in.nextLine();
                    System.out.println("What year was your book published?");
                    int yearPublished = checkInt();
                    System.out.println("How many pages is your book?");
                    int numPages = checkInt();
                    myStmt = conn.prepareStatement("INSERT INTO Book (GroupName, BookTitle, PublisherName, YearPublished, NumberPages)"
                            + "VALUES(?,?,?,?,?)");
                    
                    myStmt.setString(1, groupName);
                    myStmt.setString(2,bookTitle);
                    myStmt.setString(3,publisherName);
                    myStmt.setInt(4, yearPublished);
                    myStmt.setInt(5, numPages);
                    displayFormat="%-15s%-4d%-15ds%\n";
                    break;
                case 8: //Francis
                    
                    break;
                case 9: //Christine
                    break;
            }
            String sql;
            //STEP 5: Extract data from result set
            System.out.printf(displayFormat, "ID", "First Name", "Last Name", "Phone #");
            while (rs.next()) {
                //Retrieve by column name
                String id = rs.getString("au_id");
                String phone = rs.getString("phone");
                String first = rs.getString("au_fname");
                String last = rs.getString("au_lname");

                //Display values
                System.out.printf(displayFormat,
                        dispNull(id), dispNull(first), dispNull(last), dispNull(phone));
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
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
        System.out.println("Goodbye!");
    }
    
    public static int menu(){
        System.out.println("1. List all writing groups");
        System.out.println("2. List all the data for a group specified by the user");
        System.out.println("3. List all publishers");
        System.out.println("4. List all the data for a publisher specified by the user");
        System.out.println("5. List all book titles");
        System.out.println("6. List all the data for book specified by the user");
        System.out.println("7. Insert a new book");
        System.out.println("8. Insert a new publisher and update all books published by one publisher to be published by the new publisher");
        System.out.println("9. Remove a book specified by the user");
        
        return checkInt();
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

package org.example.ecommerce;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CreateCustomerNewAccount {

    // procedding with userInput data -> creating the account here
    public void createNewAccountForCustomer(String userNameField, String userPhoneNumberField, String userPasswordField,
                                            String userEmailAddressField, String userAddressField){
        String insertQuery = "INSERT INTO customerdetails(custName, custEmail, custMobile, custPassword, custAddress) VALUES (?, ?, ?, ?, ?)";

        // Establish the DB connection
        DBConnection connection = new DBConnection();
         // Assuming getConnection() returns a Connection object
        try{
            Connection con = connection.getConnection();

            // Create a prepared statement
            PreparedStatement statement = con.prepareStatement(insertQuery);
            // Setting the values in statement
            statement.setString(1, userNameField);
            statement.setString(2, userEmailAddressField);
            statement.setString(3, userPhoneNumberField);
            statement.setString(4, userPasswordField);
            statement.setString(5, userAddressField);

            // Now execute the statment
            int rowsInserted = statement.executeUpdate();
            if(rowsInserted > 0){
                System.out.println("User Account created successfully");
            }else{
                System.out.println("Faild to create the user Account");
            }
            // close the current resurce
            statement.close();
            con.close();

        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}




// ---------------- ******************8*****
//1. When you need to retrieve data from the database, such as when querying for existing records or fetching results from a
//SELECT statement, you use a ResultSet. You execute a query using a Statement or PreparedStatement, and then you
//process the returned ResultSet to access the data.

//2. When you need to perform operations that modify the database, such as inserting, updating, or deleting records,
//you typically use a PreparedStatement. This is especially true for INSERT operations, where you want to securely
//insert data into the database using parameterized queries to prevent SQL injection attacks. You can also use PreparedStatement
//for other operations like updates and deletes, as it provides safety and performance benefits over regular Statement.
//
//        Note:-   Use ResultSet for fetching data from the database and PreparedStatement for modifying the database.
//->  The getConnection() method is used to obtain a connection to the database.
//->  The executeQuery() and executeUpdate() methods use prepared statements to execute SQL queries with parameters.
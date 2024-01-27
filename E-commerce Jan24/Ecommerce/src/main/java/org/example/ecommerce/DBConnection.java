package org.example.ecommerce;
import java.sql.*;


public class DBConnection {
    // help to connection between the database
    private final String DBurl = "jdbc:mysql://localhost:3306/ecommerce_majorDB_january";

//    username
    private final String userName = "root";   // this is workbanch userID
    private final String password = "RKumar@2024#" ;  // workbench password

    // make the connection between the appliction to DB
    private Statement getStatement(){
        try {
            // DriverManager -> will help me to create a connection for me
            Connection connection = DriverManager.getConnection(DBurl, userName, password);
            return connection.createStatement();  // creating the statement for execute the query
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }


    /// establish the conneciton
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DBurl, userName, password);
    }

    public ResultSet getQueryTable(String query){
        //here I will passing the query
        // ResultSet -> it means kind of table the DB

        try {
            Statement statement = getStatement();

            return  statement.executeQuery(query);

        }catch (Exception exception){
            exception.printStackTrace();
        }

        return null;
    }

    public int updateDataBase(String query){
        try {
            Statement statement = getStatement();
            /// updating the dataBase
            return  statement.executeUpdate(query);   // how many row is affected when order will happen

        }catch (Exception exception){
            exception.printStackTrace();
        }
        return 0;
    }


    public static void main(String[] args) {
        DBConnection connection = new DBConnection();
        // using the connection to excute the query
        ResultSet resultSet = connection.getQueryTable("SELECT * FROM customerdetails");

        if(resultSet != null){
            System.out.println("Connection Successful");
        }else {
            System.out.println("Connection Failed");
        }
    }


}

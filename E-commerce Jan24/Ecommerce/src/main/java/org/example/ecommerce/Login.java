package org.example.ecommerce;

import java.sql.ResultSet;

public class Login {

    // here I will make return type as a customer
    public Customer customerLogin(String userName, String password){
        // String loginQuery = " SELECT * FROM customerdetails WHERE custEmail = 'rajeevkr2061998@gmail.com' AND custPassword = 'xyz789'";
        String loginQuery = " SELECT * FROM customerdetails WHERE custEmail = '"+userName+"' AND custPassword = '"+password+"'";
        DBConnection connection = new DBConnection();

        try {
            ResultSet resultSet = connection.getQueryTable(loginQuery);   // pass my query in my DB
            // when my username and password will correct then i will able to login
//            System.out.println(resultSet);
            if(resultSet.next()){
               // create customer object for customer
                // pass the database table column name
                return new Customer(resultSet.getInt("id"),
                        resultSet.getString("custName"),
                        resultSet.getString("custEmail"),
                        resultSet.getString("custMobile"));
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }

        return null;
    }



    // implement the login feature
//    public boolean customerLogin(String userName, String password){
//        // String loginQuery = " SELECT * FROM customerdetails WHERE custEmail = 'rajeevkr2061998@gmail.com' AND custPassword = 'xyz789'";
//        String loginQuery = " SELECT * FROM customerdetails WHERE custEmail = '"+userName+"' AND custPassword = '"+password+"'";
//        DBConnection connection = new DBConnection();
//
//        try {
//            ResultSet resultSet = connection.getQueryTable(loginQuery);   // pass my query in my DB
//            // when my username and password will correct then i will able to login
////            System.out.println(resultSet);
//            if(resultSet.next()){
//                // for particular cutomer value I can this way
//                int customerId = resultSet.getInt("id");  /// when will int value
//                String customerName = resultSet.getString("custName");
//                String customerEmail = resultSet.getString("custEmail"); // Replace with your actual column name
//                String customerPassword = resultSet.getString("custPassword"); // Replace with your actual column name
//                String customerMobile = resultSet.getString("custMobile");
//                String customerAddress = resultSet.getString("custAddress");
//
//                // print the all values from DBTable
//                System.out.println("Customer ID: " + customerId +
//                                "  Customer Name : " +customerName+
//                                "  Customer Email : " +customerEmail+
//                                "  Customer Password : " +customerPassword+
//                                "  Customer Mobile : " +customerMobile+
//                                "  Customer Address : " +customerAddress
//                        );
//
//                return true;
//            }
//        }catch (Exception exception){
//            exception.printStackTrace();
//        }
//
//        return false;
//    }
//
    public static void main(String[] args) {
        Login login = new Login();
        Customer customer = login.customerLogin("rajeevkr2061998@gmail.com", "xyz789");
        System.out.println("Welcome : " + customer.getName());
//        System.out.println(login.customerLogin("rajeevkr2061998@gmail.com", "xyz7890"));
    }
}

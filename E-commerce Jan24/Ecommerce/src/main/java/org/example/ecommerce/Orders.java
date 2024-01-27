package org.example.ecommerce;

import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Orders {

    // for order is require -> customer details and product
    public static boolean placeOrder(Customer customer, Product product){
        // write the Query
//        String placeOrderQuery = "INSERT INTO productorders(group_order_id, customer_id, product_id) VALUES(1, 1, 17)";
//        -- getting next order -> get the data from Database
        String groupOrderIdQuery = "SELECT MAX(group_order_id)+1 id FROM productorders";
        DBConnection dbConnection = new DBConnection();
        try {
            // fire the query ->
            ResultSet rs = dbConnection.getQueryTable(groupOrderIdQuery); ///jo bhi query hoga usko pass karke check karna hai id
            if(rs.next()){
                // when group_id will present then move for placeOrder
                // here need Group order id  -> for group_id table column name is id
                String placeOrderQuery = "INSERT INTO productorders(group_order_id, customer_id, product_id) VALUES("+rs.getInt("id")+", "+customer.getId()+", "+product.getId()+")";
                return dbConnection.updateDataBase(placeOrderQuery) != 0;
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return false;
    }


    /// need multiple order -> group id
    public static int placeMultipleOrder(Customer customer, ObservableList<Product> productList){
        // write the Query
//        String placeOrderQuery = "INSERT INTO productorders(group_order_id, customer_id, product_id) VALUES(1, 1, 17)";
//        -- getting next order -> get the data from Database
        String groupOrderIdQuery = "SELECT MAX(group_order_id)+1 id FROM productorders";
        DBConnection dbConnection = new DBConnection();
        try {
            // fire the query ->
            ResultSet rs = dbConnection.getQueryTable(groupOrderIdQuery); ///jo bhi query hoga usko pass karke check karna hai id
            int count = 0;
            if(rs.next()){
                // when group_id will present then move for placeOrder
                // here need Group order id  -> for group_id table column name is id
                for(Product product: productList){
                    // create the query
                    String placeOrderMultipleQuery = "INSERT INTO productorders(group_order_id, customer_id, product_id) VALUES("+rs.getInt("id")+", "+customer.getId()+", "+product.getId()+")";
                    count += dbConnection.updateDataBase(placeOrderMultipleQuery);
                }
                return  count;
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return 0;
    }


}

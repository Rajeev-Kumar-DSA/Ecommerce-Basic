package org.example.ecommerce;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Product {
    // create property for respective product table
    private SimpleIntegerProperty id;  /// it is kind of wrappering the value and then show in table
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;

    // create constructor
    // generate
    public Product(int id, String name, double price) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    }

    // connection between the product table DB
    public static ObservableList<Product> getAllProducts(){
//        String selectAllProductsDB = "SELECT * FROM product";
        String selectAllProductsDB = "SELECT id, productName, productPrice FROM product";  // where name Like '%iphone%'  -> for search
        return fetchProductDataFromDB(selectAllProductsDB);
    }

    public static ObservableList<Product> fetchProductDataFromDB(String query){
        ObservableList<Product> data = FXCollections.observableArrayList();
        DBConnection dbConnection = new DBConnection();
        try{
            ResultSet rs = dbConnection.getQueryTable(query);
            while (rs.next()){
                // add the data from data in list
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("productName"),
                        rs.getDouble("productPrice")
                );
                data.add(product);
            }
            return data;
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    // - Generate getter and setter for above variable
    public int getId() {
        return id.get();
    }
    public String getName() {
        return name.get();
    }
    public double getPrice() {
        return price.get();
    }
}

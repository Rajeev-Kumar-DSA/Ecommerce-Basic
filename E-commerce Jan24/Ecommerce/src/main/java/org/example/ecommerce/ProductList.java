package org.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductList {
    // showing the all product -> in tabular form
    private TableView<Product> productTable;   // pass the product DB data

    public VBox createTable(ObservableList<Product> data){
        // columns
        TableColumn id = new TableColumn("ID");   // jo show karna chahta hai
        id.setCellValueFactory(new PropertyValueFactory<>("id"));  // same in product class data

        TableColumn name = new TableColumn("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("PRICE");  /// for table column name
        price.setCellValueFactory(new PropertyValueFactory<>("price"));  /// same as class name



        //create product table
        productTable = new TableView<>();
        productTable.getColumns().addAll(id, name, price);   // add all column which want to show on UI
        productTable.setItems(data);   /// add the data in tableView
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);  // it will remove extra column

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10));  /// it will make space from left and right side
        vBox.getChildren().addAll(productTable);
        return vBox;
    }

//    /// --- it is dummy data
//    public VBox getDummyTable(){
//        // Data - keep it as dummy data
//        ObservableList<Product> data = FXCollections.observableArrayList();
//        data.add(new Product(2, "Iphone", 98000));
//        data.add(new Product(5, "Laptop", 69000));
//
//        return createTable(data);
//    }


    /// this method I will call in UserInteface class kyuki wahi sabhi ko collect kar rahe hai
    public VBox getAllProducts(){
        ObservableList<Product> data = Product.getAllProducts();
        // bind the data with table
        return createTable(data);
    }

    public Product getSelectedProduct(){
        // jo product select hoga
        return productTable.getSelectionModel().getSelectedItem();  /// return product value
    }

    public VBox getProductsInCart(ObservableList<Product> data){
        return createTable(data);   // yaha data milega observable list se usko createtable me sending
    }

    public VBox getFilteredProducts(String searchQuery){
        // 1. Connect to the database.
        // 2. make SQL query to fetch products based on the search query.
        // 3. Iterate through the result set and construct UI elements (such as buttons or labels) for each product.
        // 4. Add these UI elements to a VBox.
        // 5. Return the VBox containing the filtered products.

        VBox filterProduct = new VBox();
        try{
            // connect the database
            DBConnection connection = new DBConnection();
            Connection con = connection.getConnection();

            // Prepare the query
            String filterQuery = "SELECT * FROM product WHERE productName LIKE ?";
            PreparedStatement statement = con.prepareStatement(filterQuery);
            statement.setString(1, "%"+searchQuery+"%");  // LIKE operator for partial matching

            // start execution the query
            ResultSet resultSet = statement.executeQuery();

            TableView<Product> searchedProduct = new TableView<>();

            // define the column and tableView
            TableColumn<Product, Integer> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
            priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            // add the columns to the TableView
            searchedProduct.getColumns().addAll(idColumn, nameColumn, priceColumn);
            searchedProduct.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


            // travel the table check the value
            while(resultSet.next()){
                int productId = resultSet.getInt("id");
                String productName = resultSet.getString("productName");
                double productPrice = resultSet.getDouble("productPrice");

                // Create a Product object for each Row in the result set
                Product product = new Product(productId, productName, productPrice);

                // add the product object to the TableView
                searchedProduct.getItems().add(product);
            }
            resultSet.close();
            statement.close();
            con.close();
            // add inside the table
            filterProduct.getChildren().add(searchedProduct);
        }catch (SQLException exception){
            exception.printStackTrace();
        }

        return filterProduct;
    }


///     passing the dummy data
//    public VBox createTable(){
//        // columns
//        TableColumn id = new TableColumn("ID");   // jo show karna chahta hai
//        id.setCellValueFactory(new PropertyValueFactory<>("id"));  // same in product class data
//
//        TableColumn name = new TableColumn("NAME");
//        name.setCellValueFactory(new PropertyValueFactory<>("name"));
//
//        TableColumn price = new TableColumn("PRICE");  /// for table column name
//        price.setCellValueFactory(new PropertyValueFactory<>("price"));  /// same as class name
//
//        // Data - keep it as dummy data
//        ObservableList<Product> data = FXCollections.observableArrayList();
//        data.add(new Product(2, "Iphone", 98000));
//        data.add(new Product(5, "Laptop", 69000));
//
//        //create product table
//        productTable = new TableView<>();
//        productTable.setItems(data);   /// add the data in tableView
//        productTable.getColumns().addAll(id, name, price);   // add all column which want to show on UI
//        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);  // it will remove extra column
//
//        VBox vBox = new VBox();
//        vBox.setPadding(new Insets(10));  /// it will make space from left and right side
//        vBox.getChildren().addAll(productTable);
//        return vBox;
//    }
}

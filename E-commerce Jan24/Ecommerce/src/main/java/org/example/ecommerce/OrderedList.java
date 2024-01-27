package org.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class OrderedList {
    TableView<Order> tableViewOrder;
    public OrderedList(){
        tableViewOrder = new TableView<>();
        initializeOrderTableList();
        fetchAllOrderListFromDB();
    }

    private void initializeOrderTableList(){
        // Define all column
        TableColumn<Order, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Order, String> orderProductName = new TableColumn<>("Order Product Name");
        orderProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));

        TableColumn<Order, String> orderQuantity = new TableColumn<>("Order Quantity");
        orderQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Order, Date> orderDate = new TableColumn<>("Order Date");
        orderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

        TableColumn<Order, String> orderStatus = new TableColumn<>("Status");
        orderStatus.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));


        // add all column in table
        tableViewOrder.getColumns().addAll(idColumn, orderProductName, orderQuantity, orderDate, orderStatus);
        tableViewOrder.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    // now I will fetch
    private void fetchAllOrderListFromDB(){
        ObservableList<Order> orders = FXCollections.observableArrayList();


        try{
            DBConnection connection = new DBConnection();
            Connection con = connection.getConnection();

            String orderListQuery = "SELECT productorders.id, product.productname, productorders.quantity, productorders.order_date, productorders.order_status FROM productorders join product on productorders.product_id = product.id";

            ResultSet resultSet = con.createStatement().executeQuery(orderListQuery);

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String productName = resultSet.getString("productName");
                int quantity = resultSet.getInt("quantity");
                Date orderDate = resultSet.getDate("order_date");
                String orderStatus = resultSet.getString("order_status");

                // pass fetching the details inside the order class then store inside the table view
                Order order = new Order(id, productName, quantity, orderDate, orderStatus);
                orders.add(order);
            }
            tableViewOrder.setItems(orders);

        }catch (SQLException exception){
            exception.printStackTrace();
        }
    }
}


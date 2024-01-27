package org.example.ecommerce;

public class Customer {
    // get the customer details
    private int id;
    private String name, email, mobile;

    // create a constructor for all variable -> right click here -> generator -> select all
    public Customer(int id, String name, String email, String mobile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    // create a getter for all variable -> right click here -> generator -> select getter -> then select all variable
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }
}

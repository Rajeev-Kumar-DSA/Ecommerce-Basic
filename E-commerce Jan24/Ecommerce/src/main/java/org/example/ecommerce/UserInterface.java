package org.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class UserInterface {

    GridPane loginPage;
    GridPane newAccountCreatePage;
    HBox headerBar;  /// for heading data
    HBox footerBar;
    VBox body;   ///
    Button signInButton;
    Label welcomeLabel;   // showing the name

    // store the information - who is login currently
    Customer loggedInCustomerInfo;

    ProductList productList = new ProductList();
    VBox productPage;

    Button placeOrderButton = new Button("Place Order");  /// whatever product will available in cart -> move for delivery process


    /// for multiple order
    ObservableList<Product> itemsInCart = FXCollections.observableArrayList();

    // create Pane
    BorderPane createContent(){
        BorderPane root = new BorderPane();
        root.setPrefSize(800, 600);  // width, height
        // add the login details in page
//        root.getChildren().add(loginPage);   // method to add nodes as children to pane
        root.setTop(headerBar);  // show search option
//        root.setCenter(loginPage);

        body = new VBox();  // making a add two thing login and table
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        root.setCenter(body);

        productPage = productList.getAllProducts();
        body.getChildren().add(productPage);

        root.setBottom(footerBar);



        return root;
    }

    // create a constructor for userInterface class
    // if not create constructor then it will throw the error -> nullpointerException
    public UserInterface(){
        createLoginPage();   /// constructor me call karna hoga
        createHeaderBar();
        createFooterBar();
        createNewAccount();
    }


    private void createLoginPage(){
        Text userNameText = new Text("User Name ");
        Text passwordText = new Text("Enter Password :");

        // user provide text area
        TextField userName = new TextField();
        userName.setPromptText("Type your user name here ......");
        PasswordField password = new PasswordField();
        password.setPromptText("Type your password here ......");   /// same like placeholder in HTML
        Label messageLabel = new Label("Hi buddy");

        Button loginButton = new Button("Login");
        // add new account
        Button newAccountCreate = new Button(" Don't have Account? ");

        loginPage = new GridPane();
//        loginPage.setStyle("-fx-background-color: #2f4f4f");
        loginPage.setAlignment(Pos.CENTER);
        // gap between the row and column
        loginPage.setHgap(10);
        loginPage.setVgap(10);
        //adding the controls
        // login -> (0, 0),  (1, 0)
        loginPage.add(userNameText, 0, 0);  // columnIndex = 0, row = 0
        loginPage.add(userName, 1, 0);  /// column 1, and row 0
        loginPage.add(passwordText, 0, 1);
        loginPage.add(password, 1, 1);
        loginPage.add(messageLabel, 0, 2);
        loginPage.add(loginButton, 1, 2);   ///(1, 2)
        loginPage.add(newAccountCreate, 2, 2);


        // after click the button -> message will appear
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //
                String name = userName.getText();   // jo bhi userName me enter karega wahi same message label me fetch karke show karega
//                messageLabel.setText(name);

                String pass = password.getText();
                Login login = new Login();
                loggedInCustomerInfo = login.customerLogin(name, pass);   //// giving the userId and password

                if(loggedInCustomerInfo != null){
                    messageLabel.setText("Welcome : " + loggedInCustomerInfo.getName());
                    // when successfully login then add the name in label
                    welcomeLabel.setText("Welcome : " + loggedInCustomerInfo.getName());
                    headerBar.getChildren().add(welcomeLabel);   /// add the label

                    /// after successfully login then remove the login area and bring the tableView data
                    body.getChildren().clear();
                    body.getChildren().add(productPage);
                }else {
                    messageLabel.setText("Login Failed !! Please give correct user name and password");
                }
            }
        });

        newAccountCreate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                /// create new account page
                body.getChildren().clear();
                body.getChildren().add(newAccountCreatePage);
            }
        });

    }

    private void createNewAccount(){
        Text newUserNameText = new Text("User Name: ");
        Text newUserPhoneNumber = new Text("Phone Number: ");
        Text newUserEmailAddress = new Text("Email Address: ");
        Text newUserPassword = new Text("Password: ");
        Text newUserAddress = new Text("Address: ");

        TextField userNameField = new TextField();
        userNameField.setPromptText("Enter your name: ");
        TextField userPhoneNumberField = new TextField();
        userPhoneNumberField.setPromptText("Enter your valid Phone number");
        TextField userEmailAddressField = new TextField();
        userEmailAddressField.setPromptText("Enter your email account");
        TextField userPasswordField = new TextField();
        userPasswordField.setPromptText("choose account password");
        TextField userAddressField = new TextField();
        userAddressField.setPromptText("Enter your delivery location");

        Button createNewAccount = new Button("Create Your Account");

        /// making a page -> using grid pane
        newAccountCreatePage = new GridPane();
        newAccountCreatePage.setAlignment(Pos.CENTER);
        // gap between the row and column
        newAccountCreatePage.setHgap(10);
        newAccountCreatePage.setVgap(10);
        // now i will keep the filed using co-ordinate
        newAccountCreatePage.add(newUserNameText, 0, 0);  // first row
        newAccountCreatePage.add(userNameField, 1, 0);   /// column 1, and row 0
        newAccountCreatePage.add(newUserPhoneNumber,0, 1); // second row
        newAccountCreatePage.add(userPhoneNumberField, 1, 1);
        newAccountCreatePage.add(newUserEmailAddress, 0, 2);  // third row
        newAccountCreatePage.add(userEmailAddressField, 1, 2);
        newAccountCreatePage.add(newUserPassword, 0, 3);  // fourth row
        newAccountCreatePage.add(userPasswordField, 1, 3);
        newAccountCreatePage.add(newUserAddress, 0, 4);  // fifth row
        newAccountCreatePage.add(userAddressField, 1, 4);

        newAccountCreatePage.add(createNewAccount, 1, 5);  // fifth row


        // now I will check the field and pass all details in my sql table
        createNewAccount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // check if field is blank
                if(userNameField.getText().isEmpty() || userPasswordField.getText().isEmpty() || userEmailAddressField.getText().isEmpty()
                        || userPhoneNumberField.getText().length()!=10){
                    showDialog("Please fill all required details");
                }else{
                    // when all data will available -
                    // all data will go inside sql query ->  Call a method to insert the user data into the database
                    CreateCustomerNewAccount createCustomerNewAccount = new CreateCustomerNewAccount();
                    createCustomerNewAccount.createNewAccountForCustomer(userNameField.getText(), userPhoneNumberField.getText(),
                                userPasswordField.getText(), userEmailAddressField.getText(), userAddressField.getText());

                    // after able to create account -> then move for product page
                    body.getChildren().clear();
                    body.getChildren().add(productPage);
                }
            }
        });
    }


    private void createHeaderBar(){
        // Home button
        Button homeButton = new Button();  // add image here
        // here require path of image -> right click on pasted image -> copy Path reference -> select

        // In a URL, spaces are not allowed. Therefore, when representing a file path in a URL, spaces are
        // encoded as %20. This is a common practice in web development and URL encoding. So, in the file
        // path All Major Project, each space is replaced with %20 to make it a valid URL.

        Image image = new Image("file:///D:/All%20Major%20Project/E-commerce%20Jan24/Ecommerce/src/homeImage.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(80);
        homeButton.setGraphic(imageView);

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search product here....");
        searchBar.setPrefWidth(200);  /// give the width for hbox

        Button searchButton = new Button("Search");

        signInButton = new Button("Sign In");  // when click this button this time I will bring my login page
        welcomeLabel = new Label();

        Button cartButton = new Button("Cart");

        Button OrderButton = new Button("Orders");





//        headerBar = new HBox(20);  /// give the space between the button and search area // constructor - here providing the spacing betweent he childrean
        headerBar = new HBox();   /// by default spacing is 0
//        headerBar.setStyle("-fx-background-color: #2f4f4f");  /// giving the background color for Hbox
        headerBar.setPadding(new Insets(10));
        headerBar.setSpacing(10);
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(homeButton, searchBar, searchButton, signInButton, cartButton, OrderButton);


        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // remove the table view -> then bring login page
                body.getChildren().clear();  /// clear table view
                body.getChildren().add(loginPage);   /// putting loginpage
                headerBar.getChildren().remove(signInButton);   // removing the signing button from headerbar
                // My plan i will add new button here which will called signout button
            }
        });
        // when cart button will click create a new body -> (new page)
        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                VBox productPageCart = productList.getProductsInCart(itemsInCart);  // value ko show karega
                productPageCart.setAlignment(Pos.CENTER);
                productPageCart.setSpacing(10);
                productPageCart.getChildren().add(placeOrderButton);
                body.getChildren().add(productPageCart);
                footerBar.setVisible(false);  // making invisible -> (footer area button will invisible here (i will make change according where need to visible footer button
            }
        });

//        placeOrderButton.setAlignment(Pos.CENTER);
        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // placing one order -> product and user (when will one order)
                // when multiple order means its require to list of order and user
                // need the list of procduct and customer
                if(itemsInCart == null){
                    showDialog("Please add some in the cart to the place order");
                    return;
                }
                //user must be login -> then will order possible
                if(loggedInCustomerInfo == null){
                    showDialog("Please login first to place order !!...");
                    return;
                }
                // pass the order
                int countProduct = Orders.placeMultipleOrder(loggedInCustomerInfo, itemsInCart);
                if(countProduct != 0){
                    showDialog("Order for " + countProduct + " products placed successfully");
                }else {
                    showDialog("Order Failed !!.....");
                }
            }
        });

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // show the productPage
                body.getChildren().clear();
                body.getChildren().add(productPage);
                footerBar.setVisible(true);
                // jab bhi kabhi home page par aate hai -> agar user login nahi kiya hai tab -> signIn button should available
                if(loggedInCustomerInfo == null){
                    System.out.println(headerBar.getChildren().indexOf(signInButton));
                    if(headerBar.getChildren().indexOf(signInButton) == -1) {
                        headerBar.getChildren().add(signInButton);
                    }
                }
            }
        });

        /// showing the order page
        OrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // create instance of orderList
                OrderedList orderedList = new OrderedList();
                body.getChildren().clear();
                body.getChildren().add(orderedList.tableViewOrder);

            }
        });


        /// for search functionlity
        //Implement the search functionality in the createHeaderBar method. When the search button is clicked,
        //call the getFilteredProducts method of ProductList with the entered search query and update the UI with the filtered products.
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // get the userInput search value
                String searchQuery = searchBar.getText();   // keep it in string
                if(!searchQuery.isEmpty()){  // when any value will present
                    // Clear existing products and show filtered products
                    body.getChildren().clear();
                    // show the search element
                    VBox filteredProduct = productList.getFilteredProducts(searchQuery);
                    body.getChildren().add(filteredProduct);
                }else{
                    // Show all products if search query is empty
                    body.getChildren().clear();
                    body.getChildren().add(productPage);
                }
            }
        });

    }


    private void createFooterBar(){
        Button buyNowButton = new Button("Buy Now");
        Button addToCartButton = new Button("Add to Cart");

        footerBar = new HBox();   /// by default spacing is 0
//        headerBar.setStyle("-fx-background-color: #2f4f4f");  /// giving the background color for Hbox
        footerBar.setPadding(new Insets(10));
        footerBar.setSpacing(10);
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton, addToCartButton);


        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                // if no any product is selected
                if(product == null){
                    // request -> show Dialog
                    // please select a product first to place order
                    showDialog("Please select a product first to place order!!!!..");
                    return;
                }
                if(loggedInCustomerInfo == null){
                    showDialog("Please login first to place order !!...");
                    return;
                }
                // pass the order
                boolean status = Orders.placeOrder(loggedInCustomerInfo, product);
                if(status == true){
                    showDialog("Order Placed successfully");
                }else {
                    showDialog("Order Failed !!.....");
                }

            }
        });

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                // if no any product is selected
                if(product == null){
                    // request -> show Dialog
                    // please select a product first to place order
                    showDialog("Please select a product first to add it to Cart !!!!..");
                    return;
                }
                itemsInCart.add(product);   // when item will seleected
                showDialog("Selected Item has been added to the cart successfully");
            }
        });

    }

    private void showDialog(String message){
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Message");
        alert.showAndWait();
    }
}

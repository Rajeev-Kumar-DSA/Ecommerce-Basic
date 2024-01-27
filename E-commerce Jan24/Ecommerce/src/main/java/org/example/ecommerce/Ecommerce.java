package org.example.ecommerce;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Ecommerce extends Application {

    // call my createContenet  -> calling from UserInterface class
    UserInterface userInterface = new UserInterface();

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(userInterface.createContent());
        stage.setTitle("E-commerce");
        stage.setScene(scene);
        stage.show();
    }


//    public void start(Stage stage) {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//            stage.setTitle("Hello!");
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace(); // Handle the exception as per your application's requirements
//        }
//    }

    public static void main(String[] args) {

        launch();
    }
}
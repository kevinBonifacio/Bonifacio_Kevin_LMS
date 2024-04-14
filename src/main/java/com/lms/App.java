package com.lms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * CEN 3024 - Software Development 1
 * 14 April 2024
 * App.java
 * This application will prompt the user to select one of the Library Management System options.
 * An 'add' option to add new books to the collection. Two 'remove' options to remove books from the collection by barcode or title.
 * A check-out option to borrow books. A check-in option to return borrowed books.
 * A 'print' option to display all the books currently in the collection.
 *
 * @author Kevin Bonifacio
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main_Scene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Library Management System");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * method: main
     * purpose: Initializes the application.
     */
    public static void main(String[] args) {
        launch();
    }
}
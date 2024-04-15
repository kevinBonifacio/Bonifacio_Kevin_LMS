package com.lms;

import com.lms.model.Book;
import com.lms.model.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * CEN 3024 - Software Development 1
 * 14 April 2024
 * Display_Controller.java
 * Controller for the Display_Scene.fxml
 * @author Kevin Bonifacio
 */
public class Display_Controller extends controller implements Initializable {

    private final ArrayList<Book> collection = new ArrayList<>();

    @FXML
    private ListView<Book> textDisplay;

    /**
     * method: goBack
     * purpose: switch the current scene back to the Main_Scene.
     * @param event ActionEvent
     */
    @FXML
    public void goBack(ActionEvent event) throws IOException {
        switchScene(event, "Main_Scene.fxml");
    }

    /**
     * method: initialize
     * purpose: Establishes the database connection and fetch data from it
     * @param url URL
     * @param resourceBundle ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection conn = new DatabaseConnection();
        Connection connectDB = conn.connect();

        String sqlQuery = "SELECT Barcode, Title, Author, Genre, Status, Due_Date FROM Book";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(sqlQuery);

            while (queryOutput.next()) {
                String barcode = queryOutput.getString("Barcode");
                String title = queryOutput.getString("Title");
                String author = queryOutput.getString("Author");
                String genre = queryOutput.getString("Genre");
                boolean status = queryOutput.getBoolean("Status");

                if (queryOutput.getDate("Due_Date") != null) {
                    LocalDate dueDate = queryOutput.getDate("Due_Date").toLocalDate();
                    this.collection.add(new Book(barcode, title, author, genre, status, dueDate));
                } else {
                    this.collection.add(new Book(barcode, title, author, genre, status, null));
                }
            }

            textDisplay.getItems().addAll(collection);
            System.out.println(collection);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

package com.lms;

import com.lms.model.Book;
import com.lms.model.DatabaseConnection;
import com.lms.model.Library;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * CheckIn_Controller.java
 * Controller for the CheckIn_Scene.fxml
 * @author Kevin Bonifacio
 */
public class CheckIn_Controller implements Initializable {
    private Library library = new Library();
    private final ArrayList<Book> collection = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger(CheckIn_Controller.class);

    @FXML
    private Label dueDate;

    @FXML
    private TextField titleInput;

    public void setLibrary(Library library) {
        this.library = library;
    }

    /**
     * method: goBack
     * purpose: switch the current scene back to the Main_Scene.
     * @param event ActionEvent
     */
    @FXML
    public void goBack(ActionEvent event) throws IOException {
        logger.info("Navigating back to Main_Scene.fxml");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main_Scene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * method: checkIn
     * purpose: calls the checkInBook() method.
     */
    public void checkIn() {
        String title = titleInput.getText().trim();

        if (title.isEmpty()) {
            dueDate.setText("Please enter a title.");
            dueDate.setVisible(true);
            logger.warn("Check-in attempted with empty title input.");
            return;
        }

        String resultMessage =  library.checkInBook(title, collection);
        dueDate.setText(resultMessage);
        dueDate.setVisible(true);
        logger.info("Check-in result for '{}': {}", title, resultMessage);
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
                    collection.add(new Book(barcode, title, author, genre, status, dueDate));
                } else {
                    collection.add(new Book(barcode, title, author, genre, status, null));
                }
            }

        } catch (Exception e) {
            logger.error("Error initializing book collection from database: {}", e.getMessage(), e);
        }
    }
}

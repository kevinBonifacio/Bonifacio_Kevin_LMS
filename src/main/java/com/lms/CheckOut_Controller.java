package com.lms;

import com.lms.model.Book;
import com.lms.model.DatabaseConnection;
import com.lms.model.Library;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
 * CheckOut_Controller.java
 * Controller for the CheckOut_Scene.fxml
 * @author Kevin Bonifacio
 */
public class CheckOut_Controller extends controller implements Initializable {
    private final Library library = new Library();
    private final ArrayList<Book> collection = new ArrayList<>();
    private static final Logger logger = LogManager.getLogger("LOGS");

    @FXML
    private Label dueDate;

    @FXML
    private TextField titleInput;

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
     * method: checkOut
     * purpose: calls the checkOutBook() method.
     */
    public void checkOut() {
        String title = titleInput.getText();

        // Check if the title is empty
        if (title == null || title.trim().isEmpty()) {
            logger.warn("User tried to check out a book with an empty title.");
            removeDueDate();  // Optional: clear the due date label if title is empty
            return;
        }

        try {
            String dateString = library.checkOutBook(title, this.collection);

            if (dateString.contains("checked out")) {
                logger.info("Book '{}' successfully checked out. {}", title, dateString);
            } else if (dateString.contains("already been borrowed")) {
                logger.warn("Book '{}' has already been borrowed.", title);
            } else if (dateString.contains("No match found")) {
                logger.warn("No match found for book title '{}'.", title);
            } else {
                logger.warn("Unknown result while checking out '{}': {}", title, dateString);
            }

            dueDate.setText(dateString);
            dueDate.setVisible(true);

        } catch (Exception e) {
            logger.error("Error occurred while checking out book '{}':", title, e);
            removeDueDate();
        }

    }

    private void removeDueDate() {
        dueDate.setText("?");  // Clear the due date text
        dueDate.setVisible(false);  // Hide the due date label
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

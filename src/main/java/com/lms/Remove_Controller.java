package com.lms;

import com.lms.model.Book;
import com.lms.model.DatabaseConnection;
import com.lms.model.Library;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
 * Remove_Controller.java
 * Controller for the Remove_Scene.fxml
 * @author Kevin Bonifacio
 */
public class Remove_Controller extends controller implements Initializable {

    Library library = new Library();

    private final ArrayList<Book> collection = new ArrayList<>();

    @FXML
    private ToggleGroup remove;

    @FXML
    private RadioButton barcodeButton;

    @FXML
    private RadioButton titleButton;

    @FXML
    private TextField dataInput;

    @FXML
    private Label removeLabel;

    @FXML
    private ListView<Book> bookList;

    /**
     * method: goBack
     * @param event ActionEvent
     * purpose: switch the current scene back to the Main_Scene.
     */
    public void goBack(ActionEvent event) throws IOException {
        switchScene(event, "Main_Scene.fxml");
    }

    /**
     * method: removeBook
     * purpose: gets user input from the textField and the toggleButton,
     * then calls the appropriate method to removeBookByBarcode or removeBookByTitle.
     */
    @FXML
    public void removeBook() {
        ArrayList<Book> booksToBeRemove = new ArrayList<>();
        String code = dataInput.getText();

        bookList.getItems().clear();
        bookList.setVisible(false);

        if (code != null) {
            if(barcodeButton.isSelected()) { //Remove by barcode option
                if(!library.removeBookByBarcode(code)) {
                    removeLabel.setText("This barcode is not part of the collection!");
                    removeLabel.setVisible(true);
                } else {
                    removeLabel.setText("Book removed!");
                    removeLabel.setVisible(true);
                }

            } else if (titleButton.isSelected()) { //Remove by title option

                for (Book book : collection) {
                    if (book.getTitle().equalsIgnoreCase(code)) {
                        booksToBeRemove.add(book);
                    }
                }

                if (booksToBeRemove.size() <= 1) {

                    if(!library.removeBookByTitle(code)) {
                        removeLabel.setText("This title is not part of the collection!");
                        removeLabel.setVisible(true);
                    } else {
                        removeLabel.setText("Book removed!");
                        removeLabel.setVisible(true);
                    }

                } else {
                    removeLabel.setText("Multiples books with the same title has been found, please enter a barcode!");
                    remove.selectToggle(barcodeButton);
                    bookList.getItems().addAll(booksToBeRemove);
                    removeLabel.setVisible(true);
                    bookList.setVisible(true);
                }
            }
        }
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

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

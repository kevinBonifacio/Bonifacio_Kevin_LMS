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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/*
 * Kevin Bonifacio
 * CEN 3024 - Software Development 1
 * 07 April 2024
 * CheckOut_Controller.java
 * Controller for the CheckOut_Scene.fxml
 */
public class CheckOut_Controller implements Initializable {
    private Library library = new Library();
    private final ArrayList<Book> collection = new ArrayList<>();

    @FXML
    private Label dueDate;

    @FXML
    private TextField titleInput;

    public void setLibrary(Library library) {
        this.library = library;
    }

    /*
     * method: goBack
     * parameters: ActionEvent
     * return: none
     * purpose: switch the current scene back to the Main_Scene.
     */
    @FXML
    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main_Scene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /*
     * method: checkOut
     * parameters: ActionEvent
     * return: none
     * purpose: calls the checkOutBook() method.
     */
    public void checkOut() {
        String title = titleInput.getText();
        String dateString = library.checkOutBook(title, this.collection);

        dueDate.setText(dateString);
        dueDate.setVisible(true);
    }

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
            System.err.println(e.getMessage());
        }
    }
}

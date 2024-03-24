package com.lms;

import com.lms.model.Book;
import com.lms.model.Library;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/*
 * Kevin Bonifacio
 * CEN 3024 - Software Development 1
 * 24 March 2024
 * Remove_Controller.java
 * Controller for the Remove_Scene.fxml
 */
public class Remove_Controller implements Initializable {
    private Library library;

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


    public void setLibrary(Library library) {
        this.library = library;
    }

    /*
     * method: goBack
     * parameters: ActionEvent
     * return: void
     * purpose: switch the current scene back to the Main_Scene.
     */
    public void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main_Scene.fxml"));
        Parent root = loader.load();

        Main_Controller mainController = loader.getController();
        mainController.setCollection(library.getCollection());

        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /*
     * method: removeBook
     * parameters: ActionEvent
     * return: void
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

                for (Book book : library.getCollection()) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

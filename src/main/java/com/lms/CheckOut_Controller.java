package com.lms;

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
import java.util.ResourceBundle;

/*
 * Kevin Bonifacio
 * CEN 3024 - Software Development 1
 * 24 March 2024
 * CheckOut_Controller.java
 * Controller for the CheckOut_Scene.fxml
 */
public class CheckOut_Controller implements Initializable {
    private Library library;

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
     * return: void
     * purpose: switch the current scene back to the Main_Scene.
     */
    @FXML
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
     * method: checkOut
     * parameters: ActionEvent
     * return: void
     * purpose: calls the checkOutBook() method.
     */
    public void checkOut() {
        String title = titleInput.getText();
        String date = library.checkOutBook(title);

        dueDate.setText(date);
        dueDate.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

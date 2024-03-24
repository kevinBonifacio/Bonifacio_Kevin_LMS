package com.lms;

import com.lms.model.Library;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/*
 * Kevin Bonifacio
 * CEN 3024 - Software Development 1
 * 24 March 2024
 * Add_Controller.java
 * Controller for the Add_Scene.fxml
 */
public class Add_Controller {
    private Library library;

    @FXML
    private TextField fileInput;

    @FXML
    private Label addLabel;

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
     * method: addBooks
     * parameters: ActionEvent
     * return: void
     * purpose: calls the addBooksFromFile() method.
     */
    @FXML
    public void addBooks() {
        String fileName = fileInput.getText();
        if(library.addBooksFromFile(fileName)) {
            addLabel.setText("Books added successfully.");
        } else {
            addLabel.setText("File not found");
        }
        addLabel.setVisible(true);
    }
}

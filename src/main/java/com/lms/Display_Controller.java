package com.lms;

import com.lms.model.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Kevin Bonifacio
 * CEN 3024 - Software Development 1
 * 24 March 2024
 * Display_Controller.java
 * Controller for the Display_Scene.fxml
 */
public class Display_Controller {

    private ArrayList<Book> collection;

    @FXML
    private ListView<Book> textDisplay;

    public void setCollection(ArrayList<Book> collection) {
        this.collection = collection;
        textDisplay.getItems().addAll(collection);
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
        mainController.setCollection(collection);

        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}

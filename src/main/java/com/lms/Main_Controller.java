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
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/*
 * Kevin Bonifacio
 * CEN 3024 - Software Development 1
 * 24 March 2024
 * Main_Controller.java
 * Controller for the Main_Scene.fxml
 */
public class Main_Controller implements Initializable{
    private final Library library = new Library();

    public void setCollection(ArrayList<Book> collection) {
        this.library.updateCollection(collection);
    }

    /*
     * method: switchToAdd_Scene
     * parameters: ActionEvent
     * return: void
     * purpose: switch the current scene to Add_Scene.
     */
    @FXML
    public void switchToAdd_Scene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Add_Scene.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /*
     * method: switchToRemove_Scene
     * parameters: ActionEvent
     * return: void
     * purpose: switch the current scene to Remove_Scene.
     */
    @FXML
    public void switchToRemove_Scene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Remove_Scene.fxml"));
        Parent root = loader.load();

        Remove_Controller removeController = loader.getController();
        removeController.setLibrary(library);

        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /*
     * method: switchToCheckOut_Scene
     * parameters: ActionEvent
     * return: void
     * purpose: switch the current scene to CheckOut_Scene.
     */
    @FXML
    public void switchToCheckOut_Scene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CheckOut_Scene.fxml"));
        Parent root = loader.load();

        CheckOut_Controller checkOutController = loader.getController();
        checkOutController.setLibrary(library);

        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /*
     * method: switchToCheckIn_Scene
     * parameters: ActionEvent
     * return: void
     * purpose: switch the current scene to CheckIn_Scene.
     */
    @FXML
    public void switchToCheckIn_Scene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CheckIn_Scene.fxml"));
        Parent root = loader.load();

        CheckIn_Controller checkInController = loader.getController();
        checkInController.setLibrary(library);

        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /*
     * method: switchToDisplay_Scene
     * parameters: ActionEvent
     * return: void
     * purpose: switch the current scene to Display_Scene.
     */
    @FXML
    public void switchToDisplay_Scene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Display_Scene.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void exit_out(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

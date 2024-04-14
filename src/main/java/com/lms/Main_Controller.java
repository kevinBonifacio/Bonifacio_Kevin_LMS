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

/**
 * @author Kevin Bonifacio
 * CEN 3024 - Software Development 1
 * 14 April 2024
 * Main_Controller.java
 * Controller for the Main_Scene.fxml
 */
public class Main_Controller extends controller {

    /**
     * method: switchToAdd_Scene
     * @param event
     * purpose: Switch the current scene to Add_Scene.
     */
    @FXML
    public void switchToAdd_Scene(ActionEvent event) throws IOException {
        switchScene(event, "Add_Scene.fxml");
    }

    /**
     * method: switchToRemove_Scene
     * @param event
     * purpose: Switch the current scene to Remove_Scene.
     */
    @FXML
    public void switchToRemove_Scene(ActionEvent event) throws IOException {
        switchScene(event, "Remove_Scene.fxml");
    }

    /**
     * method: switchToCheckOut_Scene
     * @param event
     * purpose: Switch the current scene to CheckOut_Scene.
     */
    @FXML
    public void switchToCheckOut_Scene(ActionEvent event) throws IOException {
        switchScene(event, "CheckOut_Scene.fxml");
    }

    /**
     * method: switchToCheckIn_Scene
     * @param event
     * purpose: Switch the current scene to CheckIn_Scene.
     */
    @FXML
    public void switchToCheckIn_Scene(ActionEvent event) throws IOException {
        switchScene(event, "CheckIn_Scene.fxml");
    }

    /**
     * method: switchToDisplay_Scene
     * @param event
     * purpose: Switch the current scene to Display_Scene.
     */
    @FXML
    public void switchToDisplay_Scene(ActionEvent event) throws IOException {
        switchScene(event, "Display_Scene.fxml");
    }

    /**
     * method: exit_out
     * @param event
     * purpose: Exit the application.
     */
    @FXML
    public void exit_out(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

}

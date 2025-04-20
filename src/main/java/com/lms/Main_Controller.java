package com.lms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main_Controller.java
 * Controller for the Main_Scene.fxml
 * @author Kevin Bonifacio
 */
public class Main_Controller extends controller {
    private static final Logger logger = LogManager.getLogger(Main_Controller.class);

    /**
     * method: switchToAdd_Scene
     * purpose: Switch the current scene to Add_Scene.
     * @param event ActionEvent
     */
    @FXML
    public void switchToAdd_Scene(ActionEvent event) throws IOException {
        logger.info("Switching to Add_Scene.fxml");
        switchScene(event, "Add_Scene.fxml");
    }

    /**
     * method: switchToRemove_Scene
     * purpose: Switch the current scene to Remove_Scene.
     * @param event ActionEvent
     */
    @FXML
    public void switchToRemove_Scene(ActionEvent event) throws IOException {
        logger.info("Switching to Remove_Scene.fxml");
        switchScene(event, "Remove_Scene.fxml");
    }

    /**
     * method: switchToCheckOut_Scene
     * purpose: Switch the current scene to CheckOut_Scene.
     * @param event ActionEvent
     */
    @FXML
    public void switchToCheckOut_Scene(ActionEvent event) throws IOException {
        logger.info("Switching to CheckOut_Scene.fxml");
        switchScene(event, "CheckOut_Scene.fxml");
    }

    /**
     * method: switchToCheckIn_Scene
     * purpose: Switch the current scene to CheckIn_Scene.
     * @param event ActionEvent
     */
    @FXML
    public void switchToCheckIn_Scene(ActionEvent event) throws IOException {
        logger.info("Switching to CheckIn_Scene.fxml");
        switchScene(event, "CheckIn_Scene.fxml");
    }

    /**
     * method: switchToDisplay_Scene
     * purpose: Switch the current scene to Display_Scene.
     * @param event ActionEvent
     */
    @FXML
    public void switchToDisplay_Scene(ActionEvent event) throws IOException {
        logger.info("Switching to Display_Scene.fxml");
        switchScene(event, "Display_Scene.fxml");
    }

    /**
     * method: exit_out
     * purpose: Exit the application.
     * @param event ActionEvent
     */
    @FXML
    public void exit_out(ActionEvent event) {
        logger.info("Exiting application");
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

}

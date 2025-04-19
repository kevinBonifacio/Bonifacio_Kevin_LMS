package com.lms;


import com.lms.model.Library;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


/**
 * Add_Controller.java
 * Controller for the Add_Scene.fxml
 * @author Kevin Bonifacio
 */
public class Add_Controller extends controller {
    Library library = new Library();
    private static final Logger logger = LogManager.getLogger("LOGS");

    @FXML
    private TextField fileInput;

    @FXML
    private Label addLabel;

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
     * method: addBooks
     * purpose: calls the addBooks() method using the provided database as parameter.
     */
    @FXML
    public void addBooks() {
        String dataBaseName = fileInput.getText();
        logger.debug("Attempting to add books using file: {}", dataBaseName);

        try {
            if (library.addBooks(dataBaseName)) {
                addLabel.setText("Books added successfully");
                logger.info("Books added from file: {}", dataBaseName);
            } else {
                addLabel.setText("Failed to add books.");
                logger.warn("Book addition failed — file might not exist or is unreadable: {}", dataBaseName);
            }
        } catch (Exception e) {
            addLabel.setText("An unexpected error occurred.");
            logger.error("Unexpected exception while adding books from {}: {}", dataBaseName, e.getMessage(), e);
        }
        addLabel.setVisible(true);
    }
}

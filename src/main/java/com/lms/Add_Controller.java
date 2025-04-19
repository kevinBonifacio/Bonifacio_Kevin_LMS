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
 * CEN 3024 - Software Development 1
 * 14 April 2024
 * Add_Controller.java
 * Controller for the Add_Scene.fxml
 * @author Kevin Bonifacio
 */
public class Add_Controller extends controller {
    Library library = new Library();
    Logger logger = LogManager.getLogger("LOGS");

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

        if(library.addBooks(dataBaseName)) {
            addLabel.setText("Books added successfully");
            logger.info("Successfully added books from database file: {}", dataBaseName);
        } else {
            addLabel.setText("Failed to add books.");
            logger.error("Failed to add books. Could not locate or read from database file: {}", dataBaseName);
        }
        addLabel.setVisible(true);
    }
}

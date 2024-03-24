module com.lms.controller {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.lms;
    exports com.lms.model;
    opens com.lms to javafx.fxml;
}
module com.lms.controller {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.lms;
    opens com.lms to javafx.fxml;
}
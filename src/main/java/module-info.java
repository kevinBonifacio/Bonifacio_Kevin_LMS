module com.lms.controller {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.lms;
    exports com.lms.model;
    opens com.lms to javafx.fxml;
}
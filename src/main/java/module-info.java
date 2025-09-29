module ktour {
    requires javafx.controls;
    requires javafx.fxml;

    opens ktour to javafx.fxml;
    exports ktour;
}

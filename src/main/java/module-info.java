module com.ricochet {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ricochet to javafx.fxml;
    exports com.ricochet;
}
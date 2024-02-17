module sample.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens sample.app to javafx.fxml;
    exports sample.app;
}
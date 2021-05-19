module fr.deadmeon {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens fr.deadmeon to javafx.fxml;
    exports fr.deadmeon;
}
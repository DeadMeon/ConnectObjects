module fr.deadmeon {
    requires javafx.controls;
    requires javafx.fxml;

    opens fr.deadmeon to javafx.fxml;
    exports fr.deadmeon;
}
module fr.deadmeon {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires com.google.gson;

    opens fr.deadmeon to javafx.fxml, com.google.gson;
    exports fr.deadmeon;
    opens fr.deadmeon.controller to javafx.fxml;
    exports fr.deadmeon.controller;
    opens fr.deadmeon.request.TwitchIntegration to com.google.gson;
    exports fr.deadmeon.request.TwitchIntegration;
    opens fr.deadmeon.data to com.google.gson;
    exports fr.deadmeon.data;
    opens fr.deadmeon.entity to com.google.gson;
    exports fr.deadmeon.entity;
}
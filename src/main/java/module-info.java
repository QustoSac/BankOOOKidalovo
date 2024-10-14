module com.example.bankoookidalovo.bankoookidalovo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.example.bankoookidalovo.bankoookidalovo to javafx.fxml;
    exports com.example.bankoookidalovo.bankoookidalovo;
}
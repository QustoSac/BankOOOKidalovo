package com.example.bankoookidalovo.bankoookidalovo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BankAccountApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bank_account.fxml"));
        GridPane root = loader.load();

        Scene scene = new Scene(root, 400, 250);
        primaryStage.setTitle("Менеджер по работе с банковскими счетами");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

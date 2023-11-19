package com.mantto.gestormanto_fx;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import javafx.stage.StageStyle;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @FXML
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        // cargar el archivo FXML login
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocumentRegistro.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
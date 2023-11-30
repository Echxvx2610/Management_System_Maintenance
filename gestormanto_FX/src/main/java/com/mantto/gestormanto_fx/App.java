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
        this.primaryStage = primaryStage;
        try {
            // inicializamos como primera ventana el log in
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocumentLogin.fxml"));
            Parent root = loader.load(); // creamos una raiz para el archivo FXML
            Scene scene = new Scene(root); // creamos una escena para el archivo FXML
            primaryStage.setScene(scene); // establecemos la escena
            primaryStage.setResizable(false); // Opcional: Para hacerla no redimensionable
            primaryStage.show(); // mostramos la ventana

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

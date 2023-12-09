package com.mantto.gestormanto_fx;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
//import javafx.stage.StageStyle;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @FXML private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        // cargar el archivo FXML login
        this.primaryStage = primaryStage;
        try {
            // inicializamos como primera ventana el log in
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocumentMainv3.fxml"));
            Parent root = loader.load(); // creamos una raiz para el archivo FXML
            Scene scene = new Scene(root); // creamos una escena para el archivo FXML
            primaryStage.setScene(scene); // establecemos la escena
            primaryStage.setResizable(false); // Opcional: Para hacerla no redimensionable
            // establecemos el icono de la ventana
            primaryStage.getIcons().add(new Image(("D:\\programmer\\Java\\Sistemas_comp\\Management_System_Maintenance\\gestormanto_FX\\src\\main\\resources\\img\\icono_app.png")));
            primaryStage.setTitle("Gestor de Mantenimiento");
            primaryStage.show(); // mostramos la ventana

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

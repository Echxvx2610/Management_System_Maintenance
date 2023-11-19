package com.mantto.gestormanto_fx;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class FXMLDocumentSingController implements Initializable {
    @FXML
    private Stage primaryStage;

    @FXML
    private Button loginButton;

    @FXML
    private Button loginButton1;

    @FXML
    private TextField userField;

    @FXML
    private TextField userField1;

    @FXML
    private TextField userField11;

    @FXML
    private TextField userField111;

    @FXML
    private TextField userField2;

    @FXML
    private TextField userField21;

    @FXML
    void pressLogin(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // if (primaryStage != null) {
        // primaryStage.setWidth(600); // Establecer el ancho deseado
        // primaryStage.setHeight(400); // Establecer la altura deseada
        // primaryStage.setResizable(false); // Opcional: Para hacerla no
        // redimensionable
        // }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

}

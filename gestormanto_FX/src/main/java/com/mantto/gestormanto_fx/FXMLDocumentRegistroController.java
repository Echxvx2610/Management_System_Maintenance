package com.mantto.gestormanto_fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLDocumentRegistroController implements Initializable {
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
    public void initialize(URL location, ResourceBundle resources) {
        if (primaryStage != null) {
            primaryStage.setWidth(600); // Establecer el ancho deseado
            primaryStage.setHeight(400); // Establecer la altura deseada
            primaryStage.setResizable(false); // Opcional: Para hacerla no redimensionable
        }
    }

}

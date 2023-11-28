package com.mantto.gestormanto_fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLDocumentUsuarioController implements Initializable {
    @FXML
    private Button buttonAgregar;

    @FXML
    private TextField gridApellido;

    @FXML
    private TextField gridContraseña;

    @FXML
    private TextField gridCorreo;

    @FXML
    private TextField gridNombre;

    @FXML
    private TextField gridUsuario;

    @FXML
    private TableView<?> tableView;

    @FXML
    void pressAgregar(ActionEvent event) {
        // crear un objeto usuario
        Usuario usuario1 = new Usuario(gridNombre.getText(),
                gridApellido.getText(),
                gridCorreo.getText(),
                gridUsuario.getText(),
                gridContraseña.getText(),
                gridContraseña.getText());

        System.out.println(usuario1);
    }


    private Stage primaryStage;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (primaryStage != null) {
            primaryStage.setWidth(700); // Establecer el ancho deseado
            primaryStage.setHeight(500); // Establecer la altura deseada
            primaryStage.setResizable(false); // Opcional: Para hacerla no redimensionable
        }
    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }
}

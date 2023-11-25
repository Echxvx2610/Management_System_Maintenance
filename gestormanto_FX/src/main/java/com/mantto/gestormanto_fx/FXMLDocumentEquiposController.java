package com.mantto.gestormanto_fx;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.swing.*;

public class FXMLDocumentEquiposController implements Initializable {
    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private TextField gridNombre;

    @FXML
    private TextField gridModelo;

    @FXML
    private TextField gridMarca;

    @FXML
    private TextField gridLocalizacion;

    @FXML
    private TextField gridDescripcion;

    @FXML
    private Button buttonAgregar;
    @FXML
    private Stage primaryStage;

    void pressAgregar(ActionEvent event) {
        // Obtener el valor seleccionado del ChoiceBox
        String estadoEquipo = choiceBox.getValue();

        // crearar objeto de equipo
        Equipo equipo = new Equipo(
                gridNombre.getText(),
                gridModelo.getText(),
                gridMarca.getText(),
                choiceBox.getValue().toString(),
                gridLocalizacion.getText(),
                gridDescripcion.getText());

        try{
            if (equipo.getName().isEmpty() || equipo.getEstado_Equipo().isEmpty() || equipo.getNota().isEmpty() || equipo.getLocalizacion().isEmpty() || equipo.getMarca().isEmpty() || equipo.getModelo().isEmpty());
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos");
        }
        // tomamos los datos del equipo y los mandamos a la base de datos gestormantto tabla equipos
    };


    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (primaryStage != null) {
            primaryStage.setWidth(700); // Establecer el ancho deseado
            primaryStage.setHeight(500); // Establecer la altura deseada
            primaryStage.setResizable(false); // Opcional: Para hacerla no redimensionable
        }
        // Configurar opciones para el ChoiceBox
        ObservableList<String> options = FXCollections.observableArrayList("Activo", "Inactivo");
        choiceBox.setItems(options);
        choiceBox.setValue("Activo");
    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

}


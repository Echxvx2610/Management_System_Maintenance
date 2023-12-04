package com.mantto.gestormanto_fx;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

// importaciones javaFX
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

// importaciones metodo initialize
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
// importaciones popups JOptionPane
import javax.swing.*;
// importaicones librerias MySQL connector
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.stage.FileChooser;
import javafx.stage.Stage;



public class FXMLDocumentPlanController implements Initializable {
    @FXML private TableColumn<?, ?> cantidadColumn;
    @FXML private TableColumn<?, ?> cantidadColumn1;
    @FXML private ChoiceBox<?> choiceFrecc;
    @FXML private ChoiceBox<?> choiceRealizado;
    @FXML private TextField gridNombre;
    @FXML private TextField gridNotaAct;
    @FXML private TextField gridNotaPlan;
    @FXML private TextField gridParte;
    @FXML private TextField gridTiempo;
    @FXML private TableColumn<?, ?> idColumn;
    @FXML private TableColumn<?, ?> idColumn1;
    @FXML private TableColumn<?, ?> nombreColumn;
    @FXML private TableColumn<?, ?> nombreColumn1;
    @FXML private TableColumn<?, ?> proveedorColumn;
    @FXML private TableColumn<?, ?> proveedorColumn1;
    @FXML private TableView<?> tableViewAct;
    @FXML private TableView<?> tableViewPlan;
    @FXML private TableColumn<?, ?> unidadColumn;
    private Stage primaryStage;

    // metodos de la escena
    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void initialize (URL url, ResourceBundle resourceBundle){
        // Configurar el tamaño de la ventana
        if (primaryStage != null) {
            primaryStage.setWidth(700); // Establecer el ancho deseado
            primaryStage.setHeight(500); // Establecer la altura deseada
            primaryStage.setResizable(false); // Opcional: Para hacerla no redimensionable
        }
    }

    // metodos del controlador


}

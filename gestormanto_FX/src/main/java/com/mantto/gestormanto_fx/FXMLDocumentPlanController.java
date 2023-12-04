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
import java.util.ArrayList;
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
import org.w3c.dom.ls.LSOutput;


public class FXMLDocumentPlanController implements Initializable {
    @FXML private ChoiceBox<String> choiceFrecc;
    @FXML private ChoiceBox<String> choiceRealizado;
    @FXML private ListView<Actividad> listViewActividades;
    @FXML private TextField gridNotaAct;
    @FXML private TextField gridParte;
    @FXML private TextField gridTiempo;
    @FXML private TableView<Actividad> tableViewAct;
    @FXML private TableView<Plan> tableViewPlan;
    @FXML private Button agregarPlan;
    @FXML private Button agregarAct;
    @FXML private TableColumn<Actividad, String> actividadColumn;
    @FXML private TableColumn<Plan, String> frecuenciaColumn;
    @FXML private TableColumn<Actividad, Integer> idActColumn;
    @FXML private TableColumn<Plan, Integer> idPlanColumn;
    @FXML private TableColumn<Plan, String> nombreColumn;
    @FXML private TableColumn<Plan, String> notaActColumn;
    @FXML private TableColumn<Plan, String> notaPlanColumn;
    @FXML private TableColumn<Plan, String> realizadoColumn;
    @FXML private TableColumn<Actividad, Integer> tiempoColumn;


    private  ObservableList<Actividad> listaAct = FXCollections.observableArrayList();
    private  ObservableList<Plan> listaPlan = FXCollections.observableArrayList();

    private Stage primaryStage;

    // metodos de la escena
    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void initialize (URL url, ResourceBundle resourceBundle){
        // Configurar el tamaño de la ventana
        if (primaryStage != null) {
            primaryStage.setWidth(500); // Establecer el ancho deseado
            primaryStage.setHeight(500); // Establecer la altura deseada
            primaryStage.setResizable(false); // Opcional: Para hacerla no redimensionable
        }
        // setear los valores de los choiceBox en la escena

        ObservableList<String> opciones_frecc = FXCollections.observableArrayList("Diario", "Semanada","Mensual");
        choiceFrecc.setItems(opciones_frecc);
        choiceFrecc.setValue("N/A");
        /*
        ObservableList<String> opciones_realizado = FXCollections.observableArrayList("Realizado", "No Realizado");
        choiceRealizado.setItems(opciones_realizado);
        choiceRealizado.setValue("No Realizado");
        */
        List<Actividad> actividadesDisponibles = cargarActividadesPlan();
        ObservableList<Actividad> observableActividades = FXCollections.observableArrayList(actividadesDisponibles);
        listViewActividades.setItems(observableActividades);
        listViewActividades.setCellFactory(param -> new ActividadListCell() {

        });

        // Configurar las celdas de la TableView para mostrar las actividades
        idActColumn.setCellValueFactory(new PropertyValueFactory<>("idTemporal"));
        actividadColumn.setCellValueFactory(new PropertyValueFactory<>("parte"));
        tiempoColumn.setCellValueFactory(new PropertyValueFactory<>("tiempo"));
        notaActColumn.setCellValueFactory(new PropertyValueFactory<>("nota"));


        // cargar los datos desde la base de datos
        cargarActividades();

    }

    // metodos del controlador
    @FXML void pressAgregarPlan(ActionEvent event){
        JOptionPane.showMessageDialog(null, "Funcionalidad no disponible");
    }

    @FXML void pressAgregarAct(ActionEvent event) {
        // validar que los campos no esten vacios(parte,tiempo,nota)
        if (gridParte.getText().isEmpty() || gridTiempo.getText().isEmpty() || gridNotaAct.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Porfavor llene todos los campos");
            return;
        }
        //tomar valores de parte,tiempo y nota de la actividad y creamos un objeto de Actividad
        Actividad act = new Actividad(
                gridParte.getText()
                ,Integer.parseInt(gridTiempo.getText()),
                gridNotaAct.getText());
        try {
            Connection conn = null;
            try {
                conn = DatabaseConnector.getConnection();
                String query = "INSERT INTO actividades (parte, tiempo, nota) VALUES (?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, act.getParte());
                pstmt.setInt(2, act.getTiempo());
                pstmt.setString(3, act.getNota());
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Actividad agregada");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al agregar la actividad");
                e.printStackTrace();
            } finally {
                //cerrar la conexion con la base de datos
                if (conn != null) {
                    conn.close();
                }
                // limpiar los campos
                gridParte.setText("");
                gridTiempo.setText("");
                gridNotaAct.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };

    private void cargarActividades() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        // limpiar la lista antes de cargar actividades
        listaAct.clear();
        try{
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM actividades");
            rs = stmt.executeQuery();

            while(rs.next()){
                // recorremos la base de datos y cargamos las actividades
                Actividad act = new Actividad();
                act.setIdTemporal(rs.getInt("id"));
                act.setParte(rs.getString("parte"));
                act.setTiempo(rs.getInt("tiempo"));
                act.setNota(rs.getString("nota"));

                listaAct.add(act);
            }
            // Agrega la lista de actividades a la TableView
            tableViewAct.setItems(listaAct);
            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    private  List<Actividad> cargarActividadesPlan() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Actividad> actividadsDisponibles = new ArrayList<>();
        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM actividades");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Actividad act = new Actividad();
                act.setIdTemporal(rs.getInt("id"));
                act.setParte(rs.getString("parte"));
                act.setTiempo(rs.getInt("tiempo"));
                act.setNota(rs.getString("nota"));

                actividadsDisponibles.add(act);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return actividadsDisponibles;
    }

    private static class ActividadStringConverter extends javafx.util.StringConverter<Actividad> {
        @Override
        public String toString(Actividad actividad) {
            return actividad != null ? actividad.getParte() : "";
        }

        @Override
        public Actividad fromString(String string) {
            // to do
            return null;
        }
    }

    private static class ActividadListCell extends ListCell<Actividad> {
        @Override
        protected void updateItem(Actividad actividad, boolean empty) {
            super.updateItem(actividad, empty);

            if (empty || actividad == null) {
                setText(null);
            } else {
                setText(actividad.getParte());
            }
        }
    }

    // Método para manejar el evento de agregar actividad al plan
    @FXML
    private void agregarActividadAlPlan() {
        Actividad actividadSeleccionada = listViewActividades.getSelectionModel().getSelectedItem();

        if (actividadSeleccionada != null) {
            // Agregar la actividad al plan o realizar otras acciones necesarias
            // Puedes tener una lista en el controlador que almacene las actividades seleccionadas
            // y actualizar la visualización en el ListView.
        }
    }
    void cargarPlanes(){
        // por hacer
    }


}

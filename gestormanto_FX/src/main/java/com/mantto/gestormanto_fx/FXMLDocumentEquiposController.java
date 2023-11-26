package com.mantto.gestormanto_fx;

// importaciones javaFX
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

// importaciones metodo initialize
import java.net.URL;
import java.util.ResourceBundle;
// importaciones popups JOptionPane
import javax.swing.*;
// importaicones librerias MySQL connector
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class FXMLDocumentEquiposController implements Initializable {
    // Elementos de la vista
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
    //Botones
    @FXML
    private Button buttonAgregar;
    @FXML
    private Button buttonActualizar;
    @FXML
    private Stage primaryStage;
    // ids columnas
    @FXML
    private TableColumn<Equipo, Integer> idColumn;
    @FXML
    private TableColumn<Equipo, String> nombreColumn;
    @FXML
    private TableColumn<Equipo, String> modeloColumn;
    @FXML
    private TableColumn<Equipo, String> marcaColumn;
    @FXML
    private TableColumn<Equipo, String> estadoColumn;
    @FXML
    private TableColumn<Equipo, String> localizacionColumn;
    @FXML
    private TableColumn<Equipo, String> descripcionColumn;

    @FXML
    private TableView<Equipo> tableView;
    //coleccion de elementos equipos
    private ObservableList<Equipo> listaEquipos = FXCollections.observableArrayList();

    @FXML public void pressActualizar(ActionEvent event){
        cargarDatosDesdeBD();
    }
    @FXML
    private void pressAgregar(ActionEvent event) {
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
            if (equipo.getNombre().isEmpty() || equipo.getEstado_equipo().isEmpty() || equipo.getNota().isEmpty() || equipo.getLocalizacion().isEmpty() || equipo.getMarca().isEmpty() || equipo.getModelo().isEmpty());
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos");
        }
        // Tomamos los valores del equipo y lo mandamos a la base de datos
        Connection conn = null;
        try{
            conn = DatabaseConnector.getConnection();
            String query = "INSERT INTO equipos (nombre, modelo, marca, disponible, lugar, descripcion) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, equipo.getNombre());
            stmt.setString(2, equipo.getModelo());
            stmt.setString(3, equipo.getMarca());
            stmt.setString(4, equipo.getEstado_equipo());
            stmt.setString(5, equipo.getLocalizacion());
            stmt.setString(6, equipo.getNota());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Equipo registrado exitosamente");
        }catch (SQLException e){
            e.printStackTrace();
            // Manejar errores de la base de datos
        }finally {
            // Cerrar conexión con la base de datos
            // imprimir los datos registrados
            System.out.println("los datos registrados son:");
            System.out.println("nombre: " + equipo.getNombre() + "\n" + "modelo: " + equipo.getModelo() + "\n" + "marca: " + equipo.getMarca() + "\n" + "estado_equipo: " + equipo.getEstado_equipo() + "\n" + "localizacion: " + equipo.getLocalizacion() + "\n" + "nota: " + equipo.getNota());
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                // limpiamos los campos de los textfields
                gridNombre.setText("");
                gridModelo.setText("");
                gridMarca.setText("");
                gridLocalizacion.setText("");
                gridDescripcion.setText("");
                choiceBox.setValue("Activo");
        }
        }
    };

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurar el tamaño de la ventana
        if (primaryStage != null) {
            primaryStage.setWidth(700); // Establecer el ancho deseado
            primaryStage.setHeight(500); // Establecer la altura deseada
            primaryStage.setResizable(false); // Opcional: Para hacerla no redimensionable
        }
        // Configurar opciones para el ChoiceBox
        ObservableList<String> options = FXCollections.observableArrayList("Activo", "Inactivo");
        choiceBox.setItems(options);
        choiceBox.setValue("Activo");

        // Configurar las celdas de la TableView para mostrar los datos de los equipos
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idTemporal"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        modeloColumn.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        marcaColumn.setCellValueFactory(new PropertyValueFactory<>("marca"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado_equipo"));
        localizacionColumn.setCellValueFactory(new PropertyValueFactory<>("localizacion"));
        descripcionColumn.setCellValueFactory(new PropertyValueFactory<>("nota"));

        // Cargar los datos desde la base de datos
        cargarDatosDesdeBD();

    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    public boolean equipoExists(String name) {
        // Verificar si el equipo ya existe en la base de datos
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM equipos WHERE nombre = ?")) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private void cargarDatosDesdeBD() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        // Limpiar la lista antes de cargar nuevos datos
        listaEquipos.clear();
        try {
            conn = DatabaseConnector.getConnection(); // establecer la coneccion
            stmt = conn.prepareStatement("SELECT * FROM equipos"); // preparar la sentencia
            rs = stmt.executeQuery(); // ejecutar query stmt

            while (rs.next()) {
                // recorremos la base de datos y guardamos los datos en objetos de Equipo
                Equipo equipo = new Equipo();
                equipo.setIdTemporal(rs.getInt("id"));
                equipo.setNombre(rs.getString("nombre"));
                equipo.setModelo(rs.getString("modelo"));
                equipo.setMarca(rs.getString("marca"));
                equipo.setEstado_equipo(rs.getString("disponible"));
                equipo.setLocalizacion(rs.getString("lugar"));
                equipo.setNota(rs.getString("descripcion"));

                listaEquipos.add(equipo);
            }
            // Agrega la lista de equipos a la TableView
            tableView.setItems(listaEquipos);

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar errores de la base de datos
        } finally {
            // Cierra conexiones
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

}



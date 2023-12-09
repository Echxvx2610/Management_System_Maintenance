package com.mantto.gestormanto_fx;

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

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class FXMLDocumentOrdenController implements Initializable {
    private ObservableList<GenerarOrden> listaOrdenes = FXCollections.observableArrayList();
    @FXML private Button buttonAgregar;
    @FXML private Button buttonEditar;
    @FXML private Button buttonEliminar;
    @FXML private ChoiceBox<String> choiceEquipo;
    @FXML private ChoiceBox<String> choicePlan;
    @FXML private ChoiceBox<String> choiceUsuario;
    @FXML private ChoiceBox<String> choiceRealizado;
    @FXML private TextField textObservaciones;
    @FXML private TableColumn<GenerarOrden, Integer> usuarioColumn;
    @FXML private TableColumn<GenerarOrden, Integer> equipoColumn;
    @FXML private TableColumn<GenerarOrden, String> estadoColumn;
    @FXML private TableColumn<GenerarOrden, Integer> idColumn;
    @FXML private TableColumn<GenerarOrden, String> observacionColumn;
    @FXML private TableColumn<GenerarOrden, Integer> planColumn;
    @FXML private TableView<GenerarOrden> tableViewOrden;
    @FXML private Button pressMarcar;

    @FXML private Stage primaryStage;

    public void initialize(URL url, ResourceBundle rb) {
        // configurar el tamanho de la ventana
        if (primaryStage != null) {
            primaryStage.setResizable(false);
            primaryStage.setWidth(800);
            primaryStage.setHeight(600);
        }

        ObservableList<String> listaNombresUsuarios = convertirAListaGenerarOrden(obtenerNombresUsuariosDesdeBD());
        choiceUsuario.setItems(listaNombresUsuarios);

        List<String> nombresEquipos = obtenerNombresEquiposDesdeBD();
        ObservableList<String> equiposObservableList = FXCollections.observableArrayList(nombresEquipos);
        choiceEquipo.setItems(equiposObservableList);

        List<String> listaNombresPlanes = obtenerNombresPlanesDesdeBD();
        ObservableList<String> observableNombresPlanes = FXCollections.observableArrayList(listaNombresPlanes);
        choicePlan.setItems(observableNombresPlanes);

        // agregar valores a choiceRealizado
        ObservableList<String> valoresRealizado = FXCollections.observableArrayList("Realizado", "No Realizado");
        choiceRealizado.setItems(valoresRealizado);

        // cargar datos en tableViewOrden
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idTemporal"));
        usuarioColumn.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        equipoColumn.setCellValueFactory(new PropertyValueFactory<>("equipo"));
        planColumn.setCellValueFactory(new PropertyValueFactory<>("plan"));
        observacionColumn.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("realizado"));
        cargarOrdenes();

    }


    private void cargarDatosChoiceBox() {
        // usuarios
        choiceUsuario.getItems().addAll(obtenerNombresUsuariosDesdeBD());

        choiceEquipo.getItems().addAll(obtenerNombresEquiposDesdeBD());

        choicePlan.getItems().addAll(obtenerNombresPlanesDesdeBD());
    }
    private List<String> obtenerNombresUsuariosDesdeBD() {
        List<String> nombresUsuarios = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT nombre, apellido FROM usuarios");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String nombreCompleto = nombre + " " + apellido;
                nombresUsuarios.add(nombreCompleto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nombresUsuarios;
    }

    private ObservableList<String> convertirAListaGenerarOrden(List<String> nombresUsuarios) {
        return FXCollections.observableArrayList(nombresUsuarios);
    }
    private List<String> obtenerNombresEquiposDesdeBD() {
        List<String> nombresEquipos = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT nombre FROM equipos");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String nombreEquipo = resultSet.getString("nombre");
                nombresEquipos.add(nombreEquipo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nombresEquipos;
    }

    private List<String> obtenerNombresPlanesDesdeBD() {
        List<String> nombresPlanes = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT nombre FROM planmantto");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String nombrePlan = resultSet.getString("nombre");
                nombresPlanes.add(nombrePlan);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nombresPlanes;
    }

    private Usuario obtenerUsuarioPorNombre(String nombreUsuario) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM usuarios WHERE nombre = ?");
        ) {
            statement.setString(1, nombreUsuario);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Crea un objeto Usuario con los datos de la base de datos
                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    String apellido = resultSet.getString("apellido");
                    // Otros atributos...

                    return new Usuario(id, nombre, apellido /*, otros atributos... */);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Retorna null si no se encuentra el usuario
    }

    private Equipo obtenerEquipoPorNombre(String nombreEquipo) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM equipos WHERE nombre = ?");
        ) {
            statement.setString(1, nombreEquipo);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Crea un objeto Equipo con los datos de la base de datos
                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    String modelo = resultSet.getString("modelo");
                    // Otros atributos...

                    return new Equipo(id, nombre, modelo /*, otros atributos... */);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Retorna null si no se encuentra el equipo
    }

    private Plan obtenerPlanPorNombre(String nombrePlan) {
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM planmantto WHERE nombre = ?");
        ) {
            statement.setString(1, nombrePlan);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Crea un objeto Plan con los datos de la base de datos
                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    // Otros atributos...

                    return new Plan(id, nombre /*, otros atributos... */);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Retorna null si no se encuentra el plan
    }

    @FXML public void pressAgregarOT(ActionEvent event) {
        // Obtener los valores seleccionados de los ChoiceBox
        boolean realizado = choiceRealizado.getValue().equals("Realizado");
        GenerarOrden orden = new GenerarOrden(choiceUsuario.getValue(), choiceEquipo.getValue(), choicePlan.getValue(), textObservaciones.getText(),realizado);

        // mandar los atributos a la base de datos
        Connection conn = null;
        try {
            conn = DatabaseConnector.getConnection();
            String query = "INSERT INTO ordenes (nombreUsuario, nombreEquipo, nombrePlan, Observaciones,realizado) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, orden.getUsuario());
            pstmt.setString(2, orden.getEquipo());
            pstmt.setString(3, orden.getPlan());
            pstmt.setString(4, orden.getObservaciones());
            pstmt.setBoolean(5, realizado);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Orden agregada exitosamente.");
            // Recargar las órdenes en la tabla
            cargarOrdenes();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar la orden.");
            e.printStackTrace();
        }

    }
    @FXML void pressEditarOT(ActionEvent event) {
        // Obtener los valores seleccionados de la tabla
        GenerarOrden orden = tableViewOrden.getSelectionModel().getSelectedItem();

        // Verificar si se ha seleccionado una orden
        if (orden == null) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una orden.");
            return;
        }
        // crear una nueva orden con los valores de los textFields
        GenerarOrden ordenEdicion = new GenerarOrden(
                orden.getUsuario(),
                orden.getEquipo(),
                orden.getPlan(),
                orden.getObservaciones(),
                orden.isRealizado()
        );
        // crear la ventana de dialogo
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Editar orden");
        dialog.setHeaderText("Editando orden con ID: " + orden.getIdTemporal());

        // Crear el contenido de la ventana de dialogo
        TextField textFieldID = new TextField(String.valueOf(orden.getIdTemporal()));
        TextField textFieldUsuario = new TextField(ordenEdicion.getUsuario());
        TextField textFieldEquipo = new TextField(ordenEdicion.getEquipo());
        TextField textFieldPlan = new TextField(ordenEdicion.getPlan());
        TextField textFieldObservaciones = new TextField(ordenEdicion.getObservaciones());

        // agregar los campos al dialogo
        dialog.getDialogPane().setContent(new VBox(4,
                textFieldID,
                textFieldUsuario,
                textFieldEquipo,
                textFieldPlan,
                textFieldObservaciones));
        // Botones de acciones
        ButtonType buttonTypeGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeGuardar, buttonTypeCancelar);

        // configurar el resultado de la ventana de dialogo
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeGuardar) {
                return new Pair<>(textFieldUsuario.getText(), textFieldEquipo.getText());
            }
            return null;
        });
        // mostrar la ventana de dialogo
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(nuevosValores -> {
            // Actualizar los valores de la orden en la base de datos
            ordenEdicion.setIdTemporal(Integer.parseInt(textFieldID.getText()));
            ordenEdicion.setUsuario(textFieldUsuario.getText());
            ordenEdicion.setEquipo(textFieldEquipo.getText());
            ordenEdicion.setPlan(textFieldPlan.getText());
            ordenEdicion.setObservaciones(textFieldObservaciones.getText());
            actualizarOT(ordenEdicion);
            // Recargar las órdenes en la tabla
            cargarOrdenes();
        });
    }

    public void actualizarOT(GenerarOrden orden){
        try(Connection conn = DatabaseConnector.getConnection()) {
            String query = "UPDATE ordenes SET nombreUsuario = ?, nombreEquipo = ?, nombrePlan = ?, Observaciones = ?, realizado = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, orden.getUsuario());
            pstmt.setString(2, orden.getEquipo());
            pstmt.setString(3, orden.getPlan());
            pstmt.setString(4, orden.getObservaciones());
            pstmt.setBoolean(5, orden.isRealizado());
            pstmt.setInt(6, orden.getIdTemporal());
            pstmt.executeUpdate();

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0){
                JOptionPane.showMessageDialog(null, "Orden actualizada exitosamente.");
                // Recargar las órdenes en la tabla
                cargarOrdenes();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron coincidencias.");
            }

        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la orden.");
            e.printStackTrace();
        }
    }
    @FXML
    void pressEliminarOT(ActionEvent event) {
        // Obtener la orden seleccionada en la tabla
        GenerarOrden ordenSeleccionada = tableViewOrden.getSelectionModel().getSelectedItem();
        // verificar si se ha seleccionado una orden
        if (ordenSeleccionada == null ){
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una orden.");
            return;
        }
        // confirmar eliminacion de la orden
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea eliminar la orden?");
        if (confirmacion == JOptionPane.YES_OPTION) {
            // eliminar la orden en la base de datos
            try(Connection conn = DatabaseConnector.getConnection()){
                    PreparedStatement stmt = conn.prepareStatement("DELETE FROM ordenes WHERE id = ?");
                    stmt.setInt(1, ordenSeleccionada.getIdTemporal());

                int filasAfectadas = stmt.executeUpdate();
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Orden eliminada exitosamente.");
                    // Recargar las órdenes en la tabla
                    cargarOrdenes();
                }else{
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar la orden.");}
            }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Ocurrio un error al eliminar la orden.");
        }
    }
    }

    @FXML void pressMarcarOT(ActionEvent event) {
        // Obtener la orden seleccionada de la tabla
        GenerarOrden orden = tableViewOrden.getSelectionModel().getSelectedItem();

        // Verificar si se ha seleccionado una orden
        if (orden == null) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una orden.");
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea cambiar el estado de la orden?");
        if (confirmacion == JOptionPane.YES_OPTION) {
            // Cambiar el estado
            orden.setRealizado(!orden.isRealizado());
            // Actualizar el estado en la base de datos
            try (Connection conn = DatabaseConnector.getConnection()) {
                String query = "UPDATE ordenes SET realizado = ? WHERE id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setBoolean(1, orden.isRealizado());
                    pstmt.setInt(2, orden.getIdTemporal());
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Estado de la orden actualizado exitosamente.");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al actualizar el estado de la orden.");
                e.printStackTrace();
            }
        }else {
            return;
        }
        // Recargar las órdenes en la tabla
        cargarOrdenes();
    }
    @FXML void cargarOrdenes(){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        listaOrdenes.clear();

        try{
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM ordenes");
            rs = stmt.executeQuery();

            while (rs.next()) {
                // recorremos la tabla de ordenes
                GenerarOrden orden = new GenerarOrden();
                orden.setIdTemporal(rs.getInt("id"));
                orden.setUsuario(rs.getString("nombreUsuario"));
                orden.setEquipo(rs.getString("nombreEquipo"));
                orden.setPlan(rs.getString("nombrePlan"));
                orden.setObservaciones(rs.getString("Observaciones"));
                orden.setRealizado(rs.getBoolean("realizado"));
                listaOrdenes.add(orden);
            }
            // agregar la lista de ordenes a la TableView
            tableViewOrden.setItems(listaOrdenes);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            // cerrar conexiones
            try{
                if (rs != null){
                    rs.close();
                }
                if (stmt != null){
                    stmt.close();
                }
                if (conn != null){
                    conn.close();
                }
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
}


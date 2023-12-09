package com.mantto.gestormanto_fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

// importaicones librerias MySQL connector
import java.sql.ResultSet;

public class FXMLDocumentConsumibleController implements Initializable {

    @FXML private Stage primaryStage;
    @FXML private Button buttonAgregar;
    @FXML private Button buttonEditar;
    @FXML private Button buttonEliminar;
    @FXML private Button buttonActualizar;
    @FXML private TextField gridCantidad;

    @FXML private TextField gridNombre;
    @FXML private TextField gridObservaciones;
    @FXML private TextField gridPrecio;
    @FXML private TextField gridProveedor;
    @FXML private TextField gridUnidad;


    @FXML private TableColumn<Consumible, Integer> idColumn;
    @FXML private TableColumn<Consumible, Integer> cantidadColumn;
    @FXML private TableColumn<Consumible, String> nombreColumn;
    @FXML private TableColumn<Consumible, String> observacionesColumn;
    @FXML private TableColumn<Consumible, Double> precioColumn;
    @FXML private TableColumn<Consumible, String> proveedorColumn;
    @FXML private TableColumn<Consumible, String> unidadColumn;
    @FXML private TableView<Consumible> tableView;
    //coleccion de consumibles
    private ObservableList<Consumible> listaConsumibles = FXCollections.observableArrayList();

    // metodos propios del controlador
    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (primaryStage != null) {
            primaryStage.setWidth(700); // Establecer el ancho deseado
            primaryStage.setHeight(500); // Establecer la altura deseada
            primaryStage.setResizable(false); // Opcional: Para hacerla no redimensionable
        }

        // configurar las celdas de la tableview para mostrar los datos
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idTemporal"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        proveedorColumn.setCellValueFactory(new PropertyValueFactory<>("proveedor"));
        cantidadColumn.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        observacionesColumn.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));
        unidadColumn.setCellValueFactory(new PropertyValueFactory<>("unidad"));

        // cargar los datos de la base de datos
        cargarDatosDesdeBD();

    }

    // metodos para controlar escena
    @FXML
    private void pressEditar(ActionEvent event) {
        // Obtener el consumible seleccionado
        Consumible consumibleSeleccionado = tableView.getSelectionModel().getSelectedItem();

        // Verificar si se ha seleccionado un consumible
        if (consumibleSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un consumible para editar.");
            return;
        }

        // Crear un nuevo consumible con los datos actuales (para mostrar en la interfaz de edición)
        Consumible consumibleEdicion = new Consumible(
                consumibleSeleccionado.getNombre(),
                consumibleSeleccionado.getProveedor(),
                consumibleSeleccionado.getCantidad(),
                consumibleSeleccionado.getUnidad(),
                consumibleSeleccionado.getPrecio(),
                consumibleSeleccionado.getObservaciones()
        );

        // Crear un diálogo de edición
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Editar Consumible");
        dialog.setHeaderText("Editando consumible con ID: " + consumibleSeleccionado.getIdTemporal());

        // Crear campos de texto para la edición
        TextField textFieldNombre = new TextField(consumibleEdicion.getNombre());
        TextField textFieldProveedor = new TextField(consumibleEdicion.getProveedor());
        TextField textFieldCantidad = new TextField(String.valueOf(consumibleEdicion.getCantidad()));
        TextField textFieldUnidad = new TextField(consumibleEdicion.getUnidad());
        TextField textFieldPrecio = new TextField(String.valueOf(consumibleEdicion.getPrecio()));
        TextField textFieldObservaciones = new TextField(consumibleEdicion.getObservaciones());

        // Añadir campos al diálogo
        dialog.getDialogPane().setContent(new VBox(8,
                new Label("Nombre:"), textFieldNombre,
                new Label("Cantidad:"), textFieldCantidad,
                new Label("Proveedor:"), textFieldProveedor,
                new Label("Unidad:"), textFieldUnidad,
                new Label("Precio:"), textFieldPrecio,
                new Label("Observaciones:"), textFieldObservaciones
        ));

        // Botones de acción
        ButtonType buttonTypeGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeGuardar, buttonTypeCancelar);

        // Configurar el resultado del diálogo
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeGuardar) {
                // Devolver un par de nuevos valores para actualizar el consumible
                return new Pair<>(textFieldNombre.getText(), textFieldCantidad.getText());
            }
            return null; // Cancelar en caso de pulsar el botón de Cancelar
        });

        // Mostrar el diálogo y procesar el resultado
        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(nuevosValores -> {
            // Actualizar el consumible con los nuevos valores
            consumibleSeleccionado.setNombre(nuevosValores.getKey());
            consumibleSeleccionado.setCantidad(Integer.parseInt(nuevosValores.getValue()));
            consumibleSeleccionado.setUnidad(textFieldUnidad.getText());
            consumibleSeleccionado.setPrecio(Double.parseDouble(textFieldPrecio.getText()));
            consumibleSeleccionado.setObservaciones(textFieldObservaciones.getText());
            consumibleSeleccionado.setProveedor(textFieldProveedor.getText());


            // Actualizar el consumible en la base de datos
            actualizarConsumibleEnBD(consumibleSeleccionado);
            // cargar los datos de la base de datos
            cargarDatosDesdeBD();

        });
    }


    @FXML void pressAgregar(ActionEvent event) {
        // validar que los campos no esten vacios
        if (gridNombre.getText().isEmpty() || gridProveedor.getText().isEmpty() || gridPrecio.getText().isEmpty() || gridUnidad.getText().isEmpty() || gridObservaciones.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
            return; // salir del metodo si los campos estan vacios
        }

        // crear objeto de consumible con valores de textfields
        Consumible consumible = new Consumible(
                gridNombre.getText(),
                gridProveedor.getText(),
                Integer.parseInt(gridCantidad.getText()),
                gridUnidad.getText(),
                Double.parseDouble(gridPrecio.getText()),
                gridObservaciones.getText());
        try {
            // tomar los atributos del objeto y mandarlos a la base de datos
            Connection conn = null;
            try {
                conn = DatabaseConnector.getConnection();
                String query = "INSERT INTO consumibles (nombre, proveedor, cantidad, unidad, precio, observaciones) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, consumible.getNombre());
                stmt.setString(2, consumible.getProveedor());
                stmt.setInt(3, consumible.getCantidad());
                stmt.setString(4, consumible.getUnidad());
                stmt.setDouble(5, consumible.getPrecio());
                stmt.setString(6, consumible.getObservaciones());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Consumible registrado exitosamente");
                // cargar los datos de la base de datos
                cargarDatosDesdeBD();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocurrió un error al registrar el consumible: " + e.getMessage());
            } finally {
                // imprimir los datos registrados
                System.out.println(consumible);

                // limpiar los campos de los textfields
                gridNombre.setText("");
                gridProveedor.setText("");
                gridCantidad.setText("");
                gridUnidad.setText("");
                gridPrecio.setText("");
                gridObservaciones.setText("");
                // cerrar conexión con la base de datos
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            // manejar los errores
            e.printStackTrace();
        }
    }

    @FXML public void pressActualizar(ActionEvent event){
        cargarDatosDesdeBD();
    }
    @FXML public void pressEliminar(ActionEvent event) {
      Consumible consumibleseleccionado = tableView.getSelectionModel().getSelectedItem();

      // validar que el consumible seleccionado no este vacio
      if (consumibleseleccionado == null) {
          JOptionPane.showMessageDialog(null, "Por favor, seleccione un consumible");
          return;
      }
      // Confirmar la eliminacion del equipo
        int confirmation = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el consumible seleccionado?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            // Eliminar el consumible de la base de datos
            try(Connection conn = DatabaseConnector.getConnection()) {
                String query = "DELETE FROM consumibles WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, consumibleseleccionado.getIdTemporal());

                int filasAfectadas = stmt.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Consumible eliminado exitosamente");
                    // Cargar los datos de la base de datos
                    cargarDatosDesdeBD();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el consumible, Asegúrese de que el consumible exista");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error al eliminar el consumible: " + e.getMessage());
            }

            }
    }
private void cargarDatosDesdeBD() {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    // Limpiar los datos de la tabla antes de cargar
    listaConsumibles.clear();
    try {
        conn = DatabaseConnector.getConnection();
        stmt = conn.prepareStatement("SELECT * FROM consumibles");
        rs = stmt.executeQuery();

        while (rs.next()) {
            // recorremos la tabla de consumibles
            Consumible consumible = new Consumible();
            consumible.setIdTemporal(rs.getInt("id"));
            consumible.setNombre(rs.getString("nombre"));
            consumible.setProveedor(rs.getString("proveedor"));
            consumible.setCantidad(rs.getInt("cantidad"));
            consumible.setUnidad(rs.getString("unidad"));
            consumible.setPrecio(rs.getDouble("precio"));
            consumible.setObservaciones(rs.getString("observaciones"));

            listaConsumibles.add(consumible);
        }
            // agregar la lista de consumibles a la TableView
            tableView.setItems(listaConsumibles);

        }catch(SQLException e){
        System.out.println(e.getMessage());
        }finally{
            // cerrar conexiones
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
                System.out.println(e.getMessage());
            }
        }
    }

    public void actualizarConsumibleEnBD(Consumible consumible) {
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE consumibles SET nombre = ?,proveedor = ?,cantidad = ?,unidad = ?, precio = ?,observaciones = ? WHERE id = ?")) {
            stmt.setString(1, consumible.getNombre());
            stmt.setString(2, consumible.getProveedor());
            stmt.setInt(3, consumible.getCantidad());
            stmt.setString(4, consumible.getUnidad());
            stmt.setDouble(5, consumible.getPrecio());
            stmt.setString(6, consumible.getObservaciones());
            stmt.setInt(7, consumible.getIdTemporal());
            stmt.executeUpdate();
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Consumible actualizado exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar el consumible. Verifica el ID.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error al actualizar el consumible: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML private void pressExportar(ActionEvent event) {
        // Obtener la ventana principal (Stage) desde cualquier nodo en la escena
        Stage stage = (Stage) tableView.getScene().getWindow();

        // Configurar el FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));

        // Mostrar el diálogo de guardar
        File file = fileChooser.showSaveDialog(stage);

        // Verificar si el usuario ha seleccionado un archivo
        if (file != null) {
            try {
                // Crear un FileWriter para escribir en el archivo
                FileWriter writer = new FileWriter(file);

                // Escribir el encabezado del CSV (nombres de las columnas)
                for (TableColumn<?, ?> column : tableView.getColumns()) {
                    writer.write(column.getText() + ",");
                }
                writer.write("\n");

                // Escribir los datos de cada fila en el CSV
                for (Consumible consumible : listaConsumibles) {
                    writer.write(consumible.getNombre() + ",");
                    writer.write(consumible.getProveedor() + ",");
                    writer.write(String.valueOf(consumible.getCantidad()) + ",");
                    writer.write(consumible.getUnidad() + ",");
                    writer.write(String.valueOf(consumible.getPrecio()) + ",");
                    writer.write(consumible.getObservaciones() + "\n");
                }

                // Cerrar el FileWriter
                writer.close();

                // Mostrar mensaje de éxito
                JOptionPane.showMessageDialog(null, "Datos exportados exitosamente a: " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al exportar datos: " + e.getMessage());
            }
        }
    }


}

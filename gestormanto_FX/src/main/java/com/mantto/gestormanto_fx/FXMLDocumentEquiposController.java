package com.mantto.gestormanto_fx;

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

// importaciones de archivos
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;


public class FXMLDocumentEquiposController implements Initializable {
    //.......................::::: Elementos de la vista ::::::................................
    @FXML private ChoiceBox<String> choiceBox;
    @FXML private TextField gridNombre;
    @FXML private TextField gridModelo;
    @FXML private TextField gridMarca;
    @FXML private TextField gridLocalizacion;
    @FXML private TextField gridDescripcion;
    //Botones
    @FXML private Button buttonAgregar;
    @FXML private Button buttonActualizar;
    @FXML private Button buttonEditar;
    @FXML private Button buttonEliminar;
    @FXML private Button buttonExportar;
    @FXML private Button buttonPDF;
    @FXML private Stage primaryStage;
    // ids columnas
    @FXML private TableColumn<Equipo, Integer> idColumn;
    @FXML private TableColumn<Equipo, String> nombreColumn;
    @FXML private TableColumn<Equipo, String> modeloColumn;
    @FXML private TableColumn<Equipo, String> marcaColumn;
    @FXML private TableColumn<Equipo, String> estadoColumn;
    @FXML private TableColumn<Equipo, String> localizacionColumn;
    @FXML private TableColumn<Equipo, String> descripcionColumn;
    @FXML private TableView<Equipo> tableView;

    //coleccion de elementos equipos
    private ObservableList<Equipo> listaEquipos = FXCollections.observableArrayList();

    // metodos propios del controlador
    public void setStage (Stage stage){
        this.primaryStage = stage;
    }

    public void initialize (URL url, ResourceBundle resourceBundle){
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


    // ....................................::::: Metodos/Triggers ::::::.....................................

    @FXML private void pressPDF(ActionEvent event) {
        // Obtener la lista de equipos de tu TableView
        List<Equipo> listaEquipos = tableView.getItems();

        // Crear un documento PDF
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Crear el flujo de contenido para escribir en el PDF
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Establecer la posición inicial para el contenido
                contentStream.newLineAtOffset(20, page.getMediaBox().getHeight() - 20);

                // Escribir los datos de la tabla en el PDF
                for (Equipo equipo : listaEquipos) {
                    contentStream.showText(equipo.getNombre() + "\t" + equipo.getModelo() + "\t" + equipo.getMarca());
                    contentStream.newLineAtOffset(0, -20); // Avanzar a la siguiente línea
                }
            }

            // Guardar el PDF en un archivo
            File file = new File("equipos_reporte.pdf");
            document.save(file);

            // Abrir el PDF con el visor predeterminado
            java.awt.Desktop.getDesktop().open(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML private void pressEditar(ActionEvent event) {
        // Obtener el equipo seleccionado
        Equipo equipoSeleccionado = tableView.getSelectionModel().getSelectedItem();

        // Verificar si se ha seleccionado un equipo
        if (equipoSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un equipo para editar.");
            return;
        }

        // Crear un nuevo equipo con los datos actuales (para mostrar en la interfaz de edición)
        Equipo equipoEdicion = new Equipo(
                equipoSeleccionado.getNombre(),
                equipoSeleccionado.getModelo(),
                equipoSeleccionado.getMarca(),
                equipoSeleccionado.getEstado_equipo(),
                equipoSeleccionado.getLocalizacion(),
                equipoSeleccionado.getNota()
        );

        // Crear un diálogo de edición
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Editar Equipo");
        dialog.setHeaderText("Editando equipo con ID: " + equipoSeleccionado.getIdTemporal());

        // Crear campos de texto para la edición
        TextField textFieldNombre = new TextField(equipoEdicion.getNombre());
        TextField textFieldModelo = new TextField(equipoEdicion.getModelo());
        TextField textFieldMarca = new TextField(equipoEdicion.getMarca());
        TextField textFieldEstado = new TextField(equipoEdicion.getEstado_equipo());
        TextField textFieldLocalizacion = new TextField(equipoEdicion.getLocalizacion());
        TextField textFieldNota = new TextField(equipoEdicion.getNota());

        // Añadir campos al diálogo
        dialog.getDialogPane().setContent(new VBox(8,
                new Label("Nombre:"), textFieldNombre,
                new Label("Modelo:"), textFieldModelo,
                new Label("Marca:"), textFieldMarca,
                new Label("Estado:"), textFieldEstado,
                new Label("Localización:"), textFieldLocalizacion,
                new Label("Nota:"), textFieldNota
        ));

        // Botones de acción
        ButtonType buttonTypeGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeGuardar, buttonTypeCancelar);

        // Configurar el resultado del diálogo
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeGuardar) {
                // Devolver un par de nuevos valores para actualizar el equipo
                return new Pair<>(textFieldNombre.getText(), textFieldModelo.getText());
            }
            return null; // Cancelar en caso de pulsar el botón de Cancelar
        });

        // Mostrar el diálogo y procesar el resultado
        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(nuevosValores -> {
            // Actualizar el equipo con los nuevos valores
            equipoSeleccionado.setNombre(nuevosValores.getKey());
            equipoSeleccionado.setModelo(nuevosValores.getValue());
            equipoSeleccionado.setNota(textFieldNota.getText());
            equipoSeleccionado.setLocalizacion(textFieldLocalizacion.getText());
            equipoSeleccionado.setEstado_equipo(textFieldEstado.getText());
            equipoSeleccionado.setMarca(textFieldMarca.getText());


            // Luego, actualiza estos cambios en la base de datos
            actualizarEquipoEnBD(equipoSeleccionado);
            // Cargar los datos desde la base de datos
            cargarDatosDesdeBD();
        });
    }

    @FXML private void pressEliminar(ActionEvent event){
        // Obtener el equipo seleccionado
        Equipo equipoSeleccionado = tableView.getSelectionModel().getSelectedItem();

        // Verificar si se ha seleccionado un equipo
        if (equipoSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un equipo para editar.");
            return;
        }
        // Confirmar la eliminacion del equipo
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Deseas eliminar el equipo seleccionado.","Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            // Eliminar el equipo de la base de datos
            try(Connection conn = DatabaseConnector.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM equipos WHERE id = ?");
                stmt.setInt(1, equipoSeleccionado.getIdTemporal());

                int filasAfectadas = stmt.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Equipo eliminado correctamente.");
                    // Cargar los datos desde la base de datos
                    cargarDatosDesdeBD();
                    cargarDatosDesdeBD();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el equipo. Asegurate de que el equipo exista.");
                }
            }catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al eliminar el equipo: " + e.getMessage());
            }
        }
    }

    @FXML public void pressActualizar(ActionEvent event){
        cargarDatosDesdeBD();
    }
    @FXML private void pressAgregar(ActionEvent event) {
        // Validar que los campos no estén vacíos
        if (gridNombre.getText().isEmpty() || gridModelo.getText().isEmpty() || gridMarca.getText().isEmpty() || gridLocalizacion.getText().isEmpty() || gridDescripcion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos");
            return; // Salir del método si hay campos vacíos
        }

        // Crear objeto de equipo
        Equipo equipo = new Equipo(
                gridNombre.getText(),
                gridModelo.getText(),
                gridMarca.getText(),
                choiceBox.getValue().toString(),
                gridLocalizacion.getText(),
                gridDescripcion.getText());
        try {
            // Tomar los valores del equipo y mandarlos a la base de datos
            Connection conn = null;
            try {
                conn = DatabaseConnector.getConnection();
                String query = "INSERT INTO equipos (nombre, modelo, Marca, disponible, lugar, descripcion) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, equipo.getNombre());
                stmt.setString(2, equipo.getModelo());
                stmt.setString(3, equipo.getMarca());
                stmt.setString(4, equipo.getEstado_equipo());
                stmt.setString(5, equipo.getLocalizacion());
                stmt.setString(6, equipo.getNota());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Equipo registrado exitosamente");
                // Cargar los datos desde la base de datos
                cargarDatosDesdeBD();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocurrió un error al registrar el equipo: " + e.getMessage());
            } finally {
                // Cerrar conexión con la base de datos
                // Imprimir los datos registrados
                System.out.println("Los datos registrados son:");
                System.out.println("Nombre: " + equipo.getNombre() + "\n" + "Modelo: " + equipo.getModelo() + "\n" + "Marca: " + equipo.getMarca() + "\n" + "Estado: " + equipo.getEstado_equipo() + "\n" + "Localización: " + equipo.getLocalizacion() + "\n" + "Nota: " + equipo.getNota());

                // Limpiar los campos de los textfields
                gridNombre.setText("");
                gridModelo.setText("");
                gridMarca.setText("");
                gridLocalizacion.setText("");
                gridDescripcion.setText("");
                choiceBox.setValue("Activo");

                // Cerrar conexión
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            // Manejar errores
            e.printStackTrace();
        }
    }

    // Metodos para operar base de datos
    private void cargarDatosDesdeBD () {
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
    private void actualizarEquipoEnBD(Equipo equipo) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnector.getConnection();
            String query = "UPDATE equipos SET nombre=?, modelo=?, marca=?, disponible=?, lugar=?, descripcion=? WHERE id=?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, equipo.getNombre());
            stmt.setString(2, equipo.getModelo());
            stmt.setString(3, equipo.getMarca());
            stmt.setString(4, equipo.getEstado_equipo());
            stmt.setString(5, equipo.getLocalizacion());
            stmt.setString(6, equipo.getNota());
            stmt.setInt(7, equipo.getIdTemporal());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Equipo actualizado exitosamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error al actualizar el equipo: " + e.getMessage());
        } finally {
            // Cerrar conexión con la base de datos
            try {
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
                for (Equipo equipo : listaEquipos) {
                    writer.write(equipo.getNombre() + ",");
                    writer.write(equipo.getModelo() + ",");
                    writer.write(equipo.getMarca() + ",");
                    writer.write(equipo.getEstado_equipo() + ",");
                    writer.write(equipo.getLocalizacion() + ",");
                    writer.write(equipo.getNota() + "\n");
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



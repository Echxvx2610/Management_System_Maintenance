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
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
// importaciones popups JOptionPane
import javax.swing.*;
// importaicones librerias MySQL connector

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.ls.LSOutput;


public class FXMLDocumentPlanController implements Initializable {
    @FXML private ChoiceBox<String> choiceFrecc;
    @FXML private ChoiceBox<String> choiceRealizado;
    @FXML private ChoiceBox<Actividad> choiceAct;
    @FXML private TextField gridNombre;
    @FXML private TextField gridNotaPlan;
    @FXML private TextField gridNotaAct;
    @FXML private TextField gridParte;
    @FXML private TextField gridTiempo;
    @FXML private TableView<Actividad> tableViewAct;
    @FXML private TableView<Plan> tableViewPlan;
    @FXML private Button agregarPlan;
    @FXML private Button agregarAct;
    @FXML private Button eliminarAct;
    @FXML private Button eliminarPlan;
    @FXML private Button editarAct;
    @FXML private Button editarPlan;
    @FXML private TableColumn<Actividad, String> actividadColumn;
    @FXML private TableColumn<Plan, String> frecuenciaColumn;
    @FXML private TableColumn<Actividad, Integer> idActColumn;
    @FXML private TableColumn<Plan, Integer> idPlanColumn;
    @FXML private TableColumn<Plan, String> nombreColumn;
    @FXML private TableColumn<Plan, String> notaActColumn;
    @FXML private TableColumn<Plan, Timestamp> fechaActColumn;
    @FXML private TableColumn<Plan, Timestamp> fechaProxColumn;
    @FXML private TableColumn<Plan ,Integer> actIDColumn;
    @FXML private TableColumn<Plan, String> realizadoColumn;
    @FXML private TableColumn<Actividad, Integer> tiempoColumn;

    private  ObservableList<Plan> listaPlan = FXCollections.observableArrayList();
    private ObservableList<Actividad> listaAct = FXCollections.observableArrayList();
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

        ObservableList<String> opciones_frecc = FXCollections.observableArrayList("Diario", "Semanal","Mensual");
        choiceFrecc.setItems(opciones_frecc);
        choiceFrecc.setValue("N/A");
        ObservableList<String> opciones_realizado = FXCollections.observableArrayList("Realizado", "No Realizado");
        choiceRealizado.setItems(opciones_realizado);
        choiceRealizado.setValue("No Realizado");

        // cargar actividades en choiceBox actividades
        List<Actividad> actividades = cargarActividadesPlan();
        choiceAct.getItems().addAll(actividades);

        // Configurar las celdas de la TableView para mostrar las actividades
        idActColumn.setCellValueFactory(new PropertyValueFactory<>("idTemporal"));
        actividadColumn.setCellValueFactory(new PropertyValueFactory<>("parte"));
        tiempoColumn.setCellValueFactory(new PropertyValueFactory<>("tiempo"));
        notaActColumn.setCellValueFactory(new PropertyValueFactory<>("nota"));

        // Configurar las celdas de la TableView para mostrar los planes
        idPlanColumn.setCellValueFactory(new PropertyValueFactory<>("idTemporalPlan"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        frecuenciaColumn.setCellValueFactory(new PropertyValueFactory<>("frecuencia"));
        realizadoColumn.setCellValueFactory(new PropertyValueFactory<>("realizado"));
        if (fechaActColumn != null) {
            fechaActColumn.setCellValueFactory(new PropertyValueFactory<>("fechaActual"));
        }
        //fechaActColumn.setCellValueFactory(new PropertyValueFactory<>("fechaActual"));
        fechaProxColumn.setCellValueFactory(new PropertyValueFactory<>("fechaProximaMantenimiento"));
        if (actIDColumn != null) {
            actIDColumn.setCellValueFactory(new PropertyValueFactory<>("actividadId"));
        }

        // cargar los datos desde la base de datos
        cargarActividadesDesdeBD();
        cargarPlanes();
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
                // cargar los datos desde la base de datos
                cargarActividadesDesdeBD();
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
    // Método para cargar las actividades desde la base de datos
    private List<Actividad> cargarActividadesPlan() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Actividad> actividades = new ArrayList<>();

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

                actividades.add(act);
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

        return actividades;
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

    private void cargarActividadesDesdeBD() {
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

    @FXML private void pressAgregarPlan() {
        // Validar que los campos no estén vacíos
        if (gridNombre.getText().isEmpty() || choiceFrecc.getValue().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos del plan.");
            return;
        }

        // Obtener valores de los campos
        String nombrePlan = gridNombre.getText();
        String frecuenciaPlan = choiceFrecc.getValue();
        boolean realizadoPlan = choiceRealizado.getValue().equals("Realizado");

        // Obtener la actividad seleccionada en el ChoiceBox (suponiendo que se haya seleccionado una actividad)
        Actividad actividadSeleccionada = choiceAct.getValue();
        ArrayList<Actividad> actividades = new ArrayList<>();
        actividades.add(actividadSeleccionada);

        // Crear un objeto de tipo Plan con los datos ingresados
        Plan nuevoPlan = new Plan(nombrePlan, frecuenciaPlan, actividades, realizadoPlan);


        System.out.println(nuevoPlan);
        System.out.println(nuevoPlan.getFechaActualFormateada());
        System.out.println(nuevoPlan.getFechaProximaMantenimientoFormateada());
        ///*
        // Guardar el nuevo plan en la base de datos
        try {
            Connection conn = DatabaseConnector.getConnection();
            String query = "INSERT INTO planmantto (nombre, frecuencia, realizado, fecha_actual, fecha_proxima_mantenimiento, actividad_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, nuevoPlan.getNombre());
            pstmt.setString(2, nuevoPlan.getFrecuencia());
            pstmt.setBoolean(3, nuevoPlan.isRealizado());
            pstmt.setString(4, nuevoPlan.getFechaActualFormateada());
            pstmt.setString(5, nuevoPlan.getFechaProximaMantenimientoFormateada());
            pstmt.setInt(6, actividadSeleccionada.getIdTemporal()); // Agrega el id de la actividad asociada al plan
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Plan agregado exitosamente.");
            // cargar los datos desde la base de datos
            cargarPlanes();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar el plan.");
            e.printStackTrace();
        }
        //*/
    }


    @FXML void cargarPlanes() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        // Limpiar la lista antes de cargar los planes
        listaPlan.clear();

        try {
            conn = DatabaseConnector.getConnection();
            String query = "SELECT * FROM planmantto";
            PreparedStatement pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Plan plan = new Plan();
                plan.setIdTemporal(rs.getInt("id"));
                plan.setNombre(rs.getString("nombre"));
                plan.setFrecuencia(rs.getString("frecuencia"));
                plan.setRealizado(rs.getBoolean("realizado"));
                plan.setFechaActual(rs.getTimestamp("fecha_actual"));
                plan.setFechaProximaMantenimiento(rs.getTimestamp("fecha_proxima_mantenimiento"));

                // Obtener la actividad asociada al plan (si tienes la columna actividad_id en tu tabla)
                plan.setActividadId(rs.getInt("actividad_id"));
                
                listaPlan.add(plan);
            }

            // Cerrar conexiones con la BD
            rs.close();
            pstmt.close();
            conn.close();

            // Mostrar los planes en la tabla
            tableViewPlan.setItems(listaPlan);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los planes desde la base de datos.");
            e.printStackTrace();
        }
    }

    @FXML private void pressEditarAct(ActionEvent event) {
        // Obtener la actividad seleccionada
        Actividad actividadSeleccionada = tableViewAct.getSelectionModel().getSelectedItem();

        // Verificar si se ha seleccionado una actividad
        if (actividadSeleccionada == null) {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona una actividad para editar.");
            return;
        }

        // Crear una nueva actividad con los datos actuales (para mostrar en la interfaz de edición)
        Actividad actividadEdicion = new Actividad(
                actividadSeleccionada.getParte(),
                actividadSeleccionada.getTiempo(),
                actividadSeleccionada.getNota()
        );

        // Crear un diálogo de edición
        Dialog<Actividad> dialog = new Dialog<>();
        dialog.setTitle("Editar Actividad");
        dialog.setHeaderText("Editando actividad con ID: " + actividadSeleccionada.getIdTemporal());

        // Crear campos de texto para la edición
        TextField textFieldActividad = new TextField(actividadEdicion.getParte());
        TextField textFieldtiempo = new TextField(String.valueOf(actividadEdicion.getTiempo()));
        TextField textFieldNota = new TextField(actividadEdicion.getNota());

        // Añadir campos al diálogo
        dialog.getDialogPane().setContent(new VBox(8,
                new Label("Actividad:"), textFieldActividad,
                new Label("Tiempo:"), textFieldtiempo,
                new Label("Nota:"), textFieldNota
        ));

        // Botones de acción
        ButtonType buttonTypeGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeGuardar, buttonTypeCancelar);

        // Configurar el resultado del diálogo
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeGuardar) {
                // Devolver la actividad con los nuevos valores
                return new Actividad(
                        textFieldActividad.getText(),
                        Integer.parseInt(textFieldtiempo.getText()),
                        textFieldNota.getText()
                );
            }
            return null; // Cancelar en caso de pulsar el botón de Cancelar
        });

        // Mostrar el diálogo y procesar el resultado
        Optional<Actividad> result = dialog.showAndWait();

        result.ifPresent(nuevaActividad -> {
            // Actualizar la actividad con los nuevos valores
            actividadSeleccionada.setParte(nuevaActividad.getParte());
            actividadSeleccionada.setTiempo(nuevaActividad.getTiempo());
            actividadSeleccionada.setNota(nuevaActividad.getNota());

            // Luego, actualiza estos cambios en la base de datos
            actualizarActividadEnBD(actividadSeleccionada);
            // cargar los datos desde la base de datos
            cargarActividadesDesdeBD();
        });
    }

    public void actualizarActividadEnBD(Actividad actividad) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnector.getConnection();
            String query = "UPDATE actividades SET parte = ?,tiempo = ? ,nota = ?WHERE id = ?";
            stmt = conn.prepareStatement(query);

            // Establecer los parámetros de la consulta con los nuevos valores
            stmt.setString(1, actividad.getParte());
            stmt.setInt(2, actividad.getTiempo());
            stmt.setString(3,actividad.getNota());
            // Establece otros parámetros según los atributos de tu clase Actividad
            stmt.setInt(4, actividad.getIdTemporal()); // Supongamos que el ID es clave primaria

            // Ejecutar la actualización
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Actividad actualizada correctamente en la base de datos.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar la actividad en la base de datos.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la actividad en la base de datos.");
            e.printStackTrace();
        } finally {
            // Cerrar conexiones
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

    @FXML
    void pressEditarPlan(ActionEvent event) {
        Plan planSeleccionado = tableViewPlan.getSelectionModel().getSelectedItem();

        if (planSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un plan para editar.");
            return;
        }

        // Crear una nueva instancia de Plan con los datos actuales para mostrar en la interfaz de edición
        // Crear una nueva instancia de Plan con los datos actuales para mostrar en la interfaz de edición
        Plan planEdicion = new Plan(
                planSeleccionado.getNombre(),
                planSeleccionado.getFrecuencia(),
                planSeleccionado.getActividades(),
                planSeleccionado.isRealizado()
        );


        // Crear un diálogo de edición
        Dialog<Plan> dialog = new Dialog<>();
        dialog.setTitle("Editar Plan");
        dialog.setHeaderText("Editando plan con ID: " + planSeleccionado.getIdTemporalPlan());

        // Crear campos de texto para la edición
        TextField textFieldNombre = new TextField(planEdicion.getNombre());
        TextField textFieldFrecuencia = new TextField(planEdicion.getFrecuencia());
        CheckBox checkBoxRealizado = new CheckBox();
        checkBoxRealizado.setSelected(planEdicion.isRealizado());

        // Agregar campos al diálogo
        dialog.getDialogPane().setContent(new VBox(8,
                new Label("Nombre:"), textFieldNombre,
                new Label("Frecuencia:"), textFieldFrecuencia,
                new Label("Realizado:"), checkBoxRealizado
        ));

        // Botones de acción
        ButtonType buttonTypeGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeGuardar, buttonTypeCancelar);

        // Configurar el resultado del diálogo
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeGuardar) {
                // Devolver un nuevo objeto Plan con los nuevos valores
                return new Plan(
                        textFieldNombre.getText(),
                        textFieldFrecuencia.getText(),
                        planEdicion.getActividades(),
                        checkBoxRealizado.isSelected()
                );
            }
            return null; // Cancelar en caso de pulsar el botón de Cancelar
        });

        // Mostrar el diálogo y procesar el resultado
        Optional<Plan> result = dialog.showAndWait();

        result.ifPresent(nuevoPlan -> {
            // Actualizar el plan con los nuevos valores
            planSeleccionado.setNombre(nuevoPlan.getNombre());
            planSeleccionado.setFrecuencia(nuevoPlan.getFrecuencia());
            planSeleccionado.setRealizado(nuevoPlan.isRealizado());

            // Luego, actualiza estos cambios en la base de datos
            actualizarPlanEnBD(planSeleccionado);
            // cargar los datos desde la base de datos
            cargarActividadesDesdeBD();
        });
    }

    public void actualizarPlanEnBD(Plan plan) {
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE planmantto SET nombre = ?, frecuencia = ?, realizado = ? WHERE id = ?")) {

            stmt.setString(1, plan.getNombre());
            stmt.setString(2, plan.getFrecuencia());
            stmt.setBoolean(3, plan.isRealizado());
            stmt.setInt(4, plan.getIdTemporalPlan());

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Plan actualizado correctamente en la base de datos.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar el plan en la base de datos.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el plan en la base de datos.");
            e.printStackTrace();
        }
    }
    @FXML void pressEliminarAct(ActionEvent event) {
        // Obtener id de la actividad seleccionada
        Actividad actividadSeleccionada = tableViewAct.getSelectionModel().getSelectedItem();
        // verificar si se ha seleccionado una actividad
        if (actividadSeleccionada == null) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una actividad.");
            return;
        }
        // confirmar si desea eliminar la actividad
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea eliminar la actividad seleccionada?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            // Eliminar la actividad de la base de datos
            try {
                Connection conn = DatabaseConnector.getConnection();
                String query = "DELETE FROM actividades WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, actividadSeleccionada.getIdTemporal());
                int filasAfectadas = pstmt.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Actividad eliminada exitosamente.");
                    // cargar los datos desde la base de datos
                    cargarActividadesDesdeBD();
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró la actividad en la base de datos.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"Error al eliminar la actividad." + e.getMessage());
            }
    }
    }
    @FXML void pressEliminarPlan(ActionEvent event) {
        // Obtener id del plan seleccionado
        Plan planSeleccionado = tableViewPlan.getSelectionModel().getSelectedItem();
        // verificar si se ha seleccionado un plan
        if (planSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un Plan.");
            return;
        }
        // confirmar si desea eliminar el plan
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el plan seleccionada?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            // Eliminar el plan de la base de datos
            try {
                Connection conn = DatabaseConnector.getConnection();
                String query = "DELETE FROM planmantto WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, planSeleccionado.getIdTemporalPlan());
                int filasAfectadas = pstmt.executeUpdate();

                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Plan eliminado exitosamente.");
                    // cargar los datos desde la base de datos
                    cargarPlanes();
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el Plan en la base de datos.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"Error al eliminar el Plan." + e.getMessage());
            }
        }
    }

    private Actividad obtenerActividadPorId(int actividadId) {
        Actividad actividad = null;

        try {
            Connection conn = DatabaseConnector.getConnection();
            String query = "SELECT * FROM actividades WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, actividadId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                actividad = new Actividad();
                actividad.setIdTemporal(rs.getInt("id"));
                actividad.setParte(rs.getString("parte"));
                actividad.setTiempo(rs.getInt("tiempo"));
                actividad.setNota(rs.getString("nota"));
                // Otros campos que puedas tener en la tabla

                // Puedes ajustar el constructor de Actividad según la estructura real de tu tabla
            }

            // Cerrar recursos
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener la actividad desde la base de datos.");
            e.printStackTrace();
        }

        return actividad;
    }
}


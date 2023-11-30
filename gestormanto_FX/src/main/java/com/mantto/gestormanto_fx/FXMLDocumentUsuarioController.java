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
import java.util.Optional;
import java.util.ResourceBundle;

// importaicones librerias MySQL connector
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class FXMLDocumentUsuarioController implements Initializable {
    @FXML private Button buttonAgregar;
    @FXML private Button buttonEditar;
    @FXML private Button buttonEliminar;
    @FXML private Button buttonActualizar;
    @FXML private TextField gridApellido;
    @FXML private TextField gridContraseña;
    @FXML private TextField gridCorreo;
    @FXML private TextField gridNombre;
    @FXML private TextField gridUsuario;
    @FXML private TableView<Usuario> tableView;
    @FXML private TableColumn<Usuario, Integer> idColumn;
    @FXML private TableColumn<Usuario, String> apellidoColumn;
    @FXML private TableColumn<Usuario, String> contraseñaColumn;
    @FXML private TableColumn<Usuario, String> correoColumn;
    @FXML private TableColumn<Usuario, String> nombreColumn;
    @FXML private TableColumn<Usuario, String> usuarioColumn;



    private ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    // metodos propios del controlador
    private Stage primaryStage;

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (primaryStage != null) {
            primaryStage.setWidth(700); // Establecer el ancho deseado
            primaryStage.setHeight(500); // Establecer la altura deseada
            primaryStage.setResizable(false); // Opcional: Para hacerla no redimensionable
        }

        // configurar las celdas de la tableView para mostrar los datos
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idTemporal"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        apellidoColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        correoColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        usuarioColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        contraseñaColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        // cargar los datos de la base de datos
        cargarDatosDesdeBD();
    }
    // metodos para controlar escena
    @FXML void pressAgregar(ActionEvent event) {
        // validar que los campos no esten vacios
        if (gridApellido.getText().isEmpty() || gridContraseña.getText().isEmpty() || gridCorreo.getText().isEmpty() || gridNombre.getText().isEmpty() || gridUsuario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, rellene todos los campos.");
            return;
        }

        // crea un nuevo usuario con los valores de los textFields
        Usuario usuario = new Usuario(
                gridNombre.getText(),
                gridApellido.getText(),
                gridCorreo.getText(),
                gridUsuario.getText(),
                gridContraseña.getText(),
                gridContraseña.getText());

        try {
            // tomar los atributos del objetos y mandarlos a la base de datos
            Connection conn = null;
            try {
                conn = DatabaseConnector.getConnection();
                String query = "INSERT INTO usuarios (nombre, apellido, correo, usuario, contraseña) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, usuario.getName());
                stmt.setString(2, usuario.getLastname());
                stmt.setString(3, usuario.getEmail());
                stmt.setString(4, usuario.getUsername());
                stmt.setString(5, usuario.getPassword());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error al registrar el usuario: " + e.getMessage());
            } finally {
                // limpiar los campos de los textfields
                gridNombre.setText("");
                gridApellido.setText("");
                gridCorreo.setText("");
                gridUsuario.setText("");
                gridContraseña.setText("");

                // cargar los datos de la base de datos
                cargarDatosDesdeBD();

                // cerrar conexion con la base de datos
                if (conn != null) {
                    conn.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML void pressEditar(ActionEvent event) {
        // Obtener el usuario seleccionado en la tabla
        Usuario usuarioSeleccionado = tableView.getSelectionModel().getSelectedItem();

        // Verificar si se ha seleccionado un usuario
        if (usuarioSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un usuario.");
            return;
        }

        // crear un nuevo usuario con los valores de los textFields
        Usuario usuarioEdicion = new Usuario(
                usuarioSeleccionado.getName(),
                usuarioSeleccionado.getLastname(),
                usuarioSeleccionado.getEmail(),
                usuarioSeleccionado.getUsername(),
                usuarioSeleccionado.getPassword(),
                usuarioSeleccionado.getPassword()
        );
        // crear un dialogo de edicion
        Dialog<Pair<String,String>> dialog = new Dialog<>();
        dialog.setTitle("Editar usuario");
        dialog.setHeaderText("Editando usuario con ID: " + usuarioSeleccionado.getIdTemporal());

        // Crear el contenido de la ventana
        TextField textFieldName = new TextField(usuarioEdicion.getName());
        TextField textFieldLastname = new TextField(usuarioEdicion.getLastname());
        TextField textFieldEmail = new TextField(usuarioEdicion.getEmail());
        TextField textFieldUsername = new TextField(usuarioEdicion.getUsername());
        TextField textFieldPassword = new TextField(usuarioSeleccionado.getPassword());

        // agregar los campos al dialogo
        dialog.getDialogPane().setContent(new VBox(5,
                new Label("Nombre"),textFieldName,
                new Label("Apellido"),textFieldLastname,
                new Label("Correo"),textFieldEmail,
                new Label("Usuario"),textFieldUsername,
                new Label("Contraseña"),textFieldPassword
        ));

        // Botones de accion
        ButtonType buttonTypeGuardar = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeGuardar, buttonTypeCancelar);

        // Configurar el resultado del dialogo
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeGuardar) {
                return new Pair<>(textFieldName.getText(), textFieldLastname.getText());
            }
            return null;
        });

        // Mostrar el dialogo y obtener el resultado
        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(nuevosValores -> {
            // Actualizar el usuario con los nuevos valores
            usuarioSeleccionado.setName(textFieldName.getText());
            usuarioSeleccionado.setLastname(textFieldLastname.getText());
            usuarioSeleccionado.setEmail(textFieldEmail.getText());
            usuarioSeleccionado.setUsername(textFieldUsername.getText());
            usuarioSeleccionado.setPassword(textFieldPassword.getText());

            // actualizar el usuario en la base de datos
            actualizarUsuarioEnBD(usuarioSeleccionado);
        });
    }

    @FXML void pressEliminar(ActionEvent event) {
        // Obtener el usuario seleccionado en la tabla
        Usuario usuarioSeleccionado = tableView.getSelectionModel().getSelectedItem();

        // Verificar si se ha seleccionado un usuario
        if (usuarioSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un usuario.");
            return;
        }
        // Confirmar la eliminacion del usuario
        int confirmacion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas eliminar este usuario?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            // Eliminar el usuario en la base de datos
            try(Connection conn = DatabaseConnector.getConnection();
                    PreparedStatement stmt = conn.prepareStatement("DELETE FROM usuarios WHERE id = ?")) {
                stmt.setInt(1, usuarioSeleccionado.getIdTemporal());

                int filasAfectadas = stmt.executeUpdate();
                if (filasAfectadas > 0) {
                    JOptionPane.showMessageDialog(null, "Usuario eliminado exitosamente.");
                    cargarDatosDesdeBD();
                }else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el usuario.");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error al eliminar el usuario: " + e.getMessage());
            }
        }
    }
    @FXML void pressActualizar(ActionEvent event) {
        cargarDatosDesdeBD();
    }

    public void cargarDatosDesdeBD() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        listaUsuarios.clear();

        try {
            conn = DatabaseConnector.getConnection();
            stmt = conn.prepareStatement("SELECT * FROM usuarios");
            rs = stmt.executeQuery();

            while (rs.next()) {
                // recorremos la tabla de usuarios
                Usuario usuario = new Usuario();
                usuario.setIdTemporal(rs.getInt("id"));
                usuario.setName(rs.getString("nombre"));
                usuario.setLastname(rs.getString("apellido"));
                usuario.setEmail(rs.getString("correo"));
                usuario.setUsername(rs.getString("usuario"));
                usuario.setPassword(rs.getString("contraseña"));

                listaUsuarios.add(usuario);
            }

            // agregar la lista de usuarios a la TableView
            tableView.setItems(listaUsuarios);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

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

    public void actualizarUsuarioEnBD(Usuario usuario) {
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE usuarios SET nombre = ?, apellido = ?, correo = ?, usuario = ?, contraseña = ? WHERE id = ?")) {
            stmt.setString(1, usuario.getName());
            stmt.setString(2, usuario.getLastname());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getUsername());
            stmt.setString(5, usuario.getPassword());
            stmt.setInt(6, usuario.getIdTemporal());
            stmt.executeUpdate();

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Usuario actualizado exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar el usuario. Verifica el ID.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrio un error al actualizar el usuario: " + e.getMessage());
        }
    }
}

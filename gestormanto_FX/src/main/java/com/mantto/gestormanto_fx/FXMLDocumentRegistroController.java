package com.mantto.gestormanto_fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class FXMLDocumentRegistroController implements Initializable {
    @FXML
    private Stage primaryStage;
    @FXML
    private TextField emailField;

    @FXML
    private TextField lastnameField;

    @FXML
    private Button limpiarButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField userField;

    @FXML
    private PasswordField password1;

    @FXML
    private PasswordField password2;

    @FXML
    private Button registroButton;

    @FXML
    private Button loginButton;

    @FXML
    void pressClear(ActionEvent event) {
        // limpiar todos campos textfield y password field
        emailField.clear();
        lastnameField.clear();
        nameField.clear();
        userField.clear();
        password1.clear();
        password2.clear();
    }

    @FXML
    void pressSing(ActionEvent event) {
        // tomamos los valores de los textfields y password field y los guardamos en la clase usuario
        String email = emailField.getText();
        String lastname = lastnameField.getText();
        String name = nameField.getText();
        String username = userField.getText();
        String password = password1.getText();
        String confirmPassword = password2.getText();
        //validamos que el password1 y password2 sean iguales para poder continuar con registro
        try {
            // validar que el usuario no exista en la bd
            if (userExists(username)) {
                JOptionPane.showMessageDialog(null, "El usuario ya existe");
                return; // Salir de la función sin continuar con el registro
            }
            if(lastnameExists(lastname)){
                JOptionPane.showMessageDialog(null, "El apellido ya existe");
                return; // Salir de la función sin continuar con el registro
            }
            // validar que el correo no exista en la bd
            if (emailExists(email)) {
                JOptionPane.showMessageDialog(null, "El correo ya está registrado");
                return; // Salir de la función sin continuar con el registro
            }
            // validar que los dos password sean iguales
            if (!password1.getText().equals(password2.getText())) {
                // Mostrar mensaje de error
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden!");
                return; // Salir de la función sin continuar con el registro
            }
            System.out.println("Email: " + email + "\nLastname: " + lastname + "\nName: " + name + "\nUsername: " + username + "\nPassword: " + password + "\nConfirm Password: " + confirmPassword);
            // Tomamos los valores y los guardamos en la base de datos, tabla usuarios
            Connection conn = null;
            try {
                conn = DatabaseConnector.getConnection();
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO usuarios (nombre, apellido, correo,usuario,contraseña) VALUES (?, ?, ?, ?,?)");
                stmt.setString(1, name);
                stmt.setString(2, lastname);
                stmt.setString(3, email);
                stmt.setString(4, username);
                stmt.setString(5, password);
                stmt.executeUpdate();
                //conn.commit()
                JOptionPane.showMessageDialog(null, "Usuario registrado!");
            } catch (SQLException e) {
                e.printStackTrace();
                // Manejar errores de la base de datos
            } finally {
                //cierra conexión con base de datos
                conn.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // Método para verificar si el usuario ya existe en la base de datos
    private boolean userExists(String username) throws SQLException {
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM usuarios WHERE nombre = ?")) {
            stmt.setString(1, nameField.getText());
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
    private boolean lastnameExists(String lastname) throws SQLException {
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM usuarios WHERE apellido = ?")) {
            stmt.setString(1, lastnameField.getText());
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    // Método para verificar si el correo ya está registrado en la base de datos
    private boolean emailExists(String email) throws SQLException {
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM usuarios WHERE correo = ?")) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    @FXML
    void pressLogin(ActionEvent event) {
        // Cambiamos de vista a FXMLDocumentLogin.fxml
        try {
            // Cargar el nuevo archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocumentLogin.fxml"));
            Parent root = loader.load();

            // Obtener el Stage principal desde la aplicación
            Stage primaryStage = (Stage) registroButton.getScene().getWindow(); // obtener la ventana actual

            // Configurar el tamaño de la ventana principal
            if (primaryStage != null) {
                primaryStage.setWidth(700); // Establecer el ancho deseado
                primaryStage.setHeight(500); // Establecer la altura deseada
                primaryStage.setResizable(false); // Opcional: Para hacerla no redimensionable
            }

            // Reemplazar el contenido de la ventana actual con el nuevo FXML
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException e) {
            // Manejar errores en la carga del nuevo FXML
            e.printStackTrace();
        }
    }



    public void initialize(URL location, ResourceBundle resources) {
        if (primaryStage != null) {
        primaryStage.setWidth(700); // Establecer el ancho deseado
        primaryStage.setHeight(500); // Establecer la altura deseada
        primaryStage.setResizable(false); // Opcional: Para hacerla no redimensionable
         }
    }

}

package com.mantto.gestormanto_fx;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class FXMLDocumentLoginController implements Initializable {
    @FXML private Stage primaryStage;
    @FXML private Hyperlink hyperHelp;
    @FXML private Button signButton;
    @FXML private Button loginButton;
    @FXML private CheckBox checkPassword;
    @FXML private PasswordField passwordField;
    @FXML private TextField userField;

    @FXML void pressLogin(ActionEvent event) {
        String username = userField.getText();
        String password = passwordField.getText();

        Connection conn = null;
        try {
            // Verificar los datos de inicio de sesión
            conn = DatabaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM usuarios WHERE usuario = ? AND contraseña =?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Usuario y contraseña válidos
                // Continúa a la ventana principal
                JOptionPane.showMessageDialog(null, "Login successful");
                try {
                    // Cargar el nuevo archivo FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocumentMainv3.fxml"));
                    Parent root = loader.load();

                    // Obtener el Stage principal desde la aplicación
                    Stage primaryStage = (Stage) loginButton.getScene().getWindow(); // obtener la ventana actual

                    // Configurar el tamaño de la ventana principal
                    if (primaryStage != null) {
                        primaryStage.setWidth(1200); // Establecer el ancho deseado
                        primaryStage.setHeight(600); // Establecer la altura deseada
                        primaryStage.setResizable(false); // Opcional: Para hacerla no redimensionable
                    }
                    // Reemplazar el contenido de la ventana actual con el nuevo FXML
                    Scene scene = new Scene(root);
                    primaryStage.setScene(scene);
                    // Ajustar posicion en pantalla de la ventana principal
                    primaryStage.centerOnScreen();
                } catch (IOException e) {
                    // Manejar errores en la carga del nuevo FXML
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Login failed");
                userField.setText("");
                // limpiar passwordfield y dejar placeholder
                passwordField.clear();
                passwordField.setPromptText("Password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar errores de la base de datos
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Manejar errores al cerrar la conexión
            }
        }
    }

    @FXML
    void pressSignin(ActionEvent event) {
        // Cambiamos de vista a Registro.fxml
        try {
            // Cargar el nuevo archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocumentRegistro.fxml"));
            Parent root = loader.load();

            // Obtener el Stage principal desde la aplicación
            Stage primaryStage = (Stage) loginButton.getScene().getWindow(); // obtener la ventana actual

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


    @FXML
    void checkBoxPass(ActionEvent event) {
        if (checkPassword.isSelected()) {
            passwordField.setPromptText(passwordField.getText());
            passwordField.setText("");
        } else {
            passwordField.setText(passwordField.getPromptText());
            passwordField.setPromptText("");
        }
    }

    @FXML
    void pressHelp(ActionEvent event) {
        // To do
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configurar el tamaño de la ventana principal
        if (primaryStage != null) {
            primaryStage.setWidth(700); // Establecer el ancho deseado
            primaryStage.setHeight(500); // Establecer la altura deseada
            primaryStage.setResizable(false); // Opcional: Para hacerla no redimensionable
        }
    }
}

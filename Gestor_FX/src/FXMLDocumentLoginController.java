import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;

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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class FXMLDocumentLoginController implements Initializable {
    @FXML
    private Stage primaryStage;

    @FXML
    private Hyperlink hiperHelp;

    @FXML
    private Button loginButton;

    @FXML
    private CheckBox checkPassword;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userField;

    @FXML
    void pressLogin(ActionEvent event) {
        // permitir el acceso si usuario y password son iguales a admin
        String username = userField.getText();
        String password = passwordField.getText();
        if (username.equals("admin") && password.equals("admin")) {
            JOptionPane.showMessageDialog(null, "Login successful");
            try {
                // Cargar el nuevo archivo FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocumentMain.fxml"));
                Parent root = loader.load();

                Stage primaryStage = (Stage) loginButton.getScene().getWindow(); // obtener la ventana actual

                // configurar el tamaño de la ventana principal
                if (primaryStage != null) {
                    primaryStage.setWidth(800); // Establecer el ancho deseado
                    primaryStage.setHeight(450); // Establecer la altura deseada
                    primaryStage.setResizable(false); // Opcional: Para hacerla no redimensionable
                }

                // Reemplazar el contenido de la ventana actual con el nuevo FXML
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
            } catch (IOException e) {
                // Manejar errores en la carga del nuevo FXML
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Login failed");
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

    @FXML
    void keyEnterPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            // Cuando se presiona "Enter" en passwordField, ejecutar pressLogin
            pressLogin(new ActionEvent(passwordField, null));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (primaryStage != null) {
            primaryStage.setWidth(600); // Establecer el ancho deseado
            primaryStage.setHeight(400); // Establecer la altura deseada
            primaryStage.setResizable(false); // Opcional: Para hacerla no redimensionable
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}

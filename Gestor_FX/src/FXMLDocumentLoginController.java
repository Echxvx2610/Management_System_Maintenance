import java.io.IOException;
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
        String username = userField.getText();
        String password = passwordField.getText();
        if (username.equals("admin") && password.equals("admin")) {
            JOptionPane.showMessageDialog(null, "Login successful!");
            try {

                // Cargar el nuevo archivo FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocumentMain.fxml"));
                Parent root = loader.load();

                // Obtener el controlador asociado al nuevo archivo FXML (si es necesario)
                // FXMLDocumentMainController controller = loader.getController();

                // Obtener la ventana actual y reemplazar su contenido con el nuevo FXML
                Scene scene = loginButton.getScene();
                scene.setRoot(root);
            } catch (IOException e) {
                // Manejar errores en la carga del nuevo FXML
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Login failed!");
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

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        // TODO
    }
}

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FXMLDocumentSinginController implements Initializable {

    @FXML
    private TextField apellidoField;

    @FXML
    private PasswordField contrasenaField1;

    @FXML
    private PasswordField contrasenaField2;

    @FXML
    private TextField correoField;

    @FXML
    private Button limpiarButton;

    @FXML
    private TextField nombreField;

    @FXML
    private Button registrarButoon;

    @FXML
    private TextField usuarioField;

    @FXML
    void pressLimpiar(ActionEvent event) {

    }

    @FXML
    void pressRegistrar(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // if (primaryStage != null) {
        // primaryStage.setWidth(600); // Establecer el ancho deseado
        // primaryStage.setHeight(400); // Establecer la altura deseada
        // primaryStage.setResizable(false); // Opcional: Para hacerla no
        // redimensionable
        // }
    }

}

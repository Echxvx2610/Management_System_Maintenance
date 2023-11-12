import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import javafx.stage.StageStyle;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("FXMLDocumentLogin.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            // primaryStage.setResizable(false);
            // primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setHeight(450);
            primaryStage.setWidth(900);
            primaryStage.setResizable(false); // Opcional
            primaryStage.show();
        } catch (Exception e) {
        }
    }
}

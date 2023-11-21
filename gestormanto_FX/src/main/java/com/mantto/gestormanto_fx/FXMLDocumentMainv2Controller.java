package com.mantto.gestormanto_fx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLDocumentMainv2Controller implements Initializable {

    @FXML
    private Stage primaryStage;
    @FXML
    private AnchorPane ap;

    @FXML
    private BorderPane bp;

    @FXML
    void home(MouseEvent event) {
        bp.setCenter(ap);
    }

    @FXML
    void page1(MouseEvent event) {
        //loaderPage("FXMLPage1");
        loaderPage("FXMLDocumentLogin");
    }

    @FXML
    void page2(MouseEvent event) {
        loaderPage("FXMLPage2");
    }

    @FXML
    void page3(MouseEvent event) {
        loaderPage("FXMLPage3");
    }

    private void loaderPage(String page) {
        // cargar los fxml page
        Parent root = null;
        try{
            root = FXMLLoader.load(getClass().getResource(page+".fxml"));
        }catch (Exception e){
            e.printStackTrace();
        }
        bp.setCenter(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (primaryStage != null) {
            primaryStage.setWidth(700); // Establecer el ancho deseado
            primaryStage.setHeight(500); // Establecer la altura deseada
            primaryStage.setResizable(false); // Opcional: Para hacerla no redimensionable
        }
    }
}

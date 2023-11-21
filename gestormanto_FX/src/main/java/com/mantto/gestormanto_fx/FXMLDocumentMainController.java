package com.mantto.gestormanto_fx;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.util.Duration;

public class FXMLDocumentMainController implements Initializable {
    @FXML
    private Stage primaryStage;

    @FXML
    private Pane paneLoader;
    @FXML
    private ImageView menu;

    @FXML
    private ImageView exit;

    @FXML
    private AnchorPane pane1, pane2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (primaryStage != null) {
            primaryStage.setHeight(600);
            primaryStage.setWidth(900);
            primaryStage.setResizable(false); // Opcional
        }

        pane1.setVisible(false);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), pane2);
        translateTransition.setByX(-600);
        translateTransition.play();

        menu.setOnMouseClicked(event -> {
            pane1.setVisible(true);

            FadeTransition fadeTransition2 = new FadeTransition(Duration.seconds(1), pane1);
            fadeTransition2.setFromValue(0);
            fadeTransition2.setToValue(0.15);
            fadeTransition2.play();

            TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(1), pane2);
            translateTransition2.setByX(+600);
            translateTransition2.play();
        });

        pane1.setOnMouseClicked(event -> {
            FadeTransition fadeTransition2 = new FadeTransition(Duration.seconds(1), pane1);
            fadeTransition2.setFromValue(0.15);
            fadeTransition2.setToValue(0);
            fadeTransition2.play();

            fadeTransition2.setOnFinished(event2 -> {
                pane1.setVisible(false);
            });

            TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(1), pane2);
            translateTransition2.setByX(-600);
            translateTransition2.play();
        });

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}

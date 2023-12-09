package com.mantto.gestormanto_fx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.PrimitiveIterator;
import java.util.ResourceBundle;

public class FXMLDocumentDashboardController implements Initializable {

    @FXML private BarChart<?, ?> chartXY;
    @FXML private PieChart pastelChart;
    @FXML private Stage primaryStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (primaryStage != null) {
            primaryStage.setWidth(1000); // Establecer el ancho deseado
            primaryStage.setHeight(500); // Establecer la altura deseada
            primaryStage.setResizable(false); // Opcional: Para hacerla no redimensionable
        }
        // Crear los datos para la PieChart
        PieChart.Data completado = new PieChart.Data("Mantenimientos Realizados", 65);
        PieChart.Data no_completado = new PieChart.Data("Mantenimientos No Realizados",35);
        // Configurar la PieChart
        pastelChart.getData().addAll(completado,no_completado);
        pastelChart.setLegendVisible(true); // Opcional: Mostrar la leyenda
        pastelChart.setLabelsVisible(true); // Opcional: Mostrar etiquetas en los sectores
        pastelChart.setClockwise(false); // Opcional: Sentido contrario a las agujas del reloj
        pastelChart.setStartAngle(90); // Opcional: Ángulo de inicio (90 grados es arriba)
        //pastelChart.setTitle("Título de la PieChart"); // Opcional: Establecer el título
        pastelChart.setPrefSize(300, 300); // Opcional: Establecer el tamaño preferido

        // Crear las series de datos para barChart
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data("Categoría 1", 10));
        series.getData().add(new XYChart.Data("Categoría 2", 20));
        series.getData().add(new XYChart.Data("Categoría 3", 30));

        // Configurar el eje X sin ordenación automática
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Categorías");
        xAxis.setAnimated(false); // Opcional: Desactivar la animación del eje x

        // Configurar el eje Y
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Valores");

        // Configurar la BarChart
        chartXY.getData().addAll(series);
        chartXY.setLegendVisible(false);
        chartXY.setAnimated(false);
        chartXY.setBarGap(3);
        chartXY.setCategoryGap(20);
        chartXY.setPrefSize(400, 200);
        //chartXY.setTitle("Título de la BarChart");
        //chartXY.setCreateSymbols(false);
        //chartXY.setCategoryAxis(xAxis); // Establecer el eje x personalizado
    }

}

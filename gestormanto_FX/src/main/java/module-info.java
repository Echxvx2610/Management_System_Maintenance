module com.mantto.gestormanto_fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;


    opens com.mantto.gestormanto_fx to javafx.fxml;
    exports com.mantto.gestormanto_fx;
}
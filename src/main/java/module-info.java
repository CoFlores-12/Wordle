module unah.proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;


    opens unah.proyectofinal to javafx.fxml;
    exports unah.proyectofinal;
    exports unah.proyectofinal.otherClass;
    opens unah.proyectofinal.otherClass to javafx.fxml;
}
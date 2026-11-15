module edu.fiuba.algo3 {
    requires javafx.controls;
    requires json.simple;
    exports edu.fiuba.algo3;
    exports edu.fiuba.algo3.vistas;

    exports edu.fiuba.algo3.modelo;
    opens edu.fiuba.algo3.modelo to org.mockito;

    exports edu.fiuba.algo3.modelo.ubicacion;
    opens edu.fiuba.algo3.modelo.ubicacion to org.mockito;

    exports edu.fiuba.algo3.modelo.unidad;
    opens edu.fiuba.algo3.modelo.unidad to org.mockito;

    exports edu.fiuba.algo3.modelo.terreno;
    opens edu.fiuba.algo3.modelo.terreno to org.mockito;

    exports edu.fiuba.algo3.modelo.arma;
    opens edu.fiuba.algo3.modelo.arma to org.mockito;
}
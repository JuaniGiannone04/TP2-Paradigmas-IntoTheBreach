package edu.fiuba.algo3.modelo.arma;

import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;

public class Canion implements Arma {
    private static final int DANIO = 1;

    @Override
    public void atacar(Celda origen, Celda destino) {
        Direccion direccion = origen.direccionHacia(destino);

        origen.vecinaAl(direccion).primerBloqueoEn(direccion).ifPresent(casilla -> {
            casilla.recibirDanio(DANIO);
            casilla.empujarUnidadInterna(direccion);
        });
    }
}
package edu.fiuba.algo3.modelo.arma;

import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;

public class Punio implements Arma {
    private static final int DANIO = 2;

    @Override
    public void atacar(Celda origen, Celda destino) {
        if (!origen.esAdyacenteA(destino)) {
            throw new IllegalArgumentException("El Puño solo puede atacar casillas adyacentes");
        }
        Direccion direccion = origen.direccionHacia(destino);
        destino.recibirDanio(DANIO);
        destino.empujarUnidadInterna(direccion);
    }
}
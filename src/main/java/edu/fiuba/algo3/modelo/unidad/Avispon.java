package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;

public class Avispon extends Vek {

    public Avispon() {
        super(2);
    }

//Hay que refactorizar la logica esta que rompe tell dont ask, demeter y todo lo qu es sagrado

    @Override
    public void atacar(Celda origen, Celda destino) {
        if (!origen.esAdyacenteA(destino)) {
            throw new IllegalArgumentException("El Puño solo puede atacar casillas adyacentes");
        }
        Direccion direccion = origen.direccionHacia(destino);
        destino.recibirGolpeCuerpoACuerpo(direccion, 1);
    }
    @Override
    public void aplicarEfectoAcido() {

    }
}



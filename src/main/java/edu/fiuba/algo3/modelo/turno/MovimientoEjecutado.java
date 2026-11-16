package edu.fiuba.algo3.modelo.turno;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;

public class MovimientoEjecutado {

    private final Casilla origen;
    private final Casilla destino;

    public MovimientoEjecutado(Casilla origen, Casilla destino) {
        this.origen = origen;
        this.destino = destino;
    }

    public void ejecutar() {
        origen.moverUnidadHacia(destino);
    }

    public void deshacer() {
        destino.moverUnidadHacia(origen);
    }
}

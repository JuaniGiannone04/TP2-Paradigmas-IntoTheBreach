package edu.fiuba.algo3.modelo.turno;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;

public class Movimiento {

    private final Casilla origen;
    private final Casilla destino;

    public Movimiento(Casilla origen, Casilla destino) {
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

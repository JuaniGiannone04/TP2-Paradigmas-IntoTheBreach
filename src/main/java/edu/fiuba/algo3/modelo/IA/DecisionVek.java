package edu.fiuba.algo3.modelo.IA;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;

public class DecisionVek {

    private final Casilla destino;
    private final Direccion direccionAtaque;

    public DecisionVek(Casilla destino, Direccion direccionAtaque) {
        this.destino = destino;
        this.direccionAtaque = direccionAtaque;
    }

    public Casilla getDestino() {
        return destino;
    }

    public Direccion getDireccionAtaque() {
        return direccionAtaque;
    }

}
package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.Posicion;
import edu.fiuba.algo3.modelo.Tablero;
import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.terreno.Terreno;

public class Mech implements Unidad {
    private final Arma arma;

    public Mech(Arma arma) {
    this.arma = arma;
    }

    @Override
    public void recibirDanio(int danio) {

    }

    @Override
    public void aplicarEfectoAgua() {

    }

    @Override
    public void aplicarEfectoAcido() {

    }

    @Override
    public boolean puedeTransitar(Terreno terreno) {
        return false;
    }

    @Override
    public int getVidaActual() {
        return 0;
    }

    public void atacar(Posicion origen, Posicion destino, Tablero tablero) {
        arma.atacar(origen, destino, tablero);
    }
}
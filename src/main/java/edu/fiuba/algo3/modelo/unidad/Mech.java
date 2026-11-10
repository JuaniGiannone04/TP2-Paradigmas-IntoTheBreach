package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.Posicion;
import edu.fiuba.algo3.modelo.Tablero;
import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.terreno.Terreno;

public class Mech implements Unidad {

    private final Arma arma;
    private int vidaActual;
    private boolean contaminado;

    public Mech(Arma arma, int vidaInicial) {
        this.arma = arma;
        this.vidaActual = vidaInicial;
        this.contaminado = false;
    }


    @Override
    public void recibirDanio(int danio) {
        int danioFinal = danio;

        if (contaminado) {
            danioFinal *= 2;
        }

        vidaActual = Math.max(0, vidaActual - danioFinal);
    }

    @Override
    public void aplicarEfectoAgua() {

    }

    @Override
    public void aplicarEfectoAcido() {
        this.contaminado = true;
    }

    @Override
    public boolean puedeTransitar(Terreno terreno) {
        return false;
    }

    @Override
    public int getVidaActual() {
        return this.vidaActual;
    }

    public void atacar(Posicion origen, Posicion destino, Tablero tablero) {
        arma.atacar(origen, destino, tablero);
    }
}
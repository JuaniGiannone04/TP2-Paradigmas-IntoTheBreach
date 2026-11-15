package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.terreno.Terreno;
import edu.fiuba.algo3.modelo.ubicacion.Celda;

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
        int danioFinal = this.contaminado ? danio * 2 : danio;
        this.vidaActual = Math.max(0, this.vidaActual - danioFinal);
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
        return terreno.esTransitablePorTerrestre();
    }

    @Override
    public int getVidaActual() {
        return this.vidaActual;
    }

    public void atacar(Celda origen, Celda destino) {
        this.arma.atacar(origen, destino);
    }
}
package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.terreno.Terreno;

public class Vek implements Unidad {
    private int vidaActual;


    protected Vek(int vidaInicial) {
        this.vidaActual = vidaInicial;
    }

    @Override
    public void recibirDanio(int danio) {
        this.vidaActual = Math.max(0, vidaActual - danio);
    }

    @Override
    public void aplicarEfectoAgua() {

    }

    @Override
    public void aplicarEfectoAcido() {

    }

    @Override
    public boolean puedeTransitar(Terreno terreno) {
        return true;
    }

    @Override
    public int getVidaActual() {
        return this.vidaActual;
    }


}
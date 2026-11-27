package edu.fiuba.algo3.modelo.partida;

public class Perdida implements EstadoPartida {

    @Override
    public EstadoPartida evaluar(int turnoCompletado, int energia,int cantidadDeMechs) { return this; }

    @Override
    public boolean permiteAvanzar() {
        return false;
    }


}


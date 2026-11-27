package edu.fiuba.algo3.modelo.partida;

public interface EstadoPartida {
    EstadoPartida evaluar(int numeroDeTurno, int energia,int cantidadDeMechs);
    boolean permiteAvanzar();

}

package edu.fiuba.algo3.modelo;

public class FaseJugador implements Fase {

    @Override
    public void ejecutar(Turno turno, Tablero tablero) {
        turno.cambiarFase(new FaseResolucion());
    }


}
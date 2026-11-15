package edu.fiuba.algo3.modelo;

public class FaseVek implements Fase {

    @Override
    public void ejecutar(Turno turno, Tablero tablero) {
        turno.cambiarFase(new FaseJugador());

    }

}
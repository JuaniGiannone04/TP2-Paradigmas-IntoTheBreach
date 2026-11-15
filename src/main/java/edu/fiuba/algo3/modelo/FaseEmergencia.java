package edu.fiuba.algo3.modelo;

public class FaseEmergencia implements Fase {

    @Override
    public void ejecutar(Turno turno, Tablero tablero) {
        turno.terminarTurno();
    }
}
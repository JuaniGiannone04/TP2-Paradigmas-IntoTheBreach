package edu.fiuba.algo3.modelo.turno;

public class FaseEmergencia implements Fase {

    @Override
    public void ejecutar(Turno turno) {
        turno.terminarTurno();
    }
}
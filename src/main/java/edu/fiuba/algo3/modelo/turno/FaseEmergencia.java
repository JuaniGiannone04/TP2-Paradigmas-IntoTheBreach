package edu.fiuba.algo3.modelo.turno;

public class FaseEmergencia implements Fase {

    @Override
    public Fase ejecutar(Turno turno) {
        turno.terminarTurno();
        return new FaseVek();
    }
}
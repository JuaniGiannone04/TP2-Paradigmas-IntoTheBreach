package edu.fiuba.algo3.modelo.turno;


public class FaseResolucion implements Fase {

    @Override
    public void ejecutar(Turno turno) {
        turno.cambiarFase(new FaseEmergencia());
    }
}
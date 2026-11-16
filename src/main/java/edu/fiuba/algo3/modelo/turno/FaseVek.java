package edu.fiuba.algo3.modelo.turno;


public class FaseVek implements Fase {

    @Override
    public void ejecutar(Turno turno) {
        turno.cambiarFase(new FaseJugador());

    }

}
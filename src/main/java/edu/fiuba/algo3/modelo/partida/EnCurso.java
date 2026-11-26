package edu.fiuba.algo3.modelo.partida;

public class EnCurso implements EstadoPartida {

    @Override
    public EstadoPartida evaluar(int turnoCompletado, int energia,int cantidadMechs) {
        if (energia <= 0 || cantidadMechs <= 0 ) {
            return new Perdida();
        }
        if (turnoCompletado == 5) {
            return new Ganada();
        }
        return this;
    }

    @Override
    public boolean permiteAvanzar() {
        return true;
    }


}
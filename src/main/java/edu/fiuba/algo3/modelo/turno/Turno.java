package edu.fiuba.algo3.modelo.turno;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.unidad.Mech;

public class Turno {

    private int numero = 1;
    private Fase faseActual;

    public Turno() {
        this.faseActual = new FaseVek();
    }

    public void avanzarFase() {
        faseActual.ejecutar(this);
    }

    public Fase obtenerFaseActual() {
        return faseActual;
    }

    public int obtenerNumero() {
        return numero;
    }

    void cambiarFase(Fase nuevaFase) {
        this.faseActual = nuevaFase;
    }

    public void terminarTurno() {
        numero++;
        cambiarFase(new FaseVek());
    }

    public void moverMech(Mech mech, Casilla origen, Casilla destino) {
        faseJugador().moverMech(mech, origen, destino);
    }

    public void deshacerMovimiento(Mech mech) {
        faseJugador().deshacerMovimiento(mech);
    }

    public void atacarConMech(Mech mech, Celda origen, Celda destino) {
        faseJugador().atacarConMech(mech, origen, destino);
    }

    private FaseJugador faseJugador() {
        if (!(faseActual instanceof FaseJugador)) {
            throw new IllegalStateException("Solo se puede actuar en la Fase del Jugador");
        }
        return (FaseJugador) faseActual;
    }
}
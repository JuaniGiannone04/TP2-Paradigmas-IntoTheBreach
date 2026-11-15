package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.unidad.Mech;

public class Turno {

    private final Tablero tablero;
    private boolean yaSeDeshizoUnMovimiento = false;
    private Fase faseActual;
    private int numeroDeTurno = 1;

    public Turno(Tablero tablero) {
        this.tablero = tablero;
        this.faseActual = new FaseVek();
    }

    public void moverMech(Mech mech, Posicion destino) {
        if (!(faseActual instanceof FaseJugador)) {
            throw new IllegalStateException("Solo se mueven los Mechs en la Fase del Jugador");
        }
        Posicion origen = posicionDe(mech);
        mech.mover(origen, destino, tablero);
    }

    public void deshacerMovimiento(Mech mech) {
        if (mech.yaAtaco()) {
            throw new IllegalStateException("No se puede deshacer un movimiento después de atacar");
        }
        if (yaSeDeshizoUnMovimiento) {
            throw new IllegalStateException("Solo se puede deshacer un movimiento por turno");
        }

        mech.deshacerMovimiento(tablero);
        yaSeDeshizoUnMovimiento = true;
    }

    private Posicion posicionDe(Mech mech) {
        for (int fila = 0; fila < Tablero.DIMENSION; fila++) {
            for (int col = 0; col < Tablero.DIMENSION; col++) {
                Posicion posicion = new Posicion(fila, col);
                Casilla casilla = tablero.obtenerCasilla(posicion);
                if (casilla.estaOcupada() && casilla.getUnidad() == mech) {
                    return posicion;
                }
            }
        }
        throw new IllegalStateException("El Mech no está en el tablero");
    }

    public Fase obtenerFaseActual() {
        return faseActual;
    }

    public void avanzarFase() {
        faseActual.ejecutar(this, tablero);
    }

    public void cambiarFase(Fase nuevaFase) {
        this.faseActual = nuevaFase;
    }

    public int obtenerNumero() {return numeroDeTurno;}

    public void terminarTurno() {
        numeroDeTurno++;
        yaSeDeshizoUnMovimiento = false;
        cambiarFase(new FaseVek());
    }
}
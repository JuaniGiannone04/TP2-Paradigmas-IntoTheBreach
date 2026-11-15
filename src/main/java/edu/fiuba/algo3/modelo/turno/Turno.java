package edu.fiuba.algo3.modelo.turno;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.unidad.Mech;

import java.util.*;

public class Turno {

    private int numero = 1;
    private Fase faseActual;
    private boolean deshacerDisponible = true;
    private final Map<Mech, Movimiento> movimientos = new HashMap<>();
    private final List<Mech> mechsQueAtacaron = new ArrayList<>();

    public Turno() {
        this.faseActual = new FaseVek();
    }

    public void avanzarFase() {
        faseActual.ejecutar(this);
    }

    public Fase obtenerFaseActual() {
        return faseActual;
    }

    private void validarFaseJugador(String mensaje) {
        if (!(faseActual instanceof FaseJugador)) {
            throw new IllegalStateException(mensaje);
        }
    }

    public int obtenerNumero() {
        return numero;
    }

    void cambiarFase(Fase nuevaFase) {
        this.faseActual = nuevaFase;
    }

    public void terminarTurno() {
        numero++;
        deshacerDisponible = true;
        movimientos.clear();
        mechsQueAtacaron.clear();
        cambiarFase(new FaseVek());
    }

    public void moverMech(Mech mech, Casilla origen, Casilla destino) {
        validarFaseJugador("Solo se mueven los Mechs en la Fase del Jugador");
        if (mechsQueAtacaron.contains(mech)) {
            throw new IllegalStateException("El Mech ya atacó en este turno");
        }
        if (movimientos.containsKey(mech)) {
            throw new IllegalStateException("El Mech ya se movió en este turno");
        }

        Movimiento movimiento = new Movimiento(origen, destino);
        movimiento.ejecutar();
        movimientos.put(mech, movimiento);
    }

    public void deshacerMovimiento(Mech mech) {
        validarFaseJugador("Solo se deshacen movimientos en la Fase del Jugador");
        if (mechsQueAtacaron.contains(mech)) {
            throw new IllegalStateException("No se puede deshacer un movimiento después de atacar");
        }
        if (!deshacerDisponible) {
            throw new IllegalStateException("Solo se puede deshacer un movimiento por turno");
        }

        Movimiento movimiento = movimientos.remove(mech);
        if (movimiento == null) {
            throw new IllegalStateException("El Mech no tiene un movimiento para deshacer");
        }

        movimiento.deshacer();
        deshacerDisponible = false;
    }

    public void atacarConMech(Mech mech, Celda origen, Celda destino) {
        validarFaseJugador("Solo atacan los Mechs en la Fase del Jugador");
        if (mechsQueAtacaron.contains(mech)) {
            throw new IllegalStateException("El Mech ya atacó en este turno");
        }

        mech.atacar(origen, destino);
        mechsQueAtacaron.add(mech);
    }


}
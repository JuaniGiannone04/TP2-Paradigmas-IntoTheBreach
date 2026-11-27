package edu.fiuba.algo3.modelo.turno;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.unidad.Mech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FaseJugador implements Fase {

    private final List<AtaqueAnunciado> ataquesAnunciados;
    private final Map<Mech, MovimientoEjecutado> movimientos = new HashMap<>();
    private final List<Mech> mechsQueAtacaron = new ArrayList<>();
    private boolean deshacerDisponible = true;
    private final FaseEmergencia emergencia;

    public FaseJugador(List<AtaqueAnunciado> ataquesAnunciados) {
        this.ataquesAnunciados = ataquesAnunciados;
        this.emergencia = new FaseEmergencia();
    }

    public FaseJugador(List<AtaqueAnunciado> ataquesAnunciados,FaseEmergencia emergencia) {
        this.ataquesAnunciados = ataquesAnunciados;
        this.emergencia = emergencia;
    }

    @Override
    public FaseResolucion ejecutar(Turno turno) {
        return new FaseResolucion(this.ataquesAnunciados, emergencia);
    }

    public void moverMech(Mech mech, Casilla origen, Casilla destino) {
        if (mechsQueAtacaron.contains(mech)) {
            throw new IllegalStateException("El Mech ya atacó en este turno");
        }
        if (movimientos.containsKey(mech)) {
            throw new IllegalStateException("El Mech ya se movió en este turno");
        }

        MovimientoEjecutado movimiento = new MovimientoEjecutado(mech, origen, destino);
        movimiento.ejecutar();
        movimientos.put(mech, movimiento);
    }

    public void deshacerMovimiento(Mech mech) {
        if (mechsQueAtacaron.contains(mech)) {
            throw new IllegalStateException("No se puede deshacer un movimiento después de atacar");
        }

        if (!deshacerDisponible) {
            throw new IllegalStateException("Solo se puede deshacer un movimiento por turno");
        }

        MovimientoEjecutado movimiento = movimientos.get(mech);

        if (movimiento == null) {
            throw new IllegalStateException("El Mech no tiene un movimiento para deshacer");
        }

        movimiento.deshacer();
        movimientos.remove(mech);
        deshacerDisponible = false;
    }

    public void atacarConMech(Mech mech, Celda origen, Celda destino) {
        if (mechsQueAtacaron.contains(mech)) {
            throw new IllegalStateException("El Mech ya atacó en este turno");
        }

        mech.atacar(origen, destino);
        mechsQueAtacaron.add(mech);
    }
}
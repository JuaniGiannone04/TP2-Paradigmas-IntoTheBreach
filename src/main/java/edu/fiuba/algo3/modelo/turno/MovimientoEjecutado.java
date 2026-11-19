package edu.fiuba.algo3.modelo.turno;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.unidad.EstadoSalud;
import edu.fiuba.algo3.modelo.unidad.Mech;

public class MovimientoEjecutado {

    private final Mech mech;
    private final Casilla origen;
    private final Casilla destino;
    private final EstadoSalud estadoAnterior;

    public MovimientoEjecutado(Mech mech, Casilla origen, Casilla destino) {
        this.mech = mech;
        this.origen = origen;
        this.destino = destino;
        this.estadoAnterior = mech.obtenerEstadoSalud();
    }

    public void ejecutar() {
        if (origen.getUnidad() != mech){
            throw new IllegalStateException("El Mech indicado no está en la casilla de origen");
        }
        origen.moverUnidadHacia(destino);
    }

    public void deshacer() {
        destino.moverUnidadHacia(origen);
        mech.restaurarEstadoSalud(estadoAnterior);
    }

}
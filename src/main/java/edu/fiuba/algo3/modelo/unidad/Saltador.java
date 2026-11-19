package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.terreno.Terreno;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;

public class Saltador extends Vek {

    public Saltador() {
        super(2, 3);
    }

    @Override
    public void aplicarEfectoAgua() {
        this.recibirDanio(this.getVidaActual());
    }

    @Override
    public void atacar(Celda origen, Celda destino) {
        if (!origen.esAdyacenteA(destino)) {
            throw new IllegalArgumentException("El Puño solo puede atacar casillas adyacentes");
        }
        Direccion direccion = origen.direccionHacia(destino);
        destino.recibirGolpeCuerpoACuerpo(direccion, 2);
    }

    @Override
    public boolean puedeTransitar(Terreno terreno) {
        return terreno.esTransitablePorTerrestre();
    }


}
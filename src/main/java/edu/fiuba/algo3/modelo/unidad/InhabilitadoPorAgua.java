package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.ubicacion.Celda;

public class InhabilitadoPorAgua implements EstadoAtaque {
    @Override
    public void atacar(Arma arma, Celda origen, Celda destino) {
        throw new IllegalStateException("El Mech no puede atacar mientras está en el agua");
    }
}
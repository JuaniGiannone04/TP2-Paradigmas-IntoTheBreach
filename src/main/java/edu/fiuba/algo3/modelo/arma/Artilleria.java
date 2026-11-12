package edu.fiuba.algo3.modelo.arma;

import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.ubicacion.Posicion;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;

public class Artilleria implements Arma {

    @Override
    public void atacar(Posicion origen, Posicion destino, Tablero tablero) {
        if (!origen.estaEnLineaRectaCon(destino)) {
            throw new IllegalArgumentException("El objetivo no se encuentra en línea recta");
        }
        if (origen.distanciaOrtogonalA(destino) < 2) {
            throw new IllegalArgumentException("La artillería debe atacar a 2 o más casillas");
        }

        tablero.aplicarDanio(destino, 1);

        for (Direccion direccion : Direccion.values()) {
            Posicion posAdyacente = destino.sumar(direccion);
            Posicion posEmpuje = posAdyacente.sumar(direccion);
            tablero.empujarUnidad(posAdyacente, posEmpuje);
        }
    }
}
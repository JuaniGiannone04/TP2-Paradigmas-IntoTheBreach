package edu.fiuba.algo3.modelo.arma;

import edu.fiuba.algo3.modelo.Posicion;
import edu.fiuba.algo3.modelo.Tablero;

public class Canion implements Arma {


    @Override
    public void atacar(Posicion origen, Posicion destino, Tablero tablero) {
        if (!origen.estaEnLineaRectaCon(destino)) {
            throw new IllegalArgumentException("El objetivo no se encuentra en linea recta");
        }

        tablero.obtenerCasilla(destino).recibirDanio(1);

        tablero.empujarUnidad(origen, destino);
    }
}
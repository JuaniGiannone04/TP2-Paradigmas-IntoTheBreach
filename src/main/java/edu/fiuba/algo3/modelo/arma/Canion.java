package edu.fiuba.algo3.modelo.arma;

import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.ubicacion.Posicion;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;

public class Canion implements Arma {

    @Override
    public void atacar(Posicion origen, Posicion destino, Tablero tablero) {
        Direccion dirDisparo = origen.direccionHacia(destino);
        Posicion posActual = origen.sumar(dirDisparo);

        while (tablero.contienePosicion(posActual)) {
            if (tablero.detieneDisparoDirecto(posActual)) {
                tablero.aplicarDanio(posActual, 1);
                tablero.empujarUnidad(posActual, posActual.sumar(dirDisparo));
                return;
            }
            posActual = posActual.sumar(dirDisparo);
        }
    }
}
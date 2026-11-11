package edu.fiuba.algo3.modelo.arma;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.ubicacion.Posicion;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;

public class Canion implements Arma {


    @Override
    public void atacar(Posicion origen, Posicion destino, Tablero tablero) {
        Direccion dirDisparo = origen.direccionHacia(destino);
        Posicion posActual = origen.sumar(dirDisparo);

        while (tablero.contienePosicion(posActual)) {
            Casilla casillaActual = tablero.obtenerCasilla(posActual);

            if (casillaActual.detieneDisparoDirecto()) {

                casillaActual.recibirDanio(1);

                Posicion posEmpuje = posActual.sumar(dirDisparo);

                tablero.empujarUnidad(posActual, posEmpuje);

                return; // Termina el ataque porque ya impactó
            }
            posActual = posActual.sumar(dirDisparo);
        }
    }
}
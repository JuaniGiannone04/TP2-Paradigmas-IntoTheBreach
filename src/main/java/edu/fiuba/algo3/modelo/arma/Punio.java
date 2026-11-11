package edu.fiuba.algo3.modelo.arma;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.ubicacion.Posicion;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;

public class Punio implements Arma {

    @Override
    public void atacar(Posicion origen, Posicion destino, Tablero tablero) {
        if (!origen.esAdyacenteA(destino)) {
            throw new IllegalArgumentException("El Puño solo puede atacar casillas adyacentes");
        }

        Casilla casillaDestino = tablero.obtenerCasilla(destino);

        casillaDestino.recibirDanio(2);

        Direccion dirGolpe = origen.direccionHacia(destino);
        Posicion posEmpuje = destino.sumar(dirGolpe);

        tablero.empujarUnidad(destino, posEmpuje);
    }


}
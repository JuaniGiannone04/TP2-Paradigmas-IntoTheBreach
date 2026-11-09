package edu.fiuba.algo3.modelo.arma;

import edu.fiuba.algo3.modelo.Posicion;
import edu.fiuba.algo3.modelo.Tablero;

public class Punio implements Arma {

    @Override
    public void atacar(Posicion origen, Posicion destino, Tablero tablero) {
        if (!origen.esAdyacenteA(destino)) {
            throw new IllegalArgumentException("El destino no es adyacente");
        }

        tablero.obtenerCasilla(destino).recibirDanio(2);
    }


}
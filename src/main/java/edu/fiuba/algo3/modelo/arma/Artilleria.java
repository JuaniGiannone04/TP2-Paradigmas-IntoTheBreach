package edu.fiuba.algo3.modelo.arma;

import edu.fiuba.algo3.modelo.Posicion;
import edu.fiuba.algo3.modelo.Tablero;

public class Artilleria implements Arma {


    @Override
    public void atacar(Posicion origen, Posicion destino, Tablero tablero) {

        if (!origen.estaEnLineaRectaCon(destino)) {
            throw new IllegalArgumentException(
                    "El objetivo no se encuentra en línea recta"
            );
        }

        if (origen.distanciaOrtogonalA(destino) < 2) {
            throw new IllegalArgumentException(
                    "La artillería debe atacar a 2 o más casillas"
            );
        }

        tablero.obtenerCasilla(destino).recibirDanio(1);

        Posicion[] desplazamientos = {
                new Posicion(-1, 0),
                new Posicion(1, 0),
                new Posicion(0, -1),
                new Posicion(0, 1)
        };

        for (Posicion desplazamiento : desplazamientos) {

            Posicion adyacente = new Posicion(
                    destino.getFila() + desplazamiento.getFila(),
                    destino.getColumna() + desplazamiento.getColumna()
            );

            if (tablero.contienePosicion(adyacente)
                    && tablero.obtenerCasilla(adyacente).estaOcupada()) {

                tablero.empujarUnidad(destino, adyacente);
            }
        }
    }
}
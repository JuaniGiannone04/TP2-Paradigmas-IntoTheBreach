package edu.fiuba.algo3.modelo.arma;

import edu.fiuba.algo3.modelo.ubicacion.Posicion;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;

public interface Arma {
    public void atacar(Posicion origen, Posicion destino, Tablero tablero);
}
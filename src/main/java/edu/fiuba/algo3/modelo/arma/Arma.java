package edu.fiuba.algo3.modelo.arma;

import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;

public interface Arma {
    void atacar(Celda origen, Direccion direccion, int distancia);
}
package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.ubicacion.Celda;

public interface EstadoAtaque {
    void atacar(Arma arma, Celda origen, Celda destino);
}
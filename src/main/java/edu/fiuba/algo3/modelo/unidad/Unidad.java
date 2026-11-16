package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.terreno.Terreno;
import edu.fiuba.algo3.modelo.ubicacion.Celda;

public interface Unidad {
    void recibirDanio(int danio);
    void aplicarEfectoAgua();
    void aplicarEfectoAcido();
    boolean puedeTransitar(Terreno terreno);
    void atacar(Celda origen, Celda destino);
    int getVidaActual();
}
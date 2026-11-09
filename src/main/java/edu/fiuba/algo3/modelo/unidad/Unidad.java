package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.terreno.Terreno;

public interface Unidad {
    void recibirDanio(int danio);
    void aplicarEfectoAgua();
    void aplicarEfectoAcido();
    boolean puedeTransitar(Terreno terreno);

    int getVidaActual();
}
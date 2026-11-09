package edu.fiuba.algo3.modelo.terreno;

import edu.fiuba.algo3.modelo.unidad.Unidad;

public interface Terreno {
    boolean esTransitablePorTerrestre();
    boolean bloqueaDisparoDirecto();
    void aplicarEfecto(Unidad unidad);
    Terreno recibirDanio(int danio);
}
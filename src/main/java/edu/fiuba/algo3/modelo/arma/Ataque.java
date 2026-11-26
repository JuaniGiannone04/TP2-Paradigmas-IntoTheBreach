package edu.fiuba.algo3.modelo.arma;

import edu.fiuba.algo3.modelo.ubicacion.BordeDelMapa;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;

public interface Ataque {
    void impactarEn(Casilla casilla);
    void impactarEn(BordeDelMapa borde);
}
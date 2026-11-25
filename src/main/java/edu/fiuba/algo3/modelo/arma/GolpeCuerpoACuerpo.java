package edu.fiuba.algo3.modelo.arma;

import edu.fiuba.algo3.modelo.ubicacion.BordeDelMapa;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;

public class GolpeCuerpoACuerpo implements Ataque {
    private final Direccion direccion;
    private final int danio;

    public GolpeCuerpoACuerpo(Direccion direccion, int danio) {
        this.direccion = direccion;
        this.danio = danio;
    }

    @Override
    public void impactarEn(Casilla casilla) {
        casilla.recibirDanio(this.danio);
        casilla.empujarUnidadInterna(this.direccion);
    }

    @Override
    public void impactarEn(BordeDelMapa borde) {
        throw new IllegalArgumentException("No se puede golpear fuera del tablero");
    }
}
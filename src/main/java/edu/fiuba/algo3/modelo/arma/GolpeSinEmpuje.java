package edu.fiuba.algo3.modelo.arma;

import edu.fiuba.algo3.modelo.ubicacion.BordeDelMapa;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;

public class GolpeSinEmpuje implements Ataque {
    private final int danio;

    public GolpeSinEmpuje(int danio) {
        this.danio = danio;
    }

    @Override
    public void impactarEn(Casilla casilla) {
        casilla.recibirDanio(this.danio);
    }

    @Override
    public void impactarEn(BordeDelMapa borde) {
    }
}
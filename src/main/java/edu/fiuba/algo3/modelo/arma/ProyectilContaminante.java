package edu.fiuba.algo3.modelo.arma;

import edu.fiuba.algo3.modelo.ubicacion.BordeDelMapa;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;

public class ProyectilContaminante implements Ataque {
    private final Direccion direccion;
    private final int danio;

    public ProyectilContaminante(Direccion direccion, int danio) {
        this.direccion = direccion;
        this.danio = danio;
    }

    @Override
    public void impactarEn(Casilla casilla) {
        if (casilla.detieneProyectil()) {
            casilla.recibirDanio(this.danio);
            casilla.empujarUnidadInterna(this.direccion);
            casilla.contaminarOcupante();
        } else {
            casilla.propagar(this.direccion, this);
        }
    }

    @Override
    public void impactarEn(BordeDelMapa borde) {
    }
}
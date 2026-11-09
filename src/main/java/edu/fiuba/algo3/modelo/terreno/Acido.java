package edu.fiuba.algo3.modelo.terreno;

import edu.fiuba.algo3.modelo.unidad.Unidad;

public class Acido implements Terreno {
    @Override
    public boolean esTransitablePorTerrestre() {
        return true;
    }

    @Override
    public boolean bloqueaDisparoDirecto() {
        return false;
    }

    @Override
    public void aplicarEfecto(Unidad unidad) {
        unidad.aplicarEfectoAcido();
    }

    @Override
    public Terreno recibirDanio(int danio) {
        return this;
    }
}
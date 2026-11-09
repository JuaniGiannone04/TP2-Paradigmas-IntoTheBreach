package edu.fiuba.algo3.modelo.terreno;

import edu.fiuba.algo3.modelo.unidad.Unidad;

public class Suelo implements Terreno {
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
        // El suelo normal no aplica efectos especiales al pisarlo
    }

    @Override
    public Terreno recibirDanio(int danio) {
        return this;
    }
}
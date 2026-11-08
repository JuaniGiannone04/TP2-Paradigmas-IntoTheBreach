package edu.fiuba.algo3.modelo;

public class Agua implements Terreno {
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
        unidad.aplicarEfectoAgua();
    }

    @Override
    public Terreno recibirDanio(int danio) {
        return this;
    }
}
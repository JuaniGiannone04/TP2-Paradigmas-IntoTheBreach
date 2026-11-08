package edu.fiuba.algo3.modelo;

public class Montana implements Terreno {
    @Override
    public boolean esTransitablePorTerrestre() {
        return false;
    }

    @Override
    public boolean bloqueaDisparoDirecto() {
        return true;
    }

    @Override
    public void aplicarEfecto(Unidad unidad) {
        // Las unidades voladoras pueden pasar por encima, no les aplica efecto
    }

    @Override
    public Terreno recibirDanio(int danio) {
        return this; // Es indestructible
    }
}
package edu.fiuba.algo3.modelo;

public interface Terreno {
    boolean esTransitablePorTerrestre();
    boolean bloqueaDisparoDirecto();
    void aplicarEfecto(Unidad unidad);
    Terreno recibirDanio(int danio);
}
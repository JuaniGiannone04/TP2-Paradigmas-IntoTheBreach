package edu.fiuba.algo3.modelo.ubicacion;

public class Tablero {
    private final Celda casillaInicial;

    public Tablero(Celda casillaInicial) {
        this.casillaInicial = casillaInicial;
    }

    public Celda getCasillaInicial() {
        return this.casillaInicial;
    }
}
package edu.fiuba.algo3.modelo.ubicacion;

import java.util.Collections;
import java.util.List;

public class Tablero {
    private final List<Casilla> casillas;

    public Tablero(List<Casilla> casillas) {
        this.casillas = casillas;
    }


    public Casilla getCasillaInicial() {
        return this.casillas.get(0);
    }
}
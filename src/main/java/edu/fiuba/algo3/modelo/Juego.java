package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.GeneradorDeTablero;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;

public class Juego {
    private static final int ENERGIA_INICIAL = 5;
    private final Tablero tablero;
    private final RedElectrica redElectrica;

    public Juego() {
        this.redElectrica = new RedElectrica(ENERGIA_INICIAL);
        GeneradorDeTablero fabrica = new GeneradorDeTablero();
        this.tablero = fabrica.crearTableroPredefinido(this.redElectrica);
    }

    public Juego(Tablero tableroPersonalizado, RedElectrica redElectrica) {
        this.tablero = tableroPersonalizado;
        this.redElectrica = redElectrica;
    }

    public Tablero getTablero() {
        return this.tablero;
    }

    public int getEnergiaRedElectrica() {
        return this.redElectrica.getEnergia();
    }
}
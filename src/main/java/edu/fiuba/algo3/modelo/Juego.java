package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.terreno.*;
import edu.fiuba.algo3.modelo.ubicacion.Posicion;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;

public class Juego {
    private static final int ENERGIA_INICIAL = 5;
    private final Tablero tablero;
    private final RedElectrica redElectrica;

    public Juego() {
        this.redElectrica = new RedElectrica(ENERGIA_INICIAL);
        this.tablero = new Tablero();
        cargarMapaPredefinido();
    }

//hay que hacer una fabrica del tablero despues pára no tener que hacer esto aca, pero por ahora esta bien

    // Constructor para inyectar tableros armados a medida en las pruebas
    public Juego(Tablero tableroPersonalizado, RedElectrica redElectrica) {
        this.tablero = tableroPersonalizado;
        this.redElectrica = redElectrica;
    }

    private void cargarMapaPredefinido() {
        // Montañas
        this.tablero.configurarTerreno(new Posicion(0, 0), new Montana());
        this.tablero.configurarTerreno(new Posicion(0, 1), new Montana());
        this.tablero.configurarTerreno(new Posicion(1, 0), new Montana());

        // Edificios conectados a la Red Eléctrica
        this.tablero.configurarTerreno(new Posicion(2, 2), new Edificio(this.redElectrica));
        this.tablero.configurarTerreno(new Posicion(2, 3), new Edificio(this.redElectrica));
        this.tablero.configurarTerreno(new Posicion(5, 4), new Edificio(this.redElectrica));

        // Agua
        this.tablero.configurarTerreno(new Posicion(7, 6), new Agua());
        this.tablero.configurarTerreno(new Posicion(7, 7), new Agua());

        // Ácido
        this.tablero.configurarTerreno(new Posicion(3, 5), new Acido());
        this.tablero.configurarTerreno(new Posicion(4, 5), new Acido());
    }

    public Tablero getTablero() {
        return this.tablero;
    }

    public int getEnergiaRedElectrica() {
        return this.redElectrica.getEnergia();
    }
}
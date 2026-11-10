package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.terreno.*;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
public class MapaPredefinidoTest{

    @Test
    public void cargarUnMapaDeOchoPorOchoConLosTerrenosEnSusPosiciones(){

        Tablero tablero = new Juego().getTablero();

        Map<Posicion, Class<? extends Terreno>> terrenosEspeciales = Map.of(
            new Posicion(0, 0), Montana.class,
            new Posicion(0, 1), Montana.class,
            new Posicion(1, 0), Montana.class,
            new Posicion(2, 2), Edificio.class,
            new Posicion(2, 3), Edificio.class,
            new Posicion(5, 4), Edificio.class,
            new Posicion(7, 6), Agua.class,
            new Posicion(7, 7), Agua.class,
            new Posicion(3, 5), Acido.class,
            new Posicion(4, 5), Acido.class
        );

        assertEquals(64, tablero.cantidadDeCasillas());

        for (int fila = 0; fila < 8; fila ++){
            for (int columna = 0; columna < 8; columna++){
                Posicion posicion = new Posicion(fila, columna);
                Class<? extends Terreno> terrenoEsperado = terrenosEspeciales.getOrDefault(posicion, Suelo.class);

                assertTrue(tablero.contienePosicion(posicion));
                assertTrue(
                        terrenoEsperado.isInstance(tablero.obtenerCasilla(posicion).getTerreno()),
                        "Terreno incorrecto en " + posicion);
            }
        }
    }
}
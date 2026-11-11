package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.arma.Punio;
import edu.fiuba.algo3.modelo.terreno.Montana;
import edu.fiuba.algo3.modelo.ubicacion.Posicion;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;
import edu.fiuba.algo3.modelo.unidad.Luciernaga;
import edu.fiuba.algo3.modelo.unidad.Mech;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PunioTest {

    @Test
    public void test01PunioCausa2DeDanioAUnidadAdyacenteYLaEmpuja1Casilla() {
        Tablero tablero = new Tablero();
        Mech mechCombate = new Mech(new Punio(), 3);
        Luciernaga vekObjetivo = new Luciernaga(); // Vida inicial: 3[cite: 6]

        Posicion posMech = new Posicion(3, 3);
        Posicion posVek = new Posicion(3, 4);
        Posicion posDestinoEmpuje = new Posicion(3, 5); // Dirección Este

        tablero.colocarUnidad(posMech, mechCombate);
        tablero.colocarUnidad(posVek, vekObjetivo);

        // Act
        mechCombate.atacar(posMech, posVek, tablero);

        // Assert
        assertEquals(1, vekObjetivo.getVidaActual());

        assertFalse(tablero.obtenerCasilla(posVek).estaOcupada());
        assertEquals(vekObjetivo, tablero.obtenerCasilla(posDestinoEmpuje).getUnidad());
    }

    @Test
    public void test02PunioLanzaExcepcionSiIntentaAtacarUnaCasillaNoAdyacente() {
        Tablero tablero = new Tablero();
        Mech mechCombate = new Mech(new Punio(), 3);

        Posicion posMech = new Posicion(3, 3);
        Posicion posLejana = new Posicion(3, 5); // Distancia 2

        tablero.colocarUnidad(posMech, mechCombate);

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            mechCombate.atacar(posMech, posLejana, tablero);
        });
    }

    @Test
    public void test03PunioEmpujaUnidadContraMontanaCausandoDanioExtraPorColision() {
        Tablero tablero = new Tablero();
        Mech mechCombate = new Mech(new Punio(), 3);
        Luciernaga vekObjetivo = new Luciernaga();

        Posicion posMech = new Posicion(4, 4);
        Posicion posVek = new Posicion(4, 3);
        Posicion posMontana = new Posicion(4, 2);

        tablero.colocarUnidad(posMech, mechCombate);
        tablero.colocarUnidad(posVek, vekObjetivo);
        tablero.configurarTerreno(posMontana, new Montana());
        // Act
        mechCombate.atacar(posMech, posVek, tablero);
        // Assert
        assertEquals(vekObjetivo, tablero.obtenerCasilla(posVek).getUnidad());
        assertEquals(0, vekObjetivo.getVidaActual());
    }
}
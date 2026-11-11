package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.arma.Canion;
import edu.fiuba.algo3.modelo.terreno.Montana;
import edu.fiuba.algo3.modelo.ubicacion.Posicion;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;
import edu.fiuba.algo3.modelo.unidad.Luciernaga;
import edu.fiuba.algo3.modelo.unidad.Mech;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CanionTest {

    @Test
    public void test01CanionDaniaALaUnidadYLaEmpujaAOtraPosicionVacia() {
        // Arrange
        Tablero tablero = new Tablero();
        Mech mech = new Mech(new Canion(), 3);
        Luciernaga vek = new Luciernaga();

        Posicion posMech = new Posicion(2, 1);
        Posicion posVek = new Posicion(2, 3);
        Posicion posDestinoEmpuje = new Posicion(2, 4);

        tablero.colocarUnidad(posMech, mech);
        tablero.colocarUnidad(posVek, vek);

        // Act - El cañón dispara hacia el este
        mech.atacar(posMech, new Posicion(2, 7), tablero);

        // Assert
        // 1 de daño por el arma
        assertEquals(2, vek.getVidaActual());

        //  la posición original queda vacía
        assertFalse(tablero.obtenerCasilla(posVek).estaOcupada());

        //  Se movio a aca
        assertEquals(vek, tablero.obtenerCasilla(posDestinoEmpuje).getUnidad());
    }

    @Test
    public void test02CanionDaniaALaUnidadYSiChocaConObstaculoNoSeMueveYRecibeDanioExtra() {
        // Arrange
        Tablero tablero = new Tablero();
        Mech mech = new Mech(new Canion(), 3);
        Luciernaga vek = new Luciernaga();

        Posicion posMech = new Posicion(5, 1);
        Posicion posVek = new Posicion(5, 3);
        Posicion posObstaculo = new Posicion(5, 4);

        tablero.colocarUnidad(posMech, mech);
        tablero.colocarUnidad(posVek, vek);

        tablero.configurarTerreno(posObstaculo, new Montana());

        // Act
        mech.atacar(posMech, new Posicion(5, 7), tablero);

        // Assert
        //  que NO cambie de posición
        assertEquals(vek, tablero.obtenerCasilla(posVek).getUnidad());

        assertEquals(1, vek.getVidaActual());
    }
}
package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.terreno.Acido;
import edu.fiuba.algo3.modelo.terreno.Agua;
import edu.fiuba.algo3.modelo.terreno.Suelo;
import edu.fiuba.algo3.modelo.unidad.Unidad;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class TableroYTerrenoTest {



    @Test
    public void test03ColocarUnidadEnAcidoOAguaDisparaElEfectoDelTerreno() {
        Unidad unidadMock = mock(Unidad.class);
        Casilla casillaAcido = new Casilla(new Acido());
        Casilla casillaAgua = new Casilla(new Agua());

        casillaAcido.colocarUnidad(unidadMock);
        verify(unidadMock, times(1)).aplicarEfectoAcido();

        casillaAgua.colocarUnidad(unidadMock);
        verify(unidadMock, times(1)).aplicarEfectoAgua();
    }

    @Test
    public void test04NoSePuedeColocarUnaUnidadEnUnaCasillaYaOcupada() {
        Unidad unidad1 = mock(Unidad.class);
        Unidad unidad2 = mock(Unidad.class);
        Casilla casilla = new Casilla(new Suelo());

        casilla.colocarUnidad(unidad1);

        assertThrows(IllegalStateException.class, () -> {
            casilla.colocarUnidad(unidad2);
        });
    }

    @Test
    public void test05MoverUnidadEnTableroActualizaCasillaOrigenYDestino() {
        Tablero tablero = new Tablero();
        Unidad unidadMock = mock(Unidad.class);
        Posicion origen = new Posicion(2, 2);
        Posicion destino = new Posicion(2, 3);

        tablero.colocarUnidad(origen, unidadMock);
        tablero.moverUnidad(origen, destino);

        assertFalse(tablero.obtenerCasilla(origen).estaOcupada());
        assertTrue(tablero.obtenerCasilla(destino).estaOcupada());
        assertEquals(unidadMock, tablero.obtenerCasilla(destino).getUnidad());
    }

    @Test
    public void test06PedirCasillaFueraDeLosLimitesDe8x8LanzaExcepcion() {
        Tablero tablero = new Tablero();

        assertThrows(IllegalArgumentException.class, () -> {
            tablero.obtenerCasilla(new Posicion(-1, 0));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            tablero.obtenerCasilla(new Posicion(8, 8));
        });
    }
}
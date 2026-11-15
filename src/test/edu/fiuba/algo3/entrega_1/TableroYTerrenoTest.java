package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.GeneradorDeTablero;
import edu.fiuba.algo3.modelo.terreno.Acido;
import edu.fiuba.algo3.modelo.terreno.Agua;
import edu.fiuba.algo3.modelo.terreno.Suelo;
import edu.fiuba.algo3.modelo.ubicacion.*;
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
    public void test05MoverUnidadEntreCasillasActualizaOrigenYDestino() {
        Unidad unidadMock = mock(Unidad.class);
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        origen.colocarUnidad(unidadMock);
        origen.moverUnidadHacia(destino);

        assertFalse(origen.estaOcupada());
        assertTrue(destino.estaOcupada());
        assertSame(unidadMock, destino.getUnidad());
    }

    @Test
    public void test06EmpujarUnidadHaciaElBordeDelMapaNoLaMueveNiLaDania() {
        GeneradorDeTablero fabrica = new GeneradorDeTablero();
        Tablero tablero = fabrica.crearTableroVacio();

        Casilla esquinaNoroeste = (Casilla) tablero.getCasillaInicial();
        Unidad unidadMock = mock(Unidad.class);
        esquinaNoroeste.colocarUnidad(unidadMock);

        esquinaNoroeste.empujarUnidadInterna(Direccion.NORTE);

        assertSame(unidadMock, esquinaNoroeste.getUnidad());
        verify(unidadMock, never()).recibirDanio(anyInt());
    }
}
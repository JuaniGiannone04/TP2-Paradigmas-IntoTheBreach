package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.arma.Punio;
import edu.fiuba.algo3.modelo.terreno.Suelo;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.unidad.Luciernaga;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PunioTest {

    // Test con Mocks en PunioTest:
    @Test
    public void test01PunioDaniaYEmpujaUnaUnidadAdyacente() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        Casilla siguiente = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, destino);
        destino.conectarVecina(Direccion.ESTE, siguiente);

        Luciernaga vek = new Luciernaga();
        destino.colocarUnidad(vek);

        new Punio().atacar(origen, destino);

        assertEquals(1, vek.getVidaActual());
        assertFalse(destino.estaOcupada());
        assertSame(vek, siguiente.getUnidad());
    }

    @Test
    public void test02PunioLanzaExcepcionSiElDestinoNoEsAdyacente() {
        Celda origenMock = mock(Celda.class);
        Celda destinoMock = mock(Celda.class);

        when(origenMock.esAdyacenteA(destinoMock)).thenReturn(false);

        Arma punio = new Punio();

        assertThrows(IllegalArgumentException.class, () -> {
            punio.atacar(origenMock, destinoMock);
        });
    }
}
package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.arma.Artilleria;
import edu.fiuba.algo3.modelo.arma.Punio;
import edu.fiuba.algo3.modelo.terreno.Suelo;
import edu.fiuba.algo3.modelo.terreno.Terreno;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.unidad.Mech;
import edu.fiuba.algo3.modelo.unidad.Unidad;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PunioTest {

    // Test con Mocks en PunioTest:
    @Test
    public void test01PunioEnviaGolpeCuerpoACuerpoALaCeldaDestinoAdyacente() {
        Celda origenMock = mock(Celda.class);
        Celda destinoMock = mock(Celda.class);

        when(origenMock.esAdyacenteA(destinoMock)).thenReturn(true);
        when(origenMock.direccionHacia(destinoMock)).thenReturn(Direccion.ESTE);

        Arma punio = new Punio();
        punio.atacar(origenMock, destinoMock);

        verify(destinoMock, times(1)).recibirGolpeCuerpoACuerpo(Direccion.ESTE, 2);
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

    // Test con Casillas en ArtilleriaTest: perdon que lo puse aca despues lo cambio pero queria probarlo sin borrar los otros
    @Test
    public void test02ArtilleriaAtacaCasillaDestinoSeleccionada() {
        Casilla origen = new Casilla(new Suelo());
        Casilla intermedia = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, intermedia);
        intermedia.conectarVecina(Direccion.ESTE, destino);

        Unidad objetivoMock = mock(Unidad.class);
        destino.colocarUnidad(objetivoMock);

        Mech mech = new Mech(new Artilleria(), 2);
        mech.atacar(origen, destino);

        verify(objetivoMock, times(1)).recibirDanio(1);
    }
}
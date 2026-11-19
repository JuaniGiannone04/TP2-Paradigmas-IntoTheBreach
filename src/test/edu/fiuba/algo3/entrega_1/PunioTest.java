package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.arma.Punio;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
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
}
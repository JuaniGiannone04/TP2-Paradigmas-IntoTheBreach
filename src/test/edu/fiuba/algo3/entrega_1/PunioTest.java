package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.arma.Punio;
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

    @Test
    public void test01PunioEnviaGolpeCuerpoACuerpoALaCeldaVecina() {
        Celda origenMock = mock(Celda.class);
        Celda vecinaMock = mock(Celda.class);
        when(origenMock.vecinaAl(Direccion.ESTE)).thenReturn(vecinaMock);

        Arma punio = new Punio();
        punio.atacar(origenMock, Direccion.ESTE, 1);

        verify(vecinaMock, times(1)).recibirGolpeCuerpoACuerpo(Direccion.ESTE, 2);
    }

    @Test
    public void test02PunioCausa2DeDanioAUnidadAdyacenteYLaEmpujaUnaCasilla() {
        Terreno sueloMock = mock(Terreno.class);
        when(sueloMock.recibirDanio(anyInt())).thenReturn(sueloMock);

        Casilla origen = new Casilla(sueloMock);
        Casilla objetivo = new Casilla(sueloMock);
        Casilla destinoEmpuje = new Casilla(sueloMock);

        origen.conectarVecina(Direccion.ESTE, objetivo);
        objetivo.conectarVecina(Direccion.ESTE, destinoEmpuje);

        Unidad unidadObjetivoMock = mock(Unidad.class);
        objetivo.colocarUnidad(unidadObjetivoMock);

        Mech mech = new Mech(new Punio(), 3);
        mech.atacar(origen, Direccion.ESTE);

        verify(unidadObjetivoMock, times(1)).recibirDanio(2);
        assertFalse(objetivo.estaOcupada());
        assertSame(unidadObjetivoMock, destinoEmpuje.getUnidad());
    }

    @Test
    public void test03PunioEmpujaUnidadContraObstaculoYAmbosRecibenDanioDeColision() {
        Terreno sueloMock = mock(Terreno.class);
        when(sueloMock.recibirDanio(anyInt())).thenReturn(sueloMock);

        Terreno obstaculoMock = mock(Terreno.class);
        when(obstaculoMock.bloqueaDisparoDirecto()).thenReturn(true);
        when(obstaculoMock.recibirDanio(anyInt())).thenReturn(obstaculoMock);

        Casilla origen = new Casilla(sueloMock);
        Casilla objetivo = new Casilla(sueloMock);
        Casilla casillaObstaculo = new Casilla(obstaculoMock);

        origen.conectarVecina(Direccion.NORTE, objetivo);
        objetivo.conectarVecina(Direccion.NORTE, casillaObstaculo);

        Unidad unidadObjetivoMock = mock(Unidad.class);
        objetivo.colocarUnidad(unidadObjetivoMock);

        Mech mech = new Mech(new Punio(), 3);
        mech.atacar(origen, Direccion.NORTE);

        verify(unidadObjetivoMock, times(1)).recibirDanio(2);
        verify(unidadObjetivoMock, times(1)).recibirDanio(1);
        verify(obstaculoMock, times(1)).recibirDanio(1);
        assertSame(unidadObjetivoMock, objetivo.getUnidad());
    }

    @Test
    public void test04PunioLanzaExcepcionSiLaDistanciaNoEsUno() {
        Celda origenMock = mock(Celda.class);
        Arma punio = new Punio();

        assertThrows(IllegalArgumentException.class, () -> {
            punio.atacar(origenMock, Direccion.SUR, 2);
        });
    }
}
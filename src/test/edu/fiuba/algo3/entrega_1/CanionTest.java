package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.arma.Canion;
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

public class CanionTest {

    @Test
    public void test01CanionDisparaProyectilRectoALaCeldaVecina() {
        Celda origenMock = mock(Celda.class);
        Celda vecinaMock = mock(Celda.class);

        when(origenMock.vecinaAl(Direccion.ESTE))
                .thenReturn(vecinaMock);

        when(origenMock.direccionHacia(vecinaMock))
                .thenReturn(Direccion.ESTE);

        Arma canion = new Canion();

        canion.atacar(origenMock, vecinaMock);

        verify(vecinaMock, times(1))
                .recibirProyectilRecto(Direccion.ESTE, 1);
    }

    @Test
    public void test02CanionImpactaALaPrimeraUnidadEnLineaRectaYNoALaQueEstaDetras() {
        Terreno sueloMock = mock(Terreno.class);
        when(sueloMock.recibirDanio(anyInt())).thenReturn(sueloMock);

        Casilla origen = new Casilla(sueloMock);
        Casilla intermedia = new Casilla(sueloMock);
        Casilla primeraOcupada = new Casilla(sueloMock);
        Casilla empuje = new Casilla(sueloMock);
        Casilla segundaOcupada = new Casilla(sueloMock);

        origen.conectarVecina(Direccion.ESTE, intermedia);
        intermedia.conectarVecina(Direccion.ESTE, primeraOcupada);
        primeraOcupada.conectarVecina(Direccion.ESTE, empuje);
        empuje.conectarVecina(Direccion.ESTE, segundaOcupada);

        Unidad primeraUnidadMock = mock(Unidad.class);
        Unidad segundaUnidadMock = mock(Unidad.class);

        when(primeraUnidadMock.getVidaActual()).thenReturn(3);
        when(segundaUnidadMock.getVidaActual()).thenReturn(3);

        primeraOcupada.colocarUnidad(primeraUnidadMock);
        segundaOcupada.colocarUnidad(segundaUnidadMock);

        Mech mech = new Mech(new Canion(), 3);
        mech.atacar(origen, primeraOcupada);

        verify(primeraUnidadMock, times(1)).recibirDanio(1);

        assertSame(primeraUnidadMock, empuje.getUnidad());
        assertFalse(primeraOcupada.estaOcupada());

        verify(segundaUnidadMock, never()).recibirDanio(anyInt());
        assertSame(segundaUnidadMock, segundaOcupada.getUnidad());
    }

    @Test
    public void test03CanionImpactaAlPrimerObstaculoYNoDaniaUnidadDetras() {
        Terreno sueloMock = mock(Terreno.class);
        when(sueloMock.recibirDanio(anyInt())).thenReturn(sueloMock);

        Terreno obstaculoMock = mock(Terreno.class);
        when(obstaculoMock.bloqueaDisparoDirecto()).thenReturn(true);
        when(obstaculoMock.recibirDanio(anyInt())).thenReturn(obstaculoMock);

        Casilla origen = new Casilla(sueloMock);
        Casilla casillaObstaculo = new Casilla(obstaculoMock);
        Casilla casillaDetras = new Casilla(sueloMock);

        origen.conectarVecina(Direccion.SUR, casillaObstaculo);
        casillaObstaculo.conectarVecina(Direccion.SUR, casillaDetras);

        Unidad unidadDetrasMock = mock(Unidad.class);
        when(unidadDetrasMock.getVidaActual()).thenReturn(3);

        casillaDetras.colocarUnidad(unidadDetrasMock);

        Mech mech = new Mech(new Canion(), 3);
        mech.atacar(origen, casillaDetras);

        verify(obstaculoMock, times(1)).recibirDanio(1);
        verify(unidadDetrasMock, never()).recibirDanio(anyInt());

        assertSame(unidadDetrasMock, casillaDetras.getUnidad());
    }
    @Test
    public void test04CanionDisparandoAlVacioSeAbsorbeEnElBordeDelMapa() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.OESTE, destino);
        destino.conectarVecina(Direccion.ESTE, origen);

        Mech mech = new Mech(new Canion(), 3);

        assertDoesNotThrow(() -> mech.atacar(origen, destino));
    }
}
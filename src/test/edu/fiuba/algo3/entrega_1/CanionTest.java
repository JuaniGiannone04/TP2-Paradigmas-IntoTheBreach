package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.arma.Canion;
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
        when(origenMock.vecinaAl(Direccion.ESTE)).thenReturn(vecinaMock);

        Arma canion = new Canion();
        canion.atacar(origenMock, Direccion.ESTE, 1);

        verify(vecinaMock, times(1)).recibirProyectilRecto(Direccion.ESTE, 1);
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
        primeraOcupada.colocarUnidad(primeraUnidadMock);
        segundaOcupada.colocarUnidad(segundaUnidadMock);

        Mech mech = new Mech(new Canion(), 3);
        mech.atacar(origen, Direccion.ESTE);

        verify(primeraUnidadMock, times(1)).recibirDanio(1);
        assertSame(primeraUnidadMock, empuje.getUnidad());
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
        casillaDetras.colocarUnidad(unidadDetrasMock);

        Mech mech = new Mech(new Canion(), 3);
        mech.atacar(origen, Direccion.SUR);

        verify(obstaculoMock, times(1)).recibirDanio(1);
        verify(unidadDetrasMock, never()).recibirDanio(anyInt());
    }

    @Test
    public void test04CanionDisparandoAlVacioSeAbsorbeEnElBordeDelMapa() {
        Terreno sueloMock = mock(Terreno.class);
        Casilla origen = new Casilla(sueloMock);
        Casilla borde = new Casilla(sueloMock);
        origen.conectarVecina(Direccion.OESTE, borde);

        Mech mech = new Mech(new Canion(), 3);

        assertDoesNotThrow(() -> mech.atacar(origen, Direccion.OESTE));
    }
}
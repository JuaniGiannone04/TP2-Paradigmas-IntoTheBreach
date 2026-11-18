package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.arma.Artilleria;
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

public class ArtilleriaTest {

    @Test
    public void test01ArtilleriaLanzaProyectilEnArcoConPasosRestantes() {
        Celda origenMock = mock(Celda.class);
        Celda vecinaMock = mock(Celda.class);

        when(origenMock.direccionHacia(vecinaMock))
                .thenReturn(Direccion.NORTE);

        when(origenMock.vecinaAl(Direccion.NORTE))
                .thenReturn(vecinaMock);

        Arma artilleria = new Artilleria();

        artilleria.atacar(origenMock, vecinaMock);

        verify(vecinaMock, times(1))
                .recibirProyectilEnArco(vecinaMock, Direccion.NORTE, 1);
    }

    @Test
    public void test02ArtilleriaNoPuedeAtacarCasillasAdyacentes() {
        Celda origenMock = mock(Celda.class);
        Celda destinoMock = mock(Celda.class);

        Arma artilleria = new Artilleria();

        when(origenMock.esAdyacenteA(destinoMock))
                .thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> artilleria.atacar(origenMock, destinoMock));
    }


    @Test
    public void test03ArtilleriaAtacaPorEncimaDeObstaculosSinAfectarlos() {
        Terreno sueloMock = mock(Terreno.class);
        when(sueloMock.recibirDanio(anyInt())).thenReturn(sueloMock);

        Terreno obstaculoMock = mock(Terreno.class);
        when(obstaculoMock.bloqueaDisparoDirecto()).thenReturn(true);

        Casilla origen = new Casilla(sueloMock);
        Casilla casillaObstaculo = new Casilla(obstaculoMock);
        Casilla objetivo = new Casilla(sueloMock);

        origen.conectarVecina(Direccion.ESTE, casillaObstaculo);
        casillaObstaculo.conectarVecina(Direccion.ESTE, objetivo);

        Unidad unidadObjetivoMock = mock(Unidad.class);
        when(unidadObjetivoMock.getVidaActual()).thenReturn(3);
        objetivo.colocarUnidad(unidadObjetivoMock);

        Mech mech = new Mech(new Artilleria(), 2);
        mech.atacar(origen, objetivo);

        verify(obstaculoMock, never()).recibirDanio(anyInt());
        verify(unidadObjetivoMock, times(1)).recibirDanio(1);
    }


    @Test
    public void test04ArtilleriaDaniaCentroYEmpujaHaciaAfueraLasCuatroAdyacentes() {
        Terreno sueloMock = mock(Terreno.class);
        when(sueloMock.recibirDanio(anyInt())).thenReturn(sueloMock);

        Casilla origen = new Casilla(sueloMock);
        Casilla puente = new Casilla(sueloMock);
        Casilla centro = new Casilla(sueloMock);

        Casilla norte = new Casilla(sueloMock);
        Casilla norteEmpuje = new Casilla(sueloMock);
        Casilla sur = new Casilla(sueloMock);
        Casilla surEmpuje = new Casilla(sueloMock);
        Casilla este = new Casilla(sueloMock);
        Casilla esteEmpuje = new Casilla(sueloMock);
        Casilla oeste = new Casilla(sueloMock);
        Casilla oesteEmpuje = new Casilla(sueloMock);

        origen.conectarVecina(Direccion.ESTE, puente);
        puente.conectarVecina(Direccion.ESTE, centro);

        centro.conectarVecina(Direccion.NORTE, norte);
        norte.conectarVecina(Direccion.NORTE, norteEmpuje);

        centro.conectarVecina(Direccion.SUR, sur);
        sur.conectarVecina(Direccion.SUR, surEmpuje);

        centro.conectarVecina(Direccion.ESTE, este);
        este.conectarVecina(Direccion.ESTE, esteEmpuje);

        centro.conectarVecina(Direccion.OESTE, oeste);
        oeste.conectarVecina(Direccion.OESTE, oesteEmpuje);

        Unidad unidadCentroMock = mock(Unidad.class);
        when(unidadCentroMock.getVidaActual()).thenReturn(3);
        Unidad unidadNorteMock = mock(Unidad.class);
        when(unidadNorteMock.getVidaActual()).thenReturn(3);
        Unidad unidadSurMock = mock(Unidad.class);
        when(unidadSurMock.getVidaActual()).thenReturn(3);
        Unidad unidadEsteMock = mock(Unidad.class);
        when(unidadEsteMock.getVidaActual()).thenReturn(3);
        Unidad unidadOesteMock = mock(Unidad.class);
        when(unidadOesteMock.getVidaActual()).thenReturn(3);

        centro.colocarUnidad(unidadCentroMock);
        norte.colocarUnidad(unidadNorteMock);
        sur.colocarUnidad(unidadSurMock);
        este.colocarUnidad(unidadEsteMock);
        oeste.colocarUnidad(unidadOesteMock);

        Mech mech = new Mech(new Artilleria(), 2);
        mech.atacar(origen, centro);

        verify(unidadCentroMock, times(1)).recibirDanio(1);
        assertSame(unidadCentroMock, centro.getUnidad());

        verify(unidadNorteMock, never()).recibirDanio(anyInt());
        assertSame(unidadNorteMock, norteEmpuje.getUnidad());

        verify(unidadSurMock, never()).recibirDanio(anyInt());
        assertSame(unidadSurMock, surEmpuje.getUnidad());

        verify(unidadEsteMock, never()).recibirDanio(anyInt());
        assertSame(unidadEsteMock, esteEmpuje.getUnidad());

        verify(unidadOesteMock, never()).recibirDanio(anyInt());
        assertSame(unidadOesteMock, oesteEmpuje.getUnidad());
    }


    @Test
    public void test05ArtilleriaAtacaCasillaDestinoSeleccionada() {
        Casilla origen = new Casilla(new Suelo());
        Casilla intermedia = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, intermedia);
        intermedia.conectarVecina(Direccion.ESTE, destino);

        Unidad objetivoMock = mock(Unidad.class);
        when(objetivoMock.getVidaActual()).thenReturn(3);
        destino.colocarUnidad(objetivoMock);

        Mech mech = new Mech(new Artilleria(), 2);
        mech.atacar(origen, destino);

        verify(objetivoMock, times(1)).recibirDanio(1);
    }
}
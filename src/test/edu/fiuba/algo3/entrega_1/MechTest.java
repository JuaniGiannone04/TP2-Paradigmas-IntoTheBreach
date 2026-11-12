package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.arma.*;
import edu.fiuba.algo3.modelo.unidad.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MechTest {

    @Test
    public void test01MechCombateGolpeaCasillaAdyacente() {
        Arma punio = new Punio();
        Mech mech = new Mech(punio, 3);
        Vek vek = new Saltador();

        Tablero tablero = new Tablero();

        Posicion origen = new Posicion(2, 2);
        Posicion destino = new Posicion(2, 3);

        tablero.colocarUnidad(origen, mech);
        tablero.colocarUnidad(destino, vek);

        int vidaInicial = vek.getVidaActual();

        mech.atacar(origen, destino, tablero);

        assertTrue(vek.getVidaActual() < vidaInicial);
    }

    @Test
    public void test02MechCombateGolpea2DeDaño() {
        Arma punio = new Punio();
        Mech mech = new Mech(punio, 3);
        Vek vek = new Saltador();

        Tablero tablero = new Tablero();

        Posicion origen = new Posicion(2, 2);
        Posicion destino = new Posicion(2, 3);

        tablero.colocarUnidad(origen, mech);
        tablero.colocarUnidad(destino, vek);

        int vidaInicial = vek.getVidaActual();

        mech.atacar(origen, destino, tablero);

        assertEquals(vidaInicial - 2, vek.getVidaActual());
    }

    @Test
    public void test03MechCombateEmpuja1CasillaEnDireccionDelGolpe(){
        Arma punio = new Punio();
        Mech mech = new Mech(punio, 3);
        Vek vek = new Luciernaga();

        Tablero tablero = new Tablero();

        Posicion origen = new Posicion(2, 2);
        Posicion destino = new Posicion(2, 3);
        Posicion posicionEsperada = new Posicion (2, 4);

        tablero.colocarUnidad(origen, mech);
        tablero.colocarUnidad(destino, vek);

        mech.atacar(origen, destino, tablero);

        assertSame(vek, tablero.obtenerCasilla(posicionEsperada).getUnidad()); //Comprueba que sean el mismo objeto
        // el Vek y la casilla donde deberia terminar
        assertFalse(tablero.obtenerCasilla(destino).estaOcupada()); //Comprueba que la celda quedo vacia
    }


    @Test
    public void test04MechCañonGolpeaLineaRecta(){
        Arma canion = new Canion();
        Mech mech = new Mech(canion, 3);
        Vek vek = new Saltador();

        Tablero tablero = new Tablero();

        Posicion origen = new Posicion(2, 2);
        Posicion destino = new Posicion(2, 5);

        tablero.colocarUnidad(origen, mech);
        tablero.colocarUnidad(destino, vek);

        int vidaInicial = vek.getVidaActual();

        mech.atacar(origen, destino, tablero);

        assertTrue(vek.getVidaActual() < vidaInicial);
    }


    @Test
    public void test05MechCañonGolpea1DeDaño(){
        Arma canion = new Canion();
        Mech mech = new Mech(canion, 3);
        Vek vek = new Saltador();

        Tablero tablero = new Tablero();

        Posicion origen = new Posicion(2, 2);
        Posicion destino = new Posicion(2, 5);

        tablero.colocarUnidad(origen, mech);
        tablero.colocarUnidad(destino, vek);

        int vidaInicial = vek.getVidaActual();

        mech.atacar(origen, destino, tablero);

        assertEquals(vidaInicial - 1, vek.getVidaActual());
    }


    @Test
    public void test06MechCañonEmpujaUnaCasilla(){
        Arma canion = new Canion();
        Mech mech = new Mech(canion, 3);
        Vek vek = new Luciernaga();

        Tablero tablero = new Tablero();

        Posicion origen = new Posicion(2, 2);
        Posicion destino = new Posicion(2, 5);
        Posicion posicionEsperada = new Posicion (2, 6);

        tablero.colocarUnidad(origen, mech);
        tablero.colocarUnidad(destino, vek);

        mech.atacar(origen, destino, tablero);

        assertSame(vek, tablero.obtenerCasilla(posicionEsperada).getUnidad()); //Comprueba que sean el mismo objeto
        // el Vek y la casilla donde deberia terminar
        assertFalse(tablero.obtenerCasilla(destino).estaOcupada()); //Comprueba que la celda quedo vacia
    }


    @Test
    public void test07MechArtilleroDisparaEnArco(){
        Arma artilleria = new Artilleria();
        Mech mech = new Mech(artilleria, 2);
        Vek vek = new Luciernaga();

        Tablero tablero = new Tablero();

        Posicion origen = new Posicion(2, 2);
        Posicion destino = new Posicion(2, 5);

        tablero.colocarUnidad(origen, mech);
        tablero.colocarUnidad(destino, vek);

        int vidaInicial = vek.getVidaActual();

        mech.atacar(origen, destino, tablero);

        assertEquals(vidaInicial - 1, vek.getVidaActual());
    }



    @Test
    public void test08MechArtilleroGolpea1DeDaño(){
        Arma artilleria = new Artilleria();
        Mech mech = new Mech(artilleria, 2);
        Vek vek = new Saltador();

        Tablero tablero = new Tablero();

        Posicion origen = new Posicion(2, 2);
        Posicion destino = new Posicion(2, 5);

        tablero.colocarUnidad(origen, mech);
        tablero.colocarUnidad(destino, vek);

        int vidaInicial = vek.getVidaActual();

        mech.atacar(origen, destino, tablero);

        assertEquals(vidaInicial - 1, vek.getVidaActual());
    }


    @Test
    public void test09MechArtilleroEmpujaHaciaAfueraCasillaAdyacente(){
        Arma artilleria = new Artilleria();
        Mech mech = new Mech(artilleria, 2);
        Vek vek = new Luciernaga();

        Tablero tablero = new Tablero();

        Posicion origen = new Posicion(3, 0);
        Posicion impacto = new Posicion(3, 3);
        Posicion posicionVek = new Posicion(3, 4);
        Posicion posicionEsperada = new Posicion(3, 5);

        tablero.colocarUnidad(origen, mech);
        tablero.colocarUnidad(posicionVek, vek);

        mech.atacar(origen, impacto, tablero);

        assertSame(vek, tablero.obtenerCasilla(posicionEsperada).getUnidad());
        assertFalse(tablero.obtenerCasilla(posicionVek).estaOcupada());
    }


    @Test
    public void test10MechContaminadoRecibeDobleDeDanioYNoSeAcumula() {
        Arma punio = new Punio();
        Mech mech = new Mech(punio, 3);

        int vidaInicial = mech.getVidaActual();

        mech.aplicarEfectoAcido();
        mech.aplicarEfectoAcido();

        mech.recibirDanio(1);

        assertEquals(vidaInicial - 2, mech.getVidaActual());
    }


//    @test
//    public void test11MechSoloPuedeMoverseACasillasDentroDeSuRangoDeMovimiento
//
//    @test
//    public void test12MechSoloPuedeMoverseYAtacarUnaVez
//
@Test
public void test13MechPuedeDeshacerSuMovimientoSiNoAtacoYVolverAlaPosicionAnterior() {
    Tablero tablero = new Tablero();
    Arma arma = mock(Arma.class);
    Mech mech = new Mech(arma, 3);
    Posicion origen = new Posicion(2, 2);
    Posicion destino = new Posicion(2, 3);
    tablero.colocarUnidad(origen, mech);

    mech.mover(origen, destino, tablero);
    mech.deshacerMovimiento(tablero);

    assertEquals(mech, tablero.obtenerCasilla(origen).getUnidad());
    assertFalse(tablero.obtenerCasilla(destino).estaOcupada());
}

    @Test
    public void test14MechNoPuedeDeshacerSuMovimientoSiYaAtaco() {
        Tablero tablero = new Tablero();
        Arma arma = mock(Arma.class);
        Mech mech = new Mech(arma, 3);
        Posicion origen = new Posicion(2, 2);
        Posicion destino = new Posicion(2, 3);
        tablero.colocarUnidad(origen, mech);

        mech.mover(origen, destino, tablero);
        mech.atacar(destino, new Posicion(2, 4), tablero);

        assertThrows(IllegalStateException.class, () -> mech.deshacerMovimiento(tablero));
        assertEquals(mech, tablero.obtenerCasilla(destino).getUnidad());
    }

    @Test
    public void test16MechNoPuedeAtacarDosVecesEnElMismoTurno(){
        Tablero tablero = new Tablero();
        Arma arma = mock(Arma.class);
        Mech mech = new Mech(arma, 3);

        Posicion origen = new Posicion(1, 1);
        Posicion destino = new Posicion(1, 2);

        mech.atacar(origen, destino, tablero);

        assertThrows(IllegalStateException.class,
                () -> mech.atacar(origen, destino, tablero));

        verify(arma, times(1)).atacar(origen, destino, tablero);
    }

    @Test
    public void test17MechNoSePuedeMoverDosVecesEnElMismoTurno(){
        Tablero tablero = mock(Tablero.class);
        Arma arma = mock(Arma.class);
        Mech mech = new Mech(arma, 3);

        Posicion origen = new Posicion(1, 1);
        Posicion intermedia = new Posicion(1, 2);
        Posicion destino = new Posicion(1, 3);

        mech.mover(origen, intermedia, tablero);

        assertThrows(IllegalStateException.class,
                () -> mech.mover(intermedia, destino, tablero));

        verify(tablero, times(1)).moverUnidad(origen, intermedia);
        verify(tablero, never()).moverUnidad(intermedia, destino);
    }

    @Test
    public void test18MechPuedeMoverseYAtacarEnElMismoTurno(){
        Tablero tablero = mock(Tablero.class);
        Arma arma = mock(Arma.class);
        Mech mech = new Mech(arma, 3);

        Posicion origen = new Posicion(1, 1);
        Posicion nuevaPosicion = new Posicion(1, 2);
        Posicion objetivo = new Posicion(1, 3);

        mech.mover(origen, nuevaPosicion, tablero);
        mech.atacar(nuevaPosicion, objetivo, tablero);

        verify(tablero, times(1)).moverUnidad(origen, nuevaPosicion);
        verify(arma, times(1)).atacar(nuevaPosicion, objetivo, tablero);
    }

    @Test
    public void test19UnMechNoPuedeMoverseDespuesDeAtacarEnUnMismoTurno(){
        Tablero tablero = mock(Tablero.class);
        Arma arma = mock(Arma.class);
        Mech mech = new Mech(arma, 3);

        Posicion origen = new Posicion(1, 1);
        Posicion objetivo = new Posicion(1, 2);
        Posicion destino = new Posicion(1, 3);

        mech.atacar(origen, objetivo, tablero);

        assertThrows(IllegalStateException.class,
                () -> mech.mover(objetivo, destino, tablero));

        verify(arma, times(1)).atacar(origen, objetivo, tablero);
        verify(tablero, never()).moverUnidad(objetivo, destino);
    }



}
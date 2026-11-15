package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;
import edu.fiuba.algo3.modelo.unidad.*;
import edu.fiuba.algo3.modelo.arma.*;
import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.arma.Punio;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MechTest {
    /*
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
//    @test
//    public void test13MechPuedeDeshacerSuMovimientoSiNoAtacoYVolverAlaPosicionAnterior
//
//    @test
//    public void test14MechNoPuedeDeshacerSuMovimientoSiYaAtaco
//
//    @test
//    public void test15MechPuedeDeshacerSuMovimientoUnaUnicaVezPorTurno
    */
    @Test
    public void test16MechNoPuedeAtacarDosVecesEnElMismoTurno(){
        Arma arma = mock(Arma.class);
        Celda origen = mock(Celda.class);
        Mech mech = new Mech(arma, 3);

        mech.atacar(origen, Direccion.ESTE, 1);

        assertThrows(IllegalStateException.class,
                () -> mech.atacar(origen, Direccion.ESTE, 1));

        verify(arma, times(1)).atacar(origen, Direccion.ESTE, 1);
    }

    @Test
    public void test17MechNoSePuedeMoverDosVecesEnElMismoTurno(){
        Arma arma = mock(Arma.class);
        Casilla origen = mock(Casilla.class);
        Casilla intermedia = mock(Casilla.class);
        Casilla destino = mock(Casilla.class);

        Mech mech = new Mech(arma, 3);


        mech.mover(origen, intermedia);

        assertThrows(IllegalStateException.class,
                () -> mech.mover(intermedia, destino));

        verify(origen, times(1)).moverUnidadHacia(intermedia);
        verify(intermedia, never()).moverUnidadHacia(destino);
    }

    @Test
    public void test18MechPuedeAtacarDespuesDeMoverseEnElMismoTurno(){
        Arma arma = mock(Arma.class);
        Mech mech = new Mech(arma, 3);
        Casilla origen = mock(Casilla.class);
        Casilla destino = mock(Casilla.class);

        mech.mover(origen, destino);
        mech.atacar(destino, Direccion.ESTE, 1);

        verify(origen, times(1)).moverUnidadHacia(destino);
        verify(arma, times(1)).atacar(destino, Direccion.ESTE, 1);
    }

    @Test
    public void test19UnMechNoPuedeMoverseDespuesDeAtacarEnElMismoTurno(){
        Arma arma = mock(Arma.class);
        Mech mech = new Mech(arma, 3);
        Casilla origen = mock(Casilla.class);
        Casilla destino = mock(Casilla.class);

        mech.atacar(origen, Direccion.ESTE, 1);

        assertThrows(IllegalStateException.class,
                () -> mech.mover(origen, destino));

        verify(arma, times(1)).atacar(origen, Direccion.ESTE, 1);
        verify(origen, never()).moverUnidadHacia(destino);
    }



}
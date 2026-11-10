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
        Mech mech = new Mech(punio);
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
        Mech mech = new Mech(punio);
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
        Mech mech = new Mech(punio);
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
        Mech mech = new Mech(canion);
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
        Mech mech = new Mech(canion);
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
        Mech mech = new Mech(canion);
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

    }



//    @test
//    public void test08MechArtilleroGolpea1DeDaño
//
//    @test
//    public void test09MechArtilleroEmpujaHaciaAfueraCasillasAdyacentes
//
//    @test
//    public void test10MechContaminadoRecibeDobleDeDañoYNoSeAcumula
//
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


}
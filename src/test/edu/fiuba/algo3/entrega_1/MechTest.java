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

//
//    @test
//    public void test02MechCombateGolpea2DeDaño
//
//    @test
//    public void test03MechCombateEmpuja1Casilla
//
//    @test
//    public void test04MechCañonGolpeaLineaRecta
//
//    @test
//    public void test05MechCañonGolpea1DeDaño
//
//    @test
//    public void test06MechCañonEmpujaUnaCasilla
//
//    @test
//    public void test07MechArtilleroDisparaEnLineaRecta
//
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
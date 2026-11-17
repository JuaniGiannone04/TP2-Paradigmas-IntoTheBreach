package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.terreno.Agua;
import edu.fiuba.algo3.modelo.terreno.Montana;
import edu.fiuba.algo3.modelo.terreno.Suelo;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.unidad.*;
import edu.fiuba.algo3.modelo.arma.*;
import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.arma.Punio;
import edu.fiuba.algo3.modelo.unidad.estadosalud.Contaminado;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MechTest {

    @Test
    public void test01MechCombateGolpeaCasillaAdyacente() {
        Arma punio = new Punio();
        Mech mech = new Mech(punio, 3);
        Vek vek = new Saltador();

        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, destino);

        origen.colocarUnidad(mech);
        destino.colocarUnidad(vek);

        int vidaInicial = vek.getVidaActual();

        mech.atacar(origen, destino);

        assertTrue(vek.getVidaActual() < vidaInicial);
    }


    @Test
    public void test02MechCombateGolpea2DeDaño() {
        Arma punio = new Punio();
        Mech mech = new Mech(punio, 3);
        Vek vek = new Saltador();

        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, destino);

        origen.colocarUnidad(mech);
        destino.colocarUnidad(vek);

        int vidaInicial = vek.getVidaActual();

        mech.atacar(origen, destino);

        assertEquals(vidaInicial - 2, vek.getVidaActual());
    }

    @Test
    public void test03MechCombateEmpuja1Casilla() {
    Arma punio = new Punio();
    Mech mech = new Mech(punio, 3);
    Vek vek = new Luciernaga();

    Casilla origen = new Casilla(new Suelo());
    Casilla destino = new Casilla(new Suelo());
    Casilla siguiente = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, destino);
        destino.conectarVecina(Direccion.OESTE, origen);

        destino.conectarVecina(Direccion.ESTE, siguiente);
        siguiente.conectarVecina(Direccion.OESTE, destino);

    origen.colocarUnidad(mech);
    destino.colocarUnidad(vek);

    mech.atacar(origen, destino);

    assertSame(vek, siguiente.getUnidad());
    assertFalse(destino.estaOcupada()); //Comprueba que la celda quedo vacia
    }


    @Test
    public void test04MechCanionGolpeaLineaRecta() {
        Arma canion = new Canion();
        Mech mech = new Mech(canion, 3);
        Vek vek = new Saltador();

        Casilla origen = new Casilla(new Suelo());
        Casilla intermedia = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, intermedia);
        intermedia.conectarVecina(Direccion.ESTE, destino);

        origen.colocarUnidad(mech);
        destino.colocarUnidad(vek);

        int vidaInicial = vek.getVidaActual();

        mech.atacar(origen, destino);

        assertTrue(vek.getVidaActual() < vidaInicial);
    }



    @Test
    public void test05MechCanionGolpea1DeDaño(){
        Arma canion = new Canion();
        Mech mech = new Mech(canion, 3);
        Vek vek = new Saltador();

        Casilla origen = new Casilla(new Suelo());
        Casilla intermedia = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, intermedia);
        intermedia.conectarVecina(Direccion.ESTE, destino);

        origen.colocarUnidad(mech);
        destino.colocarUnidad(vek);

        int vidaInicial = vek.getVidaActual();

        mech.atacar(origen, destino);

        assertEquals(vidaInicial - 1, vek.getVidaActual());
    }


    @Test
    public void test06MechCanionEmpujaUnaCasilla() {
        Arma canion = new Canion();
        Mech mech = new Mech(canion, 3);
        Vek vek = new Luciernaga();

        Casilla origen = new Casilla(new Suelo());
        Casilla intermedia = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        Casilla siguiente = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, intermedia);
        intermedia.conectarVecina(Direccion.OESTE, origen);

        intermedia.conectarVecina(Direccion.ESTE, destino);
        destino.conectarVecina(Direccion.OESTE, intermedia);

        destino.conectarVecina(Direccion.ESTE, siguiente);
        siguiente.conectarVecina(Direccion.OESTE, destino);

        origen.colocarUnidad(mech);
        destino.colocarUnidad(vek);

        mech.atacar(origen, destino);

        assertSame(vek, siguiente.getUnidad());
        assertFalse(destino.estaOcupada());
    }


    @Test
    public void test07MechArtilleroDisparaEnArco(){
        Arma artilleria = new Artilleria();
        Mech mech = new Mech(artilleria, 2);
        Vek vek = new Luciernaga();

        Casilla origen = new Casilla(new Suelo());
        Casilla intermedia = new Casilla(new Montana());
        Casilla destino = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, intermedia);
        intermedia.conectarVecina(Direccion.ESTE, destino);

        origen.colocarUnidad(mech);
        destino.colocarUnidad(vek);

        int vidaInicial = vek.getVidaActual();

        mech.atacar(origen, destino);

        assertTrue(vek.getVidaActual() < vidaInicial);
    }



    @Test
    public void test08MechArtilleroGolpea1DeDaño(){
        Arma artilleria = new Artilleria();
        Mech mech = new Mech(artilleria, 2);
        Vek vek = new Saltador();

        Casilla origen = new Casilla(new Suelo());
        Casilla intermedia = new Casilla(new Montana());
        Casilla destino = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, intermedia);
        intermedia.conectarVecina(Direccion.ESTE, destino);

        origen.colocarUnidad(mech);
        destino.colocarUnidad(vek);

        int vidaInicial = vek.getVidaActual();

        mech.atacar(origen, destino);

        assertEquals(vidaInicial - 1, vek.getVidaActual());
    }


    @Test
    public void test09MechArtilleroEmpujaHaciaAfueraCasillaAdyacente() {
        Arma artilleria = new Artilleria();
        Mech mech = new Mech(artilleria, 2);
        Vek vek = new Luciernaga();

        Casilla origen = new Casilla(new Suelo());
        Casilla intermedia1 = new Casilla(new Suelo());
        Casilla intermedia2 = new Casilla(new Suelo());
        Casilla impacto = new Casilla(new Suelo());
        Casilla posicionVek = new Casilla(new Suelo());
        Casilla posicionEsperada = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, intermedia1);
        intermedia1.conectarVecina(Direccion.ESTE, intermedia2);
        intermedia2.conectarVecina(Direccion.ESTE, impacto);

        impacto.conectarVecina(Direccion.ESTE, posicionVek);
        posicionVek.conectarVecina(Direccion.ESTE, posicionEsperada);

        origen.colocarUnidad(mech);
        posicionVek.colocarUnidad(vek);

        mech.atacar(origen, impacto);

        assertSame(vek, posicionEsperada.getUnidad());
        assertFalse(posicionVek.estaOcupada());
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

    @Test
    public void test12MechSobreAguaNoPuedeAtacar() {
        Arma arma = mock(Arma.class);
        Mech mech = new Mech(arma, 3);

        Casilla origen = new Casilla(new Agua());
        Casilla destino = new Casilla(new Suelo());

        origen.colocarUnidad(mech);

        assertThrows(IllegalStateException.class,
                () -> mech.atacar(origen, destino));

        verify(arma, never()).atacar(origen, destino);
    }


    @Test
    public void test13MechSanoRecibeDanioNormal() {
        Mech mech = new Mech(mock(Arma.class), 3);

        mech.recibirDanio(1);

        assertEquals(2, mech.getVidaActual());
    }

    @Test
    public void test14MechSanoRecibeDanioNormal() {
        Mech mech = new Mech(mock(Arma.class), 3);

        mech.recibirDanio(1);

        assertEquals(2, mech.getVidaActual());
    }

    @Test
    public void test15MechContaminadoTieneEstadoContaminado() {
        Mech mech = new Mech(mock(Arma.class), 3);

        mech.aplicarEfectoAcido();

        assertTrue(mech.estaContaminado());
    }

    @Test
    public void test16MechContaminadoTieneEstadoDeSaludContaminado() {
        Mech mech = new Mech(mock(Arma.class), 3);

        mech.aplicarEfectoAcido();

        assertTrue(mech.obtenerEstadoSalud() instanceof Contaminado);
    }




}
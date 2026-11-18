package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.arma.Punio;
import edu.fiuba.algo3.modelo.terreno.Montana;
import edu.fiuba.algo3.modelo.terreno.Suelo;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.unidad.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class VekTest {

    @Test
    public void test01SaltadorAtacaCasillaAdyacenteYHaceDosDeDanio() {
        Saltador saltador = new Saltador();
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, destino);
        origen.colocarUnidad(saltador);

        Mech objetivo = new Mech(new Punio(), 3);
        destino.colocarUnidad(objetivo);

        int vidaInicialObjetivo = objetivo.getVidaActual();

        saltador.atacar(origen, destino);

        assertEquals(vidaInicialObjetivo - 2, objetivo.getVidaActual());
    }

    @Test
    public void test02LuciernagaHaceUnDanioALaUnidadDeDestino() {
        Luciernaga luciernaga = new Luciernaga();
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, destino);
        origen.colocarUnidad(luciernaga);

        Mech objetivo = new Mech(new Punio(), 3);
        destino.colocarUnidad(objetivo);

        int vidaInicialObjetivo = objetivo.getVidaActual();

        luciernaga.atacar(origen, destino);

        assertEquals(vidaInicialObjetivo - 1, objetivo.getVidaActual());
    }

    @Test
    public void test03LuciernagaContaminaAlObjetivoQueImpacta() {
        Luciernaga luciernaga = new Luciernaga();
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, destino);
        origen.colocarUnidad(luciernaga);

        Mech objetivo = new Mech(new Punio(), 3);
        destino.colocarUnidad(objetivo);

        luciernaga.atacar(origen, destino);

        assertTrue(objetivo.estaContaminado());
    }

    @Test
    public void test04AvisponHaceUnDanioAlObjetivoAdyacente() {
        Avispon avispon = new Avispon();
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, destino);
        origen.colocarUnidad(avispon);

        Mech objetivo = new Mech(new Punio(), 3);
        destino.colocarUnidad(objetivo);

        int vidaInicialObjetivo = objetivo.getVidaActual();

        avispon.atacar(origen, destino);

        assertEquals(vidaInicialObjetivo - 1, objetivo.getVidaActual());
    }

    @Test
    public void test05AvispónPuedeVolarSobreObstaculosYUnidades() {
        Avispon avispon = new Avispon();
        Casilla origen = new Casilla(new Suelo());
        Casilla intermedia = new Casilla(new Montana());
        Casilla destino = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, intermedia);
        intermedia.conectarVecina(Direccion.ESTE, destino);

        origen.colocarUnidad(avispon);

        avispon.moverse(origen, destino);

        assertSame(avispon, destino.getUnidad());
        assertFalse(origen.estaOcupada());
    }

    @Test
    public void test06AvispónNoLeAfectaAgua() {
        Avispon avispon = new Avispon();
        int vidaInicial = avispon.getVidaActual();
        avispon.aplicarEfectoAgua();
        assertEquals(vidaInicial, avispon.getVidaActual());
    }

    @Test
    public void test07AvispónNoLeAfectaAcido() {
        Avispon avispon = new Avispon();
        avispon.aplicarEfectoAcido();
        assertFalse(avispon.estaContaminado());
    }

    @Test
    public void test08SaltadorEsTerrestre() {
        Saltador saltador = new Saltador();

        assertTrue(saltador.puedeTransitar(new Suelo()));
        assertFalse(saltador.puedeTransitar(new Montana()));
    }

    @Test
    public void test09LuciernagaEsTerrestre() {
        Luciernaga luciernaga = new Luciernaga();

        assertTrue(luciernaga.puedeTransitar(new Suelo()));
        assertFalse(luciernaga.puedeTransitar(new Montana()));
    }
}
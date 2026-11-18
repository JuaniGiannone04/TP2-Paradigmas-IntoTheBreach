package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.RedElectrica;
import edu.fiuba.algo3.modelo.arma.Punio;
import edu.fiuba.algo3.modelo.terreno.Edificio;
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
    public void test05AvisponPuedeVolarSobreObstaculosYUnidades() {
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
    public void test06AvisponNoLeAfectaAgua() {
        Avispon avispon = new Avispon();
        int vidaInicial = avispon.getVidaActual();
        avispon.aplicarEfectoAgua();
        assertEquals(vidaInicial, avispon.getVidaActual());
    }

    @Test
    public void test07AvisponNoLeAfectaAcido() {
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

    @Test
    public void test10LuciernagaNoAtacaFueraDeLinea() {
        Luciernaga luciernaga = new Luciernaga();
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        Casilla otra = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, otra);
        origen.colocarUnidad(luciernaga);
        Mech objetivo = new Mech(new Punio(), 3);
        destino.colocarUnidad(objetivo);

        assertThrows(IllegalArgumentException.class, () -> luciernaga.atacar(origen, destino));
    }

    @Test
    public void test11AvisponNoPuedeTerminarSobreUnaCasillaOcupada() {
        Avispon avispon = new Avispon();
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        origen.colocarUnidad(avispon);
        destino.colocarUnidad(new Mech(new Punio(), 3));

        assertThrows(IllegalStateException.class, () -> avispon.moverse(origen, destino));
    }

    @Test
    public void test12VekSinObjetivoAdyacenteNoAnunciaAtaque() {
        Casilla vekCasilla = new Casilla(new Suelo());
        Casilla vacia = new Casilla(new Suelo());
        vekCasilla.conectarVecina(Direccion.ESTE, vacia);
        vacia.conectarVecina(Direccion.OESTE, vekCasilla);
        Vek vek = new Saltador();
        vekCasilla.colocarUnidad(vek);

        assertThrows(IllegalStateException.class, () -> vek.anunciaAtaque(vekCasilla));
    }

    @Test
    public void test13VekSinObjetivoSeMueveHaciaElEdificioMasCercano() {
        Casilla vekCasilla = new Casilla(new Suelo());
        Casilla intermedia = new Casilla(new Suelo());
        Casilla edificio = new Casilla(new Edificio(new RedElectrica(5)));
        vekCasilla.conectarVecina(Direccion.ESTE, intermedia);
        intermedia.conectarVecina(Direccion.OESTE, vekCasilla);
        intermedia.conectarVecina(Direccion.ESTE, edificio);
        edificio.conectarVecina(Direccion.OESTE, intermedia);

        Vek vek = new Saltador();
        vekCasilla.colocarUnidad(vek);

        vek.moverHaciaEdificio(vekCasilla);

        assertSame(vek, intermedia.getUnidad());
    }

    @Test
    public void test14VekSinObjetivoSeMueveHaciaEdificioAlOeste() {
        Casilla edificio = new Casilla(new Edificio(new RedElectrica(5)));
        Casilla intermedia = new Casilla(new Suelo());
        Casilla vekCasilla = new Casilla(new Suelo());
        edificio.conectarVecina(Direccion.ESTE, intermedia);
        intermedia.conectarVecina(Direccion.OESTE, edificio);
        intermedia.conectarVecina(Direccion.ESTE, vekCasilla);
        vekCasilla.conectarVecina(Direccion.OESTE, intermedia);

        Vek vek = new Saltador();
        vekCasilla.colocarUnidad(vek);

        vek.moverHaciaEdificio(vekCasilla);

        assertSame(vek, intermedia.getUnidad());
    }
}
package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.RedElectrica;
import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.arma.Punio;
import edu.fiuba.algo3.modelo.terreno.Acido;
import edu.fiuba.algo3.modelo.terreno.Agua;
import edu.fiuba.algo3.modelo.terreno.Edificio;
import edu.fiuba.algo3.modelo.terreno.Montana;
import edu.fiuba.algo3.modelo.terreno.Suelo;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.unidad.Mech;
import edu.fiuba.algo3.modelo.unidad.Saltador;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class MovimientoMechTest {

    private void conectarHorizontal(Casilla oeste, Casilla este) {
        oeste.conectarVecina(Direccion.ESTE, este);
        este.conectarVecina(Direccion.OESTE, oeste);
    }

    private void conectarVertical(Casilla norte, Casilla sur) {
        norte.conectarVecina(Direccion.SUR, sur);
        sur.conectarVecina(Direccion.NORTE, norte);
    }

    @Test
    public void test01MoverUnPasoDevuelveLaCasillaDestino() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        conectarHorizontal(origen, destino);
        Mech mech = new Mech(new Punio(), 3);
        origen.colocarUnidad(mech);

        Casilla resultado = origen.moverUnidadPor(List.of(Direccion.ESTE));

        assertSame(destino, resultado);
    }

    @Test
    public void test02MoverUnPasoUbicaAlMechEnLaCasillaDestino() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        conectarHorizontal(origen, destino);
        Mech mech = new Mech(new Punio(), 3);
        origen.colocarUnidad(mech);

        origen.moverUnidadPor(List.of(Direccion.ESTE));

        assertTrue(destino.contieneUnidad(mech));
    }

    @Test
    public void test03MoverUnPasoDejaVaciaLaCasillaDeOrigen() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        conectarHorizontal(origen, destino);
        Mech mech = new Mech(new Punio(), 3);
        origen.colocarUnidad(mech);

        origen.moverUnidadPor(List.of(Direccion.ESTE));

        assertFalse(origen.estaOcupada());
    }

    @Test
    public void test04MoverUnPasoDescuentaUnPasoDisponibleAlMech() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        conectarHorizontal(origen, destino);
        Mech mech = new Mech(new Punio(), 3, 3);
        origen.colocarUnidad(mech);

        origen.moverUnidadPor(List.of(Direccion.ESTE));

        assertEquals(2, mech.guardarPasos());
    }

    @Test
    public void test05MechConMovimientoPorDefectoPuedeMoverseTresPasos() {
        Casilla c0 = new Casilla(new Suelo());
        Casilla c1 = new Casilla(new Suelo());
        Casilla c2 = new Casilla(new Suelo());
        Casilla c3 = new Casilla(new Suelo());
        conectarHorizontal(c0, c1);
        conectarHorizontal(c1, c2);
        conectarHorizontal(c2, c3);
        Mech mech = new Mech(new Punio(), 3);
        c0.colocarUnidad(mech);

        c0.moverUnidadPor(List.of(Direccion.ESTE, Direccion.ESTE, Direccion.ESTE));

        assertTrue(c3.contieneUnidad(mech));
    }

    @Test
    public void test06MechPuedeMoverseRodeandoUnaMontanaEnCaminoEnL() {
        Casilla c0 = new Casilla(new Suelo());
        Casilla montana = new Casilla(new Montana());
        Casilla cSur = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        conectarHorizontal(c0, montana);
        conectarVertical(c0, cSur);
        conectarHorizontal(cSur, destino);
        conectarVertical(montana, destino);
        Mech mech = new Mech(new Punio(), 3, 3);
        c0.colocarUnidad(mech);

        c0.moverUnidadPor(List.of(Direccion.SUR, Direccion.ESTE));

        assertTrue(destino.contieneUnidad(mech));
    }

    @Test
    public void test07MechNoPuedeMoverseSiYaAgotoSusPasosEnUnMovimientoPrevio() {
        Casilla c0 = new Casilla(new Suelo());
        Casilla c1 = new Casilla(new Suelo());
        Casilla c2 = new Casilla(new Suelo());
        conectarHorizontal(c0, c1);
        conectarHorizontal(c1, c2);
        Mech mech = new Mech(new Punio(), 3, 1);
        c0.colocarUnidad(mech);
        c0.moverUnidadPor(List.of(Direccion.ESTE));

        assertThrows(IllegalStateException.class, () ->
                c1.moverUnidadPor(List.of(Direccion.ESTE)));
    }

    @Test
    public void test08MechReiniciaSusPasosYPuedeVolverAMoverse() {
        Casilla c0 = new Casilla(new Suelo());
        Casilla c1 = new Casilla(new Suelo());
        Casilla c2 = new Casilla(new Suelo());
        conectarHorizontal(c0, c1);
        conectarHorizontal(c1, c2);
        Mech mech = new Mech(new Punio(), 3, 1);
        c0.colocarUnidad(mech);
        c0.moverUnidadPor(List.of(Direccion.ESTE));

        mech.reiniciarPasos();
        c1.moverUnidadPor(List.of(Direccion.ESTE));

        assertTrue(c2.contieneUnidad(mech));
    }

    @Test
    public void test09MechConCeroPasosMaximosNoPuedeMoverse() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        conectarHorizontal(origen, destino);
        Mech mech = new Mech(new Punio(), 3, 0);
        origen.colocarUnidad(mech);

        assertThrows(IllegalStateException.class, () ->
                origen.moverUnidadPor(List.of(Direccion.ESTE)));
    }

    @Test
    public void test10MoverMasAllaDelLimiteDePasosLanzaExcepcion() {
        Casilla c0 = new Casilla(new Suelo());
        Casilla c1 = new Casilla(new Suelo());
        Casilla c2 = new Casilla(new Suelo());
        Casilla c3 = new Casilla(new Suelo());
        conectarHorizontal(c0, c1);
        conectarHorizontal(c1, c2);
        conectarHorizontal(c2, c3);
        Mech mech = new Mech(new Punio(), 3, 2);
        c0.colocarUnidad(mech);

        assertThrows(IllegalStateException.class, () ->
                c0.moverUnidadPor(List.of(Direccion.ESTE, Direccion.ESTE, Direccion.ESTE)));
    }

    @Test
    public void test11FallarPorLimiteDePasosConservaAlMechEnElOrigen() {
        Casilla c0 = new Casilla(new Suelo());
        Casilla c1 = new Casilla(new Suelo());
        Casilla c2 = new Casilla(new Suelo());
        Casilla c3 = new Casilla(new Suelo());
        conectarHorizontal(c0, c1);
        conectarHorizontal(c1, c2);
        conectarHorizontal(c2, c3);
        Mech mech = new Mech(new Punio(), 3, 2);
        c0.colocarUnidad(mech);

        try {
            c0.moverUnidadPor(List.of(Direccion.ESTE, Direccion.ESTE, Direccion.ESTE));
        } catch (IllegalStateException ignored) {
        }

        assertTrue(c0.contieneUnidad(mech));
    }

    @Test
    public void test12FallarPorLimiteDePasosRestauraLosPasosDelMech() {
        Casilla c0 = new Casilla(new Suelo());
        Casilla c1 = new Casilla(new Suelo());
        Casilla c2 = new Casilla(new Suelo());
        Casilla c3 = new Casilla(new Suelo());
        conectarHorizontal(c0, c1);
        conectarHorizontal(c1, c2);
        conectarHorizontal(c2, c3);
        Mech mech = new Mech(new Punio(), 3, 2);
        c0.colocarUnidad(mech);

        try {
            c0.moverUnidadPor(List.of(Direccion.ESTE, Direccion.ESTE, Direccion.ESTE));
        } catch (IllegalStateException ignored) {
        }

        assertEquals(2, mech.guardarPasos());
    }

    @Test
    public void test13MechNoPuedeAtravesarUnaMontanaIntermedia() {
        Casilla origen = new Casilla(new Suelo());
        Casilla montana = new Casilla(new Montana());
        Casilla destino = new Casilla(new Suelo());
        conectarHorizontal(origen, montana);
        conectarHorizontal(montana, destino);
        Mech mech = new Mech(new Punio(), 3, 3);
        origen.colocarUnidad(mech);

        assertThrows(IllegalStateException.class, () ->
                origen.moverUnidadPor(List.of(Direccion.ESTE, Direccion.ESTE)));
    }

    @Test
    public void test14FallarPorMontanaIntermediaRestauraLosPasosDelMech() {
        Casilla origen = new Casilla(new Suelo());
        Casilla montana = new Casilla(new Montana());
        Casilla destino = new Casilla(new Suelo());
        conectarHorizontal(origen, montana);
        conectarHorizontal(montana, destino);
        Mech mech = new Mech(new Punio(), 3, 3);
        origen.colocarUnidad(mech);

        try {
            origen.moverUnidadPor(List.of(Direccion.ESTE, Direccion.ESTE));
        } catch (IllegalStateException ignored) {
        }

        assertEquals(3, mech.guardarPasos());
    }

    @Test
    public void test15MechNoPuedeTerminarSuMovimientoSobreUnaMontana() {
        Casilla origen = new Casilla(new Suelo());
        Casilla montana = new Casilla(new Montana());
        conectarHorizontal(origen, montana);
        Mech mech = new Mech(new Punio(), 3, 3);
        origen.colocarUnidad(mech);

        assertThrows(IllegalStateException.class, () ->
                origen.moverUnidadPor(List.of(Direccion.ESTE)));
    }

    @Test
    public void test16MechNoPuedeTerminarSuMovimientoSobreUnEdificio() {
        Casilla origen = new Casilla(new Suelo());
        Casilla edificio = new Casilla(new Edificio(new RedElectrica(5)));
        conectarHorizontal(origen, edificio);
        Mech mech = new Mech(new Punio(), 3, 3);
        origen.colocarUnidad(mech);

        assertThrows(IllegalStateException.class, () ->
                origen.moverUnidadPor(List.of(Direccion.ESTE)));
    }

    @Test
    public void test17MechNoPuedeAtravesarUnaCasillaOcupadaPorUnVek() {
        Casilla origen = new Casilla(new Suelo());
        Casilla intermedia = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        conectarHorizontal(origen, intermedia);
        conectarHorizontal(intermedia, destino);
        Mech mech = new Mech(new Punio(), 3, 3);
        origen.colocarUnidad(mech);
        intermedia.colocarUnidad(new Saltador());

        assertThrows(IllegalStateException.class, () ->
                origen.moverUnidadPor(List.of(Direccion.ESTE, Direccion.ESTE)));
    }

    @Test
    public void test18MechNoPuedeTerminarSuMovimientoEnUnaCasillaOcupadaPorOtroMech() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        conectarHorizontal(origen, destino);
        Mech mech1 = new Mech(new Punio(), 3, 3);
        Mech mech2 = new Mech(new Punio(), 3, 3);
        origen.colocarUnidad(mech1);
        destino.colocarUnidad(mech2);

        assertThrows(IllegalStateException.class, () ->
                origen.moverUnidadPor(List.of(Direccion.ESTE)));
    }



    @Test
    public void test19MoverConCaminoVacioLanzaExcepcion() {
        Casilla origen = new Casilla(new Suelo());
        origen.colocarUnidad(new Mech(new Punio(), 3));

        assertThrows(IllegalArgumentException.class, () ->
                origen.moverUnidadPor(Collections.emptyList()));
    }

    @Test
    public void test20MechNoPuedeVolverATerminarEnLaMismaCasillaDeOrigen() {
        Casilla origen = new Casilla(new Suelo());
        Casilla vecina = new Casilla(new Suelo());
        conectarHorizontal(origen, vecina);
        Mech mech = new Mech(new Punio(), 3, 3);
        origen.colocarUnidad(mech);

        assertThrows(IllegalStateException.class, () ->
                origen.moverUnidadPor(List.of(Direccion.ESTE, Direccion.OESTE)));
    }

    @Test
    public void test21MechDestruidoNoPuedeMoverse() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        conectarHorizontal(origen, destino);
        Mech mech = new Mech(new Punio(), 2);
        origen.colocarUnidad(mech);
        mech.recibirDanio(2);

        assertThrows(IllegalStateException.class, () ->
                origen.moverUnidadPor(List.of(Direccion.ESTE)));
    }

    @Test
    public void test22AtravesarAcidoSinDetenerseNoContaminaAlMech() {
        Casilla origen = new Casilla(new Suelo());
        Casilla acido = new Casilla(new Acido());
        Casilla destino = new Casilla(new Suelo());
        conectarHorizontal(origen, acido);
        conectarHorizontal(acido, destino);
        Mech mech = new Mech(new Punio(), 3, 3);
        origen.colocarUnidad(mech);

        origen.moverUnidadPor(List.of(Direccion.ESTE, Direccion.ESTE));

        assertTrue(mech.estaContaminado());

    }

    @Test
    public void test23TerminarElMovimientoSobreAcidoContaminaAlMech() {
        Casilla origen = new Casilla(new Suelo());
        Casilla acido = new Casilla(new Acido());
        conectarHorizontal(origen, acido);
        Mech mech = new Mech(new Punio(), 3, 3);
        origen.colocarUnidad(mech);

        origen.moverUnidadPor(List.of(Direccion.ESTE));

        assertTrue(mech.estaContaminado());
    }

    @Test
    public void test24AtravesarAguaSinDetenersePermiteSeguirAtacando() {
        Casilla suelo1 = new Casilla(new Suelo());
        Casilla agua = new Casilla(new Agua());
        Casilla suelo2 = new Casilla(new Suelo());
        conectarHorizontal(suelo1, agua);
        conectarHorizontal(agua, suelo2);
        Mech mech = new Mech(mock(Arma.class), 3, 3);
        suelo1.colocarUnidad(mech);

        suelo1.moverUnidadPor(List.of(Direccion.ESTE, Direccion.ESTE));

        assertDoesNotThrow(() -> mech.atacar(suelo2, agua));
    }

    @Test
    public void test25TerminarElMovimientoSobreAguaInhabilitaElAtaqueDelMech() {
        Casilla suelo1 = new Casilla(new Suelo());
        Casilla agua = new Casilla(new Agua());
        Casilla suelo2 = new Casilla(new Suelo());
        conectarHorizontal(suelo1, agua);
        conectarHorizontal(agua, suelo2);
        Mech mech = new Mech(mock(Arma.class), 3, 3);
        suelo1.colocarUnidad(mech);

        suelo1.moverUnidadPor(List.of(Direccion.ESTE));

        assertThrows(IllegalStateException.class, () -> mech.atacar(agua, suelo2));
    }

    @Test
    public void test26SalirDelAguaHaciaSueloRestauraLaCapacidadDeAtacar() {
        Casilla suelo1 = new Casilla(new Suelo());
        Casilla agua = new Casilla(new Agua());
        Casilla suelo2 = new Casilla(new Suelo());
        conectarHorizontal(suelo1, agua);
        conectarHorizontal(agua, suelo2);
        Mech mech = new Mech(mock(Arma.class), 3, 3);
        suelo1.colocarUnidad(mech);
        suelo1.moverUnidadPor(List.of(Direccion.ESTE));

        agua.moverUnidadPor(List.of(Direccion.ESTE));

        assertDoesNotThrow(() -> mech.atacar(suelo2, agua));
    }
}
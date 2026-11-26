package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.RedElectrica;
import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.arma.Punio;
import edu.fiuba.algo3.modelo.terreno.Acido;
import edu.fiuba.algo3.modelo.terreno.Edificio;
import edu.fiuba.algo3.modelo.terreno.Suelo;
import edu.fiuba.algo3.modelo.turno.*;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;
import edu.fiuba.algo3.modelo.unidad.Luciernaga;
import edu.fiuba.algo3.modelo.unidad.Mech;
import edu.fiuba.algo3.modelo.unidad.Saltador;
import edu.fiuba.algo3.modelo.unidad.Vek;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TurnoTest {

    @Test
    public void test01DeshacerMovimientoDeMechQueNoAtacoVuelveASuPosicionAnterior() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        Mech mech = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(mech);
        FaseJugador faseJugador = (FaseJugador) new Turno().avanzarFase();

        faseJugador.moverMech(mech, origen, destino);
        faseJugador.deshacerMovimiento(mech);

        assertSame(mech, origen.getUnidad());
        assertFalse(destino.estaOcupada());
    }

    @Test
    public void test02NoSePuedeDeshacerMovimientoDeMechQueYaAtaco() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        Casilla objetivo = new Casilla(new Suelo());
        Mech mech = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(mech);
        FaseJugador faseJugador = (FaseJugador) new Turno().avanzarFase();

        faseJugador.moverMech(mech, origen, destino);
        faseJugador.atacarConMech(mech, destino, objetivo);

        assertThrows(IllegalStateException.class, () -> faseJugador.deshacerMovimiento(mech));
        assertSame(mech, destino.getUnidad());
    }

    @Test
    public void test03NoSePuedeDeshacerDosVecesEnElMismoTurno() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino1 = new Casilla(new Suelo());
        Casilla destino2 = new Casilla(new Suelo());
        Mech mech = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(mech);
        FaseJugador faseJugador = (FaseJugador) new Turno().avanzarFase();

        faseJugador.moverMech(mech, origen, destino1);
        faseJugador.deshacerMovimiento(mech);
        faseJugador.moverMech(mech, origen, destino2);

        assertThrows(IllegalStateException.class, () -> faseJugador.deshacerMovimiento(mech));
    }

    @Test
    public void test04ElTurnoEmpiezaEnFaseVek() {
        Turno turno = new Turno();

        assertTrue(turno.faseActual() instanceof FaseVek);
    }

    @Test
    public void test05AvanzarFaseCambiaDeFaseVekAFaseJugador() {
        Turno turno = new Turno();

        turno.avanzarFase();

        assertTrue(turno.faseActual() instanceof FaseJugador);
    }

    @Test
    public void test06AvanzarFaseCambiaDeFaseJugadorAFaseResolucion() {
        Turno turno = new Turno();
        turno.avanzarFase();
        turno.avanzarFase();

        assertTrue(turno.faseActual() instanceof FaseResolucion);
    }

    @Test
    public void test07AvanzarFaseCambiaDeFaseResolucionAFaseEmergencia() {
        Turno turno = new Turno();
        turno.avanzarFase();
        turno.avanzarFase();
        turno.avanzarFase();


        assertTrue(turno.faseActual() instanceof FaseEmergencia);
    }

    @Test
    public void test08AlNuevoTurnoSePuedeDeshacerDeNuevo() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        Mech mech = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(mech);
        Turno turno = new Turno();

        FaseJugador faseJugador = (FaseJugador) turno.avanzarFase();
        faseJugador.moverMech(mech, origen, destino);
        faseJugador.deshacerMovimiento(mech);

        for (int i = 0; i < 4; i++) {
            turno.avanzarFase();
        }
        faseJugador = (FaseJugador) turno.faseActual();

        faseJugador.moverMech(mech, origen, destino);
        faseJugador.deshacerMovimiento(mech);

        assertSame(mech, origen.getUnidad());
    }

    @Test
    public void test09AlTerminarUnCicloCompletoPasaAlSiguienteTurno() {
        Turno turno = new Turno();
        assertEquals(1, turno.obtenerNumero());

        for (int i = 0; i < 4; i++) {
            turno.avanzarFase();
        }

        assertEquals(2, turno.obtenerNumero());
    }

    @Test
    public void test10NoSePuedeDeshacerFueraDeLaFaseDelJugador() {
        Casilla origen = new Casilla(new Suelo());
        Mech mech = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(mech);
        Turno turno = new Turno();

        assertFalse(turno.faseActual() instanceof FaseJugador);
    }

    @Test
    public void test11NoSePuedeDeshacerSiElMechNoSeMovio() {
        Casilla origen = new Casilla(new Suelo());
        Mech mech = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(mech);
        FaseJugador faseJugador = (FaseJugador) new Turno().avanzarFase();

        assertThrows(IllegalStateException.class, () -> faseJugador.deshacerMovimiento(mech));
    }

    @Test
    public void test12NoSePuedeMoverUnMechQueYaAtaco() {
        Casilla origen = new Casilla(new Suelo());
        Casilla objetivo = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        Mech mech = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(mech);
        FaseJugador faseJugador = (FaseJugador) new Turno().avanzarFase();

        faseJugador.atacarConMech(mech, origen, objetivo);

        assertThrows(IllegalStateException.class, () -> faseJugador.moverMech(mech, origen, destino));
    }

    @Test
    public void test13NoSePuedeMoverUnMechEnFaseVek() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        Mech mech = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(mech);
        Turno turno = new Turno();

        assertFalse(turno.faseActual() instanceof FaseJugador);
    }

    @Test
    public void test14DeshacerMovimientoSobreAcidoQuitaLaContaminacion() {
        Casilla origen = new Casilla(new Suelo());
        Casilla acido = new Casilla(new Acido());
        origen.conectarVecina(Direccion.ESTE, acido);
        acido.conectarVecina(Direccion.OESTE, origen);

        Mech mech = new Mech(new Punio(), 3);
        origen.colocarUnidad(mech);

        FaseJugador faseJugador = (FaseJugador) new Turno().avanzarFase();
        faseJugador.moverMech(mech, origen, acido);
        assertTrue(mech.estaContaminado());

        faseJugador.deshacerMovimiento(mech);

        assertFalse(mech.estaContaminado());
    }

    @Test
    public void test15DeshacerNoQuitaUnaContaminacionAnterior() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.ESTE, destino);
        destino.conectarVecina(Direccion.OESTE, origen);

        Mech mech = new Mech(new Punio(), 3);
        mech.aplicarEfectoAcido();
        origen.colocarUnidad(mech);

        FaseJugador faseJugador = (FaseJugador) new Turno().avanzarFase();
        faseJugador.moverMech(mech, origen, destino);
        faseJugador.deshacerMovimiento(mech);

        assertTrue(mech.estaContaminado());
    }

    @Test
    public void test16DeshacerMovimientoEsRechazadoPorAtaqueYNoPierdeElMovimientoPendiente() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        Casilla objetivo = new Casilla(new Suelo());
        Casilla otroOrigen = new Casilla(new Suelo());
        Casilla otroDestino = new Casilla(new Suelo());
        Mech primero = new Mech(mock(Arma.class), 3);
        Mech segundo = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(primero);
        otroOrigen.colocarUnidad(segundo);

        Turno turno = new Turno();
        FaseJugador faseJugador = new FaseVek().ejecutar(turno);

        faseJugador.moverMech(primero, origen, destino);
        faseJugador.atacarConMech(primero, destino, objetivo);
        faseJugador.moverMech(segundo, otroOrigen, otroDestino);

        assertThrows(IllegalStateException.class, () -> faseJugador.deshacerMovimiento(primero));

        faseJugador.deshacerMovimiento(segundo);

        assertSame(segundo, otroOrigen.getUnidad());
        assertFalse(otroDestino.estaOcupada());
    }

    @Test
    public void test17UnAtaqueAnunciadoNoHaceDanioHastaLaResolucion() {
        Casilla vekCasilla = new Casilla(new Suelo());
        Casilla mechCasilla = new Casilla(new Suelo());
        vekCasilla.conectarVecina(Direccion.ESTE, mechCasilla);
        mechCasilla.conectarVecina(Direccion.OESTE, vekCasilla);

        Vek vek = new Saltador();
        vekCasilla.colocarUnidad(vek);
        Mech mech = new Mech(new Punio(), 3);
        mechCasilla.colocarUnidad(mech);
        int vidaInicialMech = mech.getVidaActual();

        Turno turno = new Turno();
        FaseVek faseVek = new FaseVek();
        faseVek.registrarAtaque(new AtaqueAnunciado(vek, Direccion.ESTE));

        FaseJugador faseJugador = faseVek.ejecutar(turno);
        FaseResolucion faseResolucion = faseJugador.ejecutar(turno);

        assertEquals(vidaInicialMech, mech.getVidaActual());

        faseResolucion.ejecutar(turno);

        assertEquals(vidaInicialMech - 2, mech.getVidaActual());
    }


    @Test
    public void test18VekAnunciaAtaqueHaciaElMech() {
        Casilla vekCasilla = new Casilla(new Suelo());
        Casilla mechCasilla = new Casilla(new Suelo());
        vekCasilla.conectarVecina(Direccion.ESTE, mechCasilla);
        mechCasilla.conectarVecina(Direccion.OESTE, vekCasilla);

        Vek vek = new Saltador();
        vekCasilla.colocarUnidad(vek);
        mechCasilla.colocarUnidad(new Mech(mock(Arma.class), 3));

        AtaqueAnunciado ataque = vek.anunciaAtaque(vekCasilla);

        assertEquals(Direccion.ESTE, ataque.getDireccion());
    }

    @Test
    public void test19VekAnunciaAtaqueHaciaElMechQueEstaAlOeste() {
        Casilla mechCasilla = new Casilla(new Suelo());
        Casilla vekCasilla = new Casilla(new Suelo());
        mechCasilla.conectarVecina(Direccion.ESTE, vekCasilla);
        vekCasilla.conectarVecina(Direccion.OESTE, mechCasilla);

        Vek vek = new Saltador();
        vekCasilla.colocarUnidad(vek);
        mechCasilla.colocarUnidad(new Mech(mock(Arma.class), 3));

        AtaqueAnunciado ataque = vek.anunciaAtaque(vekCasilla);

        assertEquals(Direccion.OESTE, ataque.getDireccion());
    }

    @Test
    public void test20FaseResolucionEjecutaLosAtaquesEnElOrdenAnunciado() {
        Casilla casillaA = new Casilla(new Suelo());
        Casilla casillaB = new Casilla(new Suelo());

        Vek primero = mock(Vek.class);
        when(primero.getCasillaActual()).thenReturn(casillaA);
        when(primero.getVidaActual()).thenReturn(1);

        Vek segundo = mock(Vek.class);
        when(segundo.getCasillaActual()).thenReturn(casillaB);
        when(segundo.getVidaActual()).thenReturn(1);

        Turno turno = new Turno();
        FaseVek faseVek = new FaseVek();
        faseVek.registrarAtaque(new AtaqueAnunciado(primero, Direccion.ESTE));
        faseVek.registrarAtaque(new AtaqueAnunciado(segundo, Direccion.OESTE));

        FaseJugador faseJugador = faseVek.ejecutar(turno);
        FaseResolucion faseResolucion = faseJugador.ejecutar(turno);
        faseResolucion.ejecutar(turno);

        InOrder orden = inOrder(primero, segundo);
        orden.verify(primero).atacar(eq(casillaA), any(Celda.class));
        orden.verify(segundo).atacar(eq(casillaB), any(Celda.class));
    }

    @Test
    public void test21FaseResolucionEjecutaElAtaqueAnunciadoYHaceDanio() {
        Casilla vekCasilla = new Casilla(new Suelo());
        Casilla mechCasilla = new Casilla(new Suelo());
        vekCasilla.conectarVecina(Direccion.ESTE, mechCasilla);
        mechCasilla.conectarVecina(Direccion.OESTE, vekCasilla);

        Vek vek = new Saltador();
        vekCasilla.colocarUnidad(vek);
        Mech mech = new Mech(new Punio(), 3);
        mechCasilla.colocarUnidad(mech);
        int vidaInicialMech = mech.getVidaActual();

        Turno turno = new Turno();
        FaseVek faseVek = new FaseVek();
        faseVek.registrarAtaque(new AtaqueAnunciado(vek, Direccion.ESTE));

        FaseJugador faseJugador = faseVek.ejecutar(turno);
        FaseResolucion faseResolucion = faseJugador.ejecutar(turno);
        faseResolucion.ejecutar(turno);

        assertEquals(vidaInicialMech - 2, mech.getVidaActual());
    }

    @Test
    public void test22FaseVekMueveAlVekHaciaElEdificio() {
        Casilla origen = new Casilla(new Suelo());
        Casilla intermedia = new Casilla(new Suelo());
        Casilla edificio = new Casilla(new Edificio(new RedElectrica(5)));
        origen.conectarVecina(Direccion.ESTE, intermedia);
        intermedia.conectarVecina(Direccion.OESTE, origen);
        intermedia.conectarVecina(Direccion.ESTE, edificio);
        edificio.conectarVecina(Direccion.OESTE, intermedia);

        Vek vek = new Saltador();
        origen.colocarUnidad(vek);
        FaseVek faseVek = new FaseVek();

        faseVek.moverVek(origen);

        assertSame(vek, intermedia.getUnidad());
    }

    @Test
    public void test23NoSePuedeMoverUnMechDesdeLaCasillaDeOtro() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.ESTE, destino);
        destino.conectarVecina(Direccion.OESTE, origen);

        Mech mechSeleccionado = new Mech(new Punio(), 3);
        Mech mechEnOrigen = new Mech(new Punio(), 3);
        origen.colocarUnidad(mechEnOrigen);

        FaseJugador faseJugador = (FaseJugador) new Turno().avanzarFase();

        assertThrows(IllegalStateException.class,
                () -> faseJugador.moverMech(mechSeleccionado, origen, destino));
        assertSame(mechEnOrigen, origen.getUnidad());
        assertFalse(destino.estaOcupada());

        faseJugador.moverMech(mechEnOrigen, origen, destino);

        assertSame(mechEnOrigen, destino.getUnidad());
        assertFalse(origen.estaOcupada());
    }

    @Test
    public void test24RechazarDeshacerConservaElMovimientoRegistrado() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.ESTE, destino);
        destino.conectarVecina(Direccion.OESTE, origen);

        Mech mech = new Mech(new Punio(), 3);
        origen.colocarUnidad(mech);

        FaseJugador faseJugador = (FaseJugador) new Turno().avanzarFase();
        faseJugador.moverMech(mech, origen, destino);
        faseJugador.deshacerMovimiento(mech);
        faseJugador.moverMech(mech, origen, destino);

        assertThrows(IllegalStateException.class, () -> faseJugador.deshacerMovimiento(mech));
        assertSame(mech, destino.getUnidad());
        assertFalse(origen.estaOcupada());

        assertThrows(IllegalStateException.class, () -> faseJugador.moverMech(mech, destino, origen));
        assertSame(mech, destino.getUnidad());
        assertFalse(origen.estaOcupada());
    }

    @Test
    public void test25FaseVekEjecutaMovimientoYAnunciaAtaque(){
        RedElectrica red =  new RedElectrica(5);
        Casilla[] casillas = new Casilla[4];

        for(int i = 0; i < 3; i++){
            casillas[i] = new Casilla(new Suelo());
        }

        casillas[3] = new Casilla(new Edificio(red));

        for(int i = 0; i < 3; i++){
            casillas[i].conectarVecina(Direccion.ESTE, casillas[i + 1]);
            casillas[i + 1].conectarVecina(Direccion.OESTE, casillas[i]);
        }

        Saltador vek = new Saltador();
        casillas[0].colocarUnidad(vek);

        Tablero tablero = new Tablero(List.of(casillas));
        Turno turno = new Turno(tablero);

        turno.avanzarFase();

        assertSame(vek, casillas[2].getUnidad());
        assertFalse(casillas[0].estaOcupada());
        assertEquals(5, red.getEnergia());

        turno.avanzarFase();
        turno.avanzarFase();

        assertEquals(4, red.getEnergia());


    }
}
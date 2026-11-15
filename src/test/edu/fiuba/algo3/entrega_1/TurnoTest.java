package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.unidad.Mech;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TurnoTest {

    @Test
    public void test01DeshacerMovimientoDeMechQueNoAtacoVuelveASuPosicionAnterior() {
        Tablero tablero = new Tablero();
        Mech mech = new Mech(mock(Arma.class), 3);
        Posicion origen = new Posicion(2, 2);
        Posicion destino = new Posicion(2, 3);
        tablero.colocarUnidad(origen, mech);
        Turno turno = new Turno(tablero);
        turno.avanzarFase();

        turno.moverMech(mech, destino);
        turno.deshacerMovimiento(mech);

        assertEquals(mech, tablero.obtenerCasilla(origen).getUnidad());
        assertFalse(tablero.obtenerCasilla(destino).estaOcupada());
    }

    @Test
    public void test02NoSePuedeDeshacerMovimientoDeMechQueYaAtaco() {
        Tablero tablero = new Tablero();
        Mech mech = new Mech(mock(Arma.class), 3);
        Posicion origen = new Posicion(2, 2);
        Posicion destino = new Posicion(2, 3);
        tablero.colocarUnidad(origen, mech);
        Turno turno = new Turno(tablero);
        turno.avanzarFase();

        turno.moverMech(mech, destino);
        mech.atacar(destino, new Posicion(2, 4), tablero);

        assertThrows(IllegalStateException.class, () -> turno.deshacerMovimiento(mech));
        assertEquals(mech, tablero.obtenerCasilla(destino).getUnidad());
    }

    @Test
    public void test03NoSePuedeDeshacerDosVecesEnElMismoTurno() {
        Tablero tablero = new Tablero();
        Mech mech = new Mech(mock(Arma.class), 3);
        Posicion origen = new Posicion(2, 2);
        tablero.colocarUnidad(origen, mech);
        Turno turno = new Turno(tablero);
        turno.avanzarFase();

        turno.moverMech(mech, new Posicion(2, 3));
        turno.deshacerMovimiento(mech);
        turno.moverMech(mech, new Posicion(3, 2));

        assertThrows(IllegalStateException.class, () -> turno.deshacerMovimiento(mech));
    }


    @Test
    public void test04ElTurnoEmpiezaEnFaseVek() {
        Turno turno = new Turno(new Tablero());

        assertTrue(turno.obtenerFaseActual() instanceof FaseVek);
    }

    @Test
    public void test05AvanzarFaseCambiaDeFaseVekAFaseJugador() {
        Turno turno = new Turno(new Tablero());

        turno.avanzarFase();

        assertTrue(turno.obtenerFaseActual() instanceof FaseJugador);
    }

    @Test
    public void test06AvanzarFaseCambiaDeFaseJugadorAFaseResolucion() {
        Turno turno = new Turno(new Tablero());
        turno.avanzarFase();
        turno.avanzarFase();

        assertTrue(turno.obtenerFaseActual() instanceof FaseResolucion);
    }

    @Test
    public void test07AvanzarFaseCambiaDeFaseResolucionAFaseEmergencia() {
        Turno turno = new Turno(new Tablero());
        turno.avanzarFase();
        turno.avanzarFase();

        turno.avanzarFase();

        assertTrue(turno.obtenerFaseActual() instanceof FaseEmergencia);
    }

    @Test
    public void test08AlNuevoTurnoSePuedeDeshacerDeNuevo() {
        Tablero tablero = new Tablero();
        Mech mech = new Mech(mock(Arma.class), 3);
        Posicion origen = new Posicion(2, 2);
        tablero.colocarUnidad(origen, mech);
        Turno turno = new Turno(tablero);

        turno.avanzarFase();
        turno.moverMech(mech, new Posicion(2, 3));
        turno.deshacerMovimiento(mech);

        for (int i = 0; i < 4; i++) {
            turno.avanzarFase();
        }
        turno.moverMech(mech, new Posicion(2, 3));
        turno.deshacerMovimiento(mech);

        assertSame(mech, tablero.obtenerCasilla(origen).getUnidad());
    }

    @Test
    public void test09AlTerminarUnCicloCompletoPasaAlSiguienteTurno() {
        Turno turno = new Turno(new Tablero());
        assertEquals(1, turno.obtenerNumero());

        for (int i = 0; i < 4; i++) {
            turno.avanzarFase();
        }
        assertEquals(2, turno.obtenerNumero());
    }


    @Test
    public void test10NoSePuedeDeshacerFueraDeLaFaseDelJugador() {
        Tablero tablero = new Tablero();
        Mech mech = new Mech(mock(Arma.class), 3);
        Posicion origen = new Posicion(2, 2);
        tablero.colocarUnidad(origen, mech);
        Turno turno = new Turno(tablero);

        assertThrows(IllegalStateException.class, () -> turno.deshacerMovimiento(mech));
    }

    @Test
    public void test11NoSePuedeDeshacerSiElMechNoSeMovio() {
        Tablero tablero = new Tablero();
        Mech mech = new Mech(mock(Arma.class), 3);
        tablero.colocarUnidad(new Posicion(2, 2), mech);
        Turno turno = new Turno(tablero);
        turno.avanzarFase();

        assertThrows(IllegalStateException.class, () -> turno.deshacerMovimiento(mech));
    }


    @Test
    public void test12NoSePuedeMoverUnMechQueYaAtaco() {
        Tablero tablero = new Tablero();
        Mech mech = new Mech(mock(Arma.class), 3);
        Posicion origen = new Posicion(2, 2);
        tablero.colocarUnidad(origen, mech);
        Turno turno = new Turno(tablero);
        turno.avanzarFase();

        mech.atacar(origen, new Posicion(2, 3), tablero);

        assertThrows(IllegalStateException.class,
                () -> turno.moverMech(mech, new Posicion(2, 4)));
    }

    @Test
    public void test13NoSePuedeMoverUnMechEnFaseVek() {
        Tablero tablero = new Tablero();
        Mech mech = new Mech(mock(Arma.class), 3);
        Posicion origen = new Posicion(2, 2);
        tablero.colocarUnidad(origen, mech);
        Turno turno = new Turno(tablero);

        assertThrows(IllegalStateException.class,
                () -> turno.moverMech(mech, new Posicion(2, 3)));
    }
}
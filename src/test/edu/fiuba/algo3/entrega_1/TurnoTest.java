package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.terreno.Suelo;
import edu.fiuba.algo3.modelo.turno.*;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.unidad.Mech;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TurnoTest {

    @Test
    public void test01DeshacerMovimientoDeMechQueNoAtacoVuelveASuPosicionAnterior() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        Mech mech = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(mech);
        Turno turno = new Turno();
        turno.avanzarFase();

        turno.moverMech(mech, origen, destino);
        turno.deshacerMovimiento(mech);

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
        Turno turno = new Turno();
        turno.avanzarFase();

        turno.moverMech(mech, origen, destino);
        turno.atacarConMech(mech, destino, objetivo);

        assertThrows(IllegalStateException.class, () -> turno.deshacerMovimiento(mech));
        assertSame(mech, destino.getUnidad());
    }

    @Test
    public void test03NoSePuedeDeshacerDosVecesEnElMismoTurno() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino1 = new Casilla(new Suelo());
        Casilla destino2 = new Casilla(new Suelo());
        Mech mech = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(mech);
        Turno turno = new Turno();
        turno.avanzarFase();

        turno.moverMech(mech, origen, destino1);
        turno.deshacerMovimiento(mech);
        turno.moverMech(mech, origen, destino2);

        assertThrows(IllegalStateException.class, () -> turno.deshacerMovimiento(mech));
    }

    @Test
    public void test04ElTurnoEmpiezaEnFaseVek() {
        Turno turno = new Turno();

        assertTrue(turno.obtenerFaseActual() instanceof FaseVek);
    }

    @Test
    public void test05AvanzarFaseCambiaDeFaseVekAFaseJugador() {
        Turno turno = new Turno();

        turno.avanzarFase();

        assertTrue(turno.obtenerFaseActual() instanceof FaseJugador);
    }

    @Test
    public void test06AvanzarFaseCambiaDeFaseJugadorAFaseResolucion() {
        Turno turno = new Turno();
        turno.avanzarFase();
        turno.avanzarFase();

        assertTrue(turno.obtenerFaseActual() instanceof FaseResolucion);
    }

    @Test
    public void test07AvanzarFaseCambiaDeFaseResolucionAFaseEmergencia() {
        Turno turno = new Turno();
        turno.avanzarFase();
        turno.avanzarFase();

        turno.avanzarFase();

        assertTrue(turno.obtenerFaseActual() instanceof FaseEmergencia);
    }

    @Test
    public void test08AlNuevoTurnoSePuedeDeshacerDeNuevo() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        Mech mech = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(mech);
        Turno turno = new Turno();

        turno.avanzarFase();
        turno.moverMech(mech, origen, destino);
        turno.deshacerMovimiento(mech);

        for (int i = 0; i < 4; i++) {
            turno.avanzarFase();
        }
        turno.moverMech(mech, origen, destino);
        turno.deshacerMovimiento(mech);

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

        assertThrows(IllegalStateException.class, () -> turno.deshacerMovimiento(mech));
    }

    @Test
    public void test11NoSePuedeDeshacerSiElMechNoSeMovio() {
        Casilla origen = new Casilla(new Suelo());
        Mech mech = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(mech);
        Turno turno = new Turno();
        turno.avanzarFase();

        assertThrows(IllegalStateException.class, () -> turno.deshacerMovimiento(mech));
    }

    @Test
    public void test12NoSePuedeMoverUnMechQueYaAtaco() {
        Casilla origen = new Casilla(new Suelo());
        Casilla objetivo = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        Mech mech = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(mech);
        Turno turno = new Turno();
        turno.avanzarFase();

        turno.atacarConMech(mech, origen, objetivo);

        assertThrows(IllegalStateException.class, () -> turno.moverMech(mech, origen, destino));
    }

    @Test
    public void test13NoSePuedeMoverUnMechEnFaseVek() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        Mech mech = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(mech);
        Turno turno = new Turno();

        assertThrows(IllegalStateException.class, () -> turno.moverMech(mech, origen, destino));
    }
}
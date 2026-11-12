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

        turno.moverUnidad(mech, destino);
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

        turno.moverUnidad(mech, destino);
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

        turno.moverUnidad(mech, new Posicion(2, 3));
        turno.deshacerMovimiento(mech);
        turno.moverUnidad(mech, new Posicion(3, 2));

        assertThrows(IllegalStateException.class, () -> turno.deshacerMovimiento(mech));
    }
}
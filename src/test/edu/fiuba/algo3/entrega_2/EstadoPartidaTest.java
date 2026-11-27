package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.RedElectrica;
import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.partida.EnCurso;
import edu.fiuba.algo3.modelo.partida.EstadoPartida;
import edu.fiuba.algo3.modelo.terreno.Edificio;
import edu.fiuba.algo3.modelo.terreno.Suelo;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;
import edu.fiuba.algo3.modelo.unidad.Mech;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class EstadoPartidaTest {

    @Test
    public void test01EnCursoPasaAGanadaAlCompletarCincoTurnosConRedViva() {
        EstadoPartida siguiente = new EnCurso().evaluar(5, 5,3);

        assertFalse(siguiente.permiteAvanzar());
    }

    @Test
    public void test02EnCursoPasaAPerdidaCuandoLaRedLlegaACero() {
        EstadoPartida siguiente = new EnCurso().evaluar(1, 0,3);

        assertFalse(siguiente.permiteAvanzar());
    }

    @Test
    public void test03PartidaTerminadaConRedViva() {
        Casilla casilla = new Casilla(new Suelo());
        Arma arma = mock(Arma.class);
        casilla.colocarUnidad(new Mech(arma, 3));

        Juego juego = new Juego(new Tablero(List.of(casilla)), new RedElectrica(5));

        for (int i = 0; i < 5; i++) {
            juego.avanzarTurno();
        }

        assertTrue(juego.getEnergiaRedElectrica() > 0);
    }

    @Test
    public void test04PartidaTerminadaConRedEnCero() {
        Casilla casilla = new Casilla(new Suelo());
        RedElectrica redElectrica = new RedElectrica(1);
        Casilla casillaEdificio = new Casilla(new Edificio(redElectrica));

        Juego juego = new Juego(new Tablero(List.of(casilla, casillaEdificio)), redElectrica);

        casillaEdificio.recibirDanio(1);
        juego.avanzarTurno();

        assertEquals(0, juego.getEnergiaRedElectrica());
        assertThrows(IllegalStateException.class, () -> juego.avanzarTurno());
    }

    @Test
    public void test05PartidaContinuaSiQuedanMechsYTerminaSiNoQuedaNinguno() {
        Casilla casillaConMech = new Casilla(new Suelo());
        Arma arma = mock(Arma.class);
        Mech mech = new Mech(arma,3);
        casillaConMech.colocarUnidad(mech);

        Tablero tablero = new Tablero(List.of(casillaConMech));
        Juego juego = new Juego(tablero, new RedElectrica(5));

        juego.avanzarTurno();
        casillaConMech.recibirDanio(mech.getVidaActual());
        juego.avanzarTurno();

        assertEquals(0, tablero.cantidadDeMechs());
        assertThrows(IllegalStateException.class, () -> juego.avanzarTurno());
    }


}
package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.Juego;
import edu.fiuba.algo3.modelo.RedElectrica;
import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.terreno.Edificio;
import edu.fiuba.algo3.modelo.terreno.Suelo;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;
import edu.fiuba.algo3.modelo.unidad.Mech;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class JuegoTest {

    @Test
    public void test01PartidaGanadaAlCompletarCincoTurnosConRedViva() {
        Casilla casillaConMech = new Casilla(new Suelo());
        Arma arma = mock(Arma.class);
        Mech mech = new Mech(arma,3);

        casillaConMech.colocarUnidad(mech);
        Juego juego = new Juego(new Tablero(List.of(casillaConMech)), new RedElectrica(5));

        for (int i = 0; i < 5; i++) {
            juego.avanzarTurno();
        }

        assertThrows(IllegalStateException.class, () -> juego.avanzarTurno());
    }

    @Test
    public void test02PartidaNoAvanzaSiTieneRedDeEnergiaCero() {
        RedElectrica redElectrica = new RedElectrica(5);
        List<Casilla> casillas = new ArrayList<>();
        Juego juego = new Juego(new Tablero(casillas), redElectrica);


        for (int i = 0; i < 5; i++) {
            Casilla casillaEdificio = new Casilla(new Edificio(redElectrica));
            casillaEdificio.recibirDanio(1);
            casillas.add(casillaEdificio);
        }

        juego.avanzarTurno();
        assertEquals(0, juego.getEnergiaRedElectrica());
        assertThrows(IllegalStateException.class, () -> juego.avanzarTurno());
    }

    @Test
    public void test03PartidaContinuaConMechsYRedVivaYTerminaAlQuedarSinMechs() {
        RedElectrica redElectrica = new RedElectrica(5);
        Casilla casillaMech1 = new Casilla(new Suelo());
        Casilla casillaMech2 = new Casilla(new Suelo());
        Casilla casillaEdificio = new Casilla(new Edificio(redElectrica));

        Arma arma1 = mock(Arma.class);
        Arma arma2 = mock(Arma.class);
        Mech mech1 = new Mech(arma1, 3);
        Mech mech2 = new Mech(arma2, 3);

        casillaMech1.colocarUnidad(mech1);
        casillaMech2.colocarUnidad(mech2);

        Tablero tablero = new Tablero(List.of(casillaMech1, casillaMech2, casillaEdificio));
        Juego juego = new Juego(tablero, redElectrica);

        juego.avanzarTurno();
        assertEquals(2, tablero.cantidadDeMechs());
        assertTrue(juego.getEnergiaRedElectrica() > 0);

        casillaEdificio.recibirDanio(1);
        juego.avanzarTurno();
        assertEquals(4, juego.getEnergiaRedElectrica());
        assertEquals(2, tablero.cantidadDeMechs());

        casillaMech1.recibirDanio(mech1.getVidaActual());
        juego.avanzarTurno();
        assertEquals(1, tablero.cantidadDeMechs());

        casillaMech2.recibirDanio(mech2.getVidaActual());
        juego.avanzarTurno();
        assertEquals(0, tablero.cantidadDeMechs());
        assertThrows(IllegalStateException.class, () -> juego.avanzarTurno());
    }







}
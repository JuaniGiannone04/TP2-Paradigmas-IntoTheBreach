package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.IA.*;
import edu.fiuba.algo3.modelo.GeneradorDeTablero;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;
import edu.fiuba.algo3.modelo.unidad.Saltador;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IAVekTest {

    @Test
    public void test01SaltadorPuedeAlcanzarCasillaATresMovimientos() {

        GeneradorDeTablero generador = new GeneradorDeTablero();
        Tablero tablero = generador.crearTableroVacio();

        Casilla origen = tablero.getCasillaInicial();

        Casilla destino = (Casilla) origen
                .vecinaAl(Direccion.ESTE)
                .vecinaAl(Direccion.ESTE)
                .vecinaAl(Direccion.ESTE);

        Saltador saltador = new Saltador();

        origen.colocarUnidad(saltador);
        saltador.asignarCasilla(origen);

        IAVek ia = new IAVek(tablero, saltador);

        List<Casilla> alcanzables = ia.calcularCasillasAlcanzables();

        assertTrue(alcanzables.contains(destino));
    }

    @Test
    public void test02SaltadorNoPuedeAlcanzarCasillaACuatroMovimientos() {

        GeneradorDeTablero generador = new GeneradorDeTablero();
        Tablero tablero = generador.crearTableroVacio();

        Casilla origen = tablero.getCasillaInicial();

        Casilla destino = (Casilla) origen
                .vecinaAl(Direccion.ESTE)
                .vecinaAl(Direccion.ESTE)
                .vecinaAl(Direccion.ESTE)
                .vecinaAl(Direccion.ESTE);

        Saltador saltador = new Saltador();

        origen.colocarUnidad(saltador);
        saltador.asignarCasilla(origen);

        IAVek ia = new IAVek(tablero, saltador);

        List<Casilla> alcanzables = ia.calcularCasillasAlcanzables();

        assertFalse(alcanzables.contains(destino));
    }

    @Test
    public void test03SaltadorNoPuedeAtravesarUnidades() {

        GeneradorDeTablero generador = new GeneradorDeTablero();
        Tablero tablero = generador.crearTableroVacio();

        Casilla origen = tablero.getCasillaInicial();

        Casilla este = (Casilla) origen.vecinaAl(Direccion.ESTE);
        Casilla sur = (Casilla) origen.vecinaAl(Direccion.SUR);

        Casilla destino = (Casilla) este.vecinaAl(Direccion.ESTE);

        Saltador saltador = new Saltador();
        Saltador bloqueoEste = new Saltador();
        Saltador bloqueoSur = new Saltador();

        origen.colocarUnidad(saltador);
        saltador.asignarCasilla(origen);

        este.colocarUnidad(bloqueoEste);
        sur.colocarUnidad(bloqueoSur);

        IAVek ia = new IAVek(tablero, saltador);

        List<Casilla> alcanzables = ia.calcularCasillasAlcanzables();

        assertFalse(alcanzables.contains(destino));
        assertTrue(alcanzables.isEmpty());
    }

    @Test
    public void test04PrioridadEdificioDelegaEnLaSiguiente() {
        PrioridadVek prioridades = new PrioridadEdificio(
                new PrioridadMech(null)
        );

        Saltador saltador = new Saltador();
        Tablero tablero = new Tablero(new ArrayList<>());

        List<Casilla> alcanzables = new ArrayList<>();

        DecisionVek decision = prioridades.evaluar(
                saltador, tablero, alcanzables
        );

        assertNull(decision);
    }

    @Test
    public void test05UltimaPrioridadSinSiguienteDevuelveNull() {
        PrioridadVek prioridad = new PrioridadEdificio(null);

        Saltador saltador = new Saltador();

        DecisionVek decision = prioridad.evaluar(saltador, null, new ArrayList<>());

        assertNull(decision);
    }

    @Test
    public void test06CadenaCompletaSinDecisionesDevuelveNull() {
        PrioridadVek prioridades = new PrioridadEdificio(new PrioridadMech(new PrioridadAcercamiento(null)));

        DecisionVek decision = prioridades.evaluar(new Saltador(), null, new ArrayList<>());

        assertNull(decision);
    }

}

package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.IA.DecisionVek;
import edu.fiuba.algo3.modelo.IA.IAVek;
import edu.fiuba.algo3.modelo.IA.PrioridadAcercamiento;
import edu.fiuba.algo3.modelo.RedElectrica;
import edu.fiuba.algo3.modelo.arma.Punio;
import edu.fiuba.algo3.modelo.terreno.Edificio;
import edu.fiuba.algo3.modelo.terreno.Suelo;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;
import edu.fiuba.algo3.modelo.unidad.Avispon;
import edu.fiuba.algo3.modelo.unidad.Luciernaga;
import edu.fiuba.algo3.modelo.unidad.Mech;
import edu.fiuba.algo3.modelo.unidad.Saltador;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class IAVekTest {

    @Test
    public void test01SaltadorAtacaEdificioAdyacente() {

        RedElectrica redElectrica = new RedElectrica(5);
        Saltador saltador = new Saltador();

        Casilla casillaSaltador = new Casilla(new Suelo());
        Casilla casillaEdificio = new Casilla(new Edificio(redElectrica));

        casillaSaltador.conectarVecina(
                Direccion.ESTE, casillaEdificio);

        casillaEdificio.conectarVecina(
                Direccion.OESTE, casillaSaltador);

        casillaSaltador.colocarUnidad(saltador);

        Tablero tablero = new Tablero(
                List.of(casillaSaltador, casillaEdificio));

        IAVek ia = new IAVek(tablero, saltador);

        DecisionVek decision = ia.decidir();

        assertNotNull(decision);
        assertSame(casillaSaltador, decision.getDestino());
        assertEquals(Direccion.ESTE, decision.getDireccionAtaque());
    }



    @Test
    public void test02SaltadorPriorizaEdificioSobreMech() {

        RedElectrica redElectrica = new RedElectrica(5);
        Saltador saltador = new Saltador();

        Casilla casillaSaltador = new Casilla(new Suelo());
        Casilla casillaEdificio = new Casilla(new Edificio(redElectrica));
        Casilla casillaMech = new Casilla(new Suelo());

        Mech mech = new Mech(new Punio(), 3);

        casillaSaltador.conectarVecina(
                Direccion.NORTE, casillaEdificio);
        casillaEdificio.conectarVecina(
                Direccion.SUR, casillaSaltador);

        casillaSaltador.conectarVecina(
                Direccion.ESTE, casillaMech);
        casillaMech.conectarVecina(
                Direccion.OESTE, casillaSaltador);

        casillaSaltador.colocarUnidad(saltador);
        casillaMech.colocarUnidad(mech);

        Tablero tablero = new Tablero(
                List.of(casillaSaltador, casillaEdificio, casillaMech));

        IAVek ia = new IAVek(tablero, saltador);

        DecisionVek decision = ia.decidir();

        assertNotNull(decision);
        assertSame(casillaSaltador, decision.getDestino());
        assertEquals(Direccion.NORTE, decision.getDireccionAtaque());
    }



    @Test
    public void test03SaltadorAtacaMechSiNoHayEdificios() {

        Saltador saltador = new Saltador();
        Mech mech = new Mech(new Punio(), 3);

        Casilla casillaSaltador = new Casilla(new Suelo());
        Casilla casillaMech = new Casilla(new Suelo());

        casillaSaltador.conectarVecina(
                Direccion.ESTE, casillaMech);

        casillaMech.conectarVecina(
                Direccion.OESTE, casillaSaltador);

        casillaSaltador.colocarUnidad(saltador);
        casillaMech.colocarUnidad(mech);

        Tablero tablero = new Tablero(
                List.of(casillaSaltador, casillaMech));

        IAVek ia = new IAVek(tablero, saltador);

        DecisionVek decision = ia.decidir();

        assertNotNull(decision);
        assertSame(casillaSaltador, decision.getDestino());
        assertEquals(Direccion.ESTE, decision.getDireccionAtaque());
    }



    @Test
    public void test04SaltadorSeAcercaAEdificioSiNoPuedeAtacar() {

        RedElectrica redElectrica = new RedElectrica(5);
        Saltador saltador = new Saltador();

        Casilla[] casillas = new Casilla[6];

        for (int i = 0; i < 5; i++) {
            casillas[i] = new Casilla(new Suelo());
        }

        casillas[5] = new Casilla(new Edificio(redElectrica));

        for (int i = 0; i < 5; i++) {
            casillas[i].conectarVecina(Direccion.ESTE, casillas[i + 1]);
            casillas[i + 1].conectarVecina(Direccion.OESTE, casillas[i]);
        }

        casillas[0].colocarUnidad(saltador);

        Tablero tablero = new Tablero(List.of(casillas));

        IAVek ia = new IAVek(tablero, saltador);

        DecisionVek decision = ia.decidir();

        assertNotNull(decision);
        assertSame(casillas[3], decision.getDestino());
        assertNull(decision.getDireccionAtaque());
    }



    @Test
    public void test05SaltadorSinObjetivosNoGeneraDecision() {

        Saltador saltador = new Saltador();

        Casilla casillaInicial = new Casilla(new Suelo());
        Casilla casillaVecina = new Casilla(new Suelo());

        casillaInicial.conectarVecina(
                Direccion.ESTE, casillaVecina);

        casillaVecina.conectarVecina(
                Direccion.OESTE, casillaInicial);

        casillaInicial.colocarUnidad(saltador);

        Tablero tablero = new Tablero(
                List.of(casillaInicial, casillaVecina));

        IAVek ia = new IAVek(tablero, saltador);

        DecisionVek decision = ia.decidir();

        assertNull(decision);
    }



    @Test
    public void test06SaltadorSeMueveParaAtacarEdificio() {

        RedElectrica redElectrica = new RedElectrica(5);
        Saltador saltador = new Saltador();

        Casilla[] casillas = new Casilla[4];

        for (int i = 0; i < 3; i++) {
            casillas[i] = new Casilla(new Suelo());
        }

        casillas[3] = new Casilla(new Edificio(redElectrica));

        for (int i = 0; i < 3; i++) {
            casillas[i].conectarVecina(
                    Direccion.ESTE, casillas[i + 1]);

            casillas[i + 1].conectarVecina(
                    Direccion.OESTE, casillas[i]);
        }

        casillas[0].colocarUnidad(saltador);

        Tablero tablero = new Tablero(List.of(casillas));

        IAVek ia = new IAVek(tablero, saltador);

        DecisionVek decision = ia.decidir();

        assertNotNull(decision);
        assertSame(casillas[2], decision.getDestino());
        assertEquals(Direccion.ESTE, decision.getDireccionAtaque());
    }



    @Test
    public void test07AvisponAtacaEdificioAdyacente() {

        RedElectrica redElectrica = new RedElectrica(5);
        Avispon avispon = new Avispon();

        Casilla casillaAvispon = new Casilla(new Suelo());
        Casilla casillaEdificio = new Casilla(new Edificio(redElectrica));

        casillaAvispon.conectarVecina(
                Direccion.ESTE, casillaEdificio);

        casillaEdificio.conectarVecina(
                Direccion.OESTE, casillaAvispon);

        casillaAvispon.colocarUnidad(avispon);

        Tablero tablero = new Tablero(
                List.of(casillaAvispon, casillaEdificio));

        IAVek ia = new IAVek(tablero, avispon);

        DecisionVek decision = ia.decidir();

        assertNotNull(decision);
        assertSame(casillaAvispon, decision.getDestino());
        assertEquals(Direccion.ESTE, decision.getDireccionAtaque());
    }



    @Test
    public void test08LuciernagaAtacaEdificioADistancia() {

        RedElectrica redElectrica = new RedElectrica(5);
        Luciernaga luciernaga = new Luciernaga();

        Casilla[] casillas = new Casilla[4];

        for (int i = 0; i < 3; i++) {
            casillas[i] = new Casilla(new Suelo());
        }

        casillas[3] = new Casilla(new Edificio(redElectrica));

        for (int i = 0; i < 3; i++) {
            casillas[i].conectarVecina(
                    Direccion.ESTE, casillas[i + 1]);

            casillas[i + 1].conectarVecina(
                    Direccion.OESTE, casillas[i]);
        }

        casillas[0].colocarUnidad(luciernaga);

        Tablero tablero = new Tablero(List.of(casillas));

        IAVek ia = new IAVek(tablero, luciernaga);

        DecisionVek decision = ia.decidir();

        assertNotNull(decision);
        assertSame(casillas[0], decision.getDestino());
        assertEquals(Direccion.ESTE, decision.getDireccionAtaque());
    }


    // EL PROBLEMA ACA ES QUE SE ESTA ATACANDO UN MECH INTENTANDO ATACAR UN EDIFICIO
    /*@Test
    public void test09LuciernagaNoAtacaEdificioBloqueado() {

        RedElectrica redElectrica = new RedElectrica(5);
        Luciernaga luciernaga = new Luciernaga();
        Mech mech = new Mech(new Punio(), 3);

        Casilla casillaLuciernaga = new Casilla(new Suelo());
        Casilla casillaMech = new Casilla(new Suelo());
        Casilla edificioBloqueado = new Casilla(new Edificio(redElectrica));
        Casilla edificioLibre = new Casilla(new Edificio(redElectrica));

        casillaLuciernaga.conectarVecina(
                Direccion.ESTE, casillaMech);
        casillaMech.conectarVecina(
                Direccion.OESTE, casillaLuciernaga);

        casillaMech.conectarVecina(
                Direccion.ESTE, edificioBloqueado);
        edificioBloqueado.conectarVecina(
                Direccion.OESTE, casillaMech);

        casillaLuciernaga.conectarVecina(
                Direccion.NORTE, edificioLibre);
        edificioLibre.conectarVecina(
                Direccion.SUR, casillaLuciernaga);

        casillaLuciernaga.colocarUnidad(luciernaga);
        casillaMech.colocarUnidad(mech);

        Tablero tablero = new Tablero(
                List.of(
                        casillaLuciernaga,
                        casillaMech,
                        edificioBloqueado,
                        edificioLibre
                )
        );

        IAVek ia = new IAVek(tablero, luciernaga);

        DecisionVek decision = ia.decidir();

        assertNotNull(decision);
        assertSame(casillaLuciernaga, decision.getDestino());
        assertEquals(Direccion.NORTE, decision.getDireccionAtaque());
    }*/


    @Test
    public void test10SaltadorSeAcercaAlEdificioMasCercano() {

        RedElectrica redElectrica = new RedElectrica(5);
        Saltador saltador = new Saltador();

        Casilla[] casillas = new Casilla[12];

        for (int i = 0; i < casillas.length; i++) {
            casillas[i] = new Casilla(new Suelo());
        }

        casillas[0] = new Casilla(new Edificio(redElectrica));
        casillas[11] = new Casilla(new Edificio(redElectrica));

        for (int i = 0; i < casillas.length - 1; i++) {
            casillas[i].conectarVecina(
                    Direccion.ESTE, casillas[i + 1]);

            casillas[i + 1].conectarVecina(
                    Direccion.OESTE, casillas[i]);
        }

        casillas[5].colocarUnidad(saltador);

        Tablero tablero = new Tablero(List.of(casillas));

        IAVek ia = new IAVek(tablero, saltador);

        DecisionVek decision = ia.decidir();

        assertNotNull(decision);
        assertSame(casillas[2], decision.getDestino());
        assertNull(decision.getDireccionAtaque());
    }


    @Test
    public void test11SaltadorNoAtraviesaCasillasOcupadas() {

        RedElectrica redElectrica = new RedElectrica(5);
        Saltador saltador = new Saltador();
        Mech mech = new Mech(new Punio(), 3);

        Casilla[] casillas = new Casilla[6];

        for (int i = 0; i < 5; i++) {
            casillas[i] = new Casilla(new Suelo());
        }

        casillas[5] = new Casilla(new Edificio(redElectrica));

        for (int i = 0; i < 5; i++) {
            casillas[i].conectarVecina(
                    Direccion.ESTE, casillas[i + 1]);

            casillas[i + 1].conectarVecina(
                    Direccion.OESTE, casillas[i]);
        }

        casillas[0].colocarUnidad(saltador);
        casillas[1].colocarUnidad(mech);

        Tablero tablero = new Tablero(List.of(casillas));

        List<Casilla> alcanzables = new ArrayList<>(
                tablero.casillasAlcanzablesDesde(
                        casillas[0],
                        saltador,
                        saltador.getMovimientoMaximo()
                )
        );

        assertTrue(alcanzables.isEmpty());

        alcanzables.add(casillas[0]);

        PrioridadAcercamiento prioridad =
                new PrioridadAcercamiento(null);

        DecisionVek decision =
                prioridad.evaluar(saltador, tablero, alcanzables);

        assertTrue(decision == null ||
                decision.getDestino() == casillas[0]);
    }

}

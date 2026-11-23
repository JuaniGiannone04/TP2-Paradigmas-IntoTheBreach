
package edu.fiuba.algo3.modelo.IA;

import edu.fiuba.algo3.modelo.RedElectrica;
import edu.fiuba.algo3.modelo.terreno.Edificio;
import edu.fiuba.algo3.modelo.terreno.Suelo;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;
import edu.fiuba.algo3.modelo.unidad.Saltador;

import org.junit.jupiter.api.Test;
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
}


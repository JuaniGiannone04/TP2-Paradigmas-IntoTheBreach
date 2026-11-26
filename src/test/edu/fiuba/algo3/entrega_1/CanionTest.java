package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.RedElectrica;
import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.arma.Canion;
import edu.fiuba.algo3.modelo.terreno.Edificio;
import edu.fiuba.algo3.modelo.terreno.Montana;
import edu.fiuba.algo3.modelo.terreno.Suelo;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.unidad.Luciernaga;
import edu.fiuba.algo3.modelo.unidad.Unidad;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CanionTest {

    @Test
    public void test01CanionRestaUnPuntoDeVidaALaPrimeraUnidadEnLineaRecta() {
        Casilla origen = new Casilla(new Suelo());
        Casilla intermedia = new Casilla(new Suelo());
        Casilla ocupada = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.ESTE, intermedia);
        intermedia.conectarVecina(Direccion.ESTE, ocupada);

        Unidad vek = new Luciernaga();
        ocupada.colocarUnidad(vek);
        Arma canion = new Canion();

        canion.atacar(origen, ocupada);

        assertEquals(2, vek.getVidaActual());
    }

    @Test
    public void test02CanionEmpujaUnaCasillaALaPrimeraUnidadImpactada() {
        Casilla origen = new Casilla(new Suelo());
        Casilla ocupada = new Casilla(new Suelo());
        Casilla empuje = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.ESTE, ocupada);
        ocupada.conectarVecina(Direccion.ESTE, empuje);

        Unidad vek = new Luciernaga();
        ocupada.colocarUnidad(vek);
        Arma canion = new Canion();

        canion.atacar(origen, ocupada);

        assertSame(vek, empuje.getUnidad());
    }

    @Test
    public void test03CanionNoDaniaALaUnidadQueEstaDetrasDeLaPrimeraImpactada() {
        Casilla origen = new Casilla(new Suelo());
        Casilla primera = new Casilla(new Suelo());
        Casilla empuje = new Casilla(new Suelo());
        Casilla segunda = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.ESTE, primera);
        primera.conectarVecina(Direccion.ESTE, empuje);
        empuje.conectarVecina(Direccion.ESTE, segunda);

        Unidad segundaVek = new Luciernaga();
        primera.colocarUnidad(new Luciernaga());
        segunda.colocarUnidad(segundaVek);
        Arma canion = new Canion();

        canion.atacar(origen, segunda);

        assertEquals(3, segundaVek.getVidaActual());
    }

    @Test
    public void test04CanionSeDetieneEnUnaMontanaYNoDaniaALaUnidadDetras() {
        Casilla origen = new Casilla(new Suelo());
        Casilla montana = new Casilla(new Montana());
        Casilla detras = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.SUR, montana);
        montana.conectarVecina(Direccion.SUR, detras);

        Unidad vekDetras = new Luciernaga();
        detras.colocarUnidad(vekDetras);
        Arma canion = new Canion();

        canion.atacar(origen, detras);

        assertEquals(3, vekDetras.getVidaActual());
    }

    @Test
    public void test05CanionImpactaUnEdificioYReduceLaRedElectricaEnUnPunto() {
        RedElectrica redElectrica = new RedElectrica(5);
        Casilla origen = new Casilla(new Suelo());
        Casilla intermedia = new Casilla(new Suelo());
        Casilla edificio = new Casilla(new Edificio(redElectrica));
        origen.conectarVecina(Direccion.ESTE, intermedia);
        intermedia.conectarVecina(Direccion.ESTE, edificio);

        Arma canion = new Canion();

        canion.atacar(origen, edificio);

        assertEquals(4, redElectrica.getEnergia());
    }

    @Test
    public void test06CanionEmpujaUnidadContraMontanaYAplicaDanioDeColision() {
        Casilla origen = new Casilla(new Suelo());
        Casilla ocupada = new Casilla(new Suelo());
        Casilla montana = new Casilla(new Montana());
        origen.conectarVecina(Direccion.NORTE, ocupada);
        ocupada.conectarVecina(Direccion.NORTE, montana);

        Unidad vek = new Luciernaga();
        ocupada.colocarUnidad(vek);
        Arma canion = new Canion();

        canion.atacar(origen, ocupada);

        assertEquals(1, vek.getVidaActual());
    }

    @Test
    public void test07CanionEmpujaUnidadContraBordeDelMapaSinAplicarDanioDeColision() {
        Casilla origen = new Casilla(new Suelo());
        Casilla borde = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.OESTE, borde);

        Unidad vek = new Luciernaga();
        borde.colocarUnidad(vek);
        Arma canion = new Canion();

        canion.atacar(origen, borde);

        assertEquals(2, vek.getVidaActual());
    }

    @Test
    public void test08CanionConDisparoLetalEliminaLaUnidadDeLaCasilla() {
        Casilla origen = new Casilla(new Suelo());
        Casilla ocupada = new Casilla(new Suelo());
        Casilla detras = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.ESTE, ocupada);
        ocupada.conectarVecina(Direccion.ESTE, detras);

        Unidad vekHerido = new Luciernaga();
        vekHerido.recibirDanio(2);
        ocupada.colocarUnidad(vekHerido);
        Arma canion = new Canion();

        canion.atacar(origen, ocupada);

        assertFalse(ocupada.estaOcupada());
    }

    @Test
    public void test09CanionLanzaExcepcionSiElObjetivoEstaEnDiagonal() {
        Casilla origen = new Casilla(new Suelo());
        Casilla este = new Casilla(new Suelo());
        Casilla diagonal = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.ESTE, este);
        este.conectarVecina(Direccion.NORTE, diagonal);

        Arma canion = new Canion();

        assertThrows(IllegalArgumentException.class, () -> canion.atacar(origen, diagonal));
    }

    @Test
    public void test10CanionDisparandoAlVacioSeAbsorbeEnElBordeDelMapaSinError() {
        Casilla origen = new Casilla(new Suelo());
        Casilla vacia = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.OESTE, vacia);

        Arma canion = new Canion();

        assertDoesNotThrow(() -> canion.atacar(origen, vacia));
    }
}
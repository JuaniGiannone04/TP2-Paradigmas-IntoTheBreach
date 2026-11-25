package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.RedElectrica;
import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.arma.Punio;
import edu.fiuba.algo3.modelo.terreno.Edificio;
import edu.fiuba.algo3.modelo.terreno.Montana;
import edu.fiuba.algo3.modelo.terreno.Suelo;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.unidad.Luciernaga;
import edu.fiuba.algo3.modelo.unidad.Saltador;
import edu.fiuba.algo3.modelo.unidad.Unidad;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PunioTest {

    @Test
    public void test01PunioRestaDosPuntosDeVidaALaUnidadAdyacente() {
        Casilla origen = new Casilla(new Suelo());
        Casilla objetivo = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.ESTE, objetivo);

        Unidad vek = new Luciernaga();
        objetivo.colocarUnidad(vek);
        Arma punio = new Punio();

        punio.atacar(origen, objetivo);

        assertEquals(1, vek.getVidaActual());
    }

    @Test
    public void test02PunioEmpujaUnidadAdyacenteUnaCasillaEnLaDireccionDelGolpe() {
        Casilla origen = new Casilla(new Suelo());
        Casilla objetivo = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.ESTE, objetivo);
        objetivo.conectarVecina(Direccion.ESTE, destino);

        Unidad vek = new Luciernaga();
        objetivo.colocarUnidad(vek);
        Arma punio = new Punio();

        punio.atacar(origen, objetivo);

        assertSame(vek, destino.getUnidad());
    }

    @Test
    public void test03PunioDejaVaciaLaCasillaOriginalTrasEmpujarALaUnidad() {
        Casilla origen = new Casilla(new Suelo());
        Casilla objetivo = new Casilla(new Suelo());
        Casilla empuje = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.ESTE, objetivo);
        objetivo.conectarVecina(Direccion.ESTE, empuje);

        objetivo.colocarUnidad(new Luciernaga());
        Arma punio = new Punio();

        punio.atacar(origen, objetivo);

        assertFalse(objetivo.estaOcupada());
    }

    @Test
    public void test04PunioLanzaExcepcionSiElObjetivoNoEsAdyacente() {
        Casilla origen = new Casilla(new Suelo());
        Casilla intermedia = new Casilla(new Suelo());
        Casilla lejana = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.ESTE, intermedia);
        intermedia.conectarVecina(Direccion.ESTE, lejana);

        Arma punio = new Punio();

        assertThrows(IllegalArgumentException.class, () -> punio.atacar(origen, lejana));
    }

    @Test
    public void test05PunioLanzaExcepcionSiAtacaASuPropiaCasilla() {
        Casilla origen = new Casilla(new Suelo());
        Arma punio = new Punio();

        assertThrows(IllegalArgumentException.class, () -> punio.atacar(origen, origen));
    }

    @Test
    public void test06PunioEmpujaUnidadContraMontanaYAplicaDanioExtraDeColision() {
        Casilla origen = new Casilla(new Suelo());
        Casilla objetivo = new Casilla(new Suelo());
        Casilla montana = new Casilla(new Montana());
        origen.conectarVecina(Direccion.NORTE, objetivo);
        objetivo.conectarVecina(Direccion.NORTE, montana);

        Unidad vek = new Luciernaga();
        objetivo.colocarUnidad(vek);
        Arma punio = new Punio();

        punio.atacar(origen, objetivo);

        assertEquals(0, vek.getVidaActual());
    }

    @Test
    public void test07PunioEmpujaUnidadContraBordeDelMapaSinMoverla() {
        Casilla origen = new Casilla(new Suelo());
        Casilla borde = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.SUR, borde);

        Unidad vek = new Luciernaga();
        borde.colocarUnidad(vek);
        Arma punio = new Punio();

        punio.atacar(origen, borde);

        assertSame(vek, borde.getUnidad());
    }

    @Test
    public void test08PunioEmpujaUnidadContraBordeDelMapaSinAplicarDanioDeColision() {
        Casilla origen = new Casilla(new Suelo());
        Casilla borde = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.SUR, borde);

        Unidad vek = new Luciernaga();
        borde.colocarUnidad(vek);
        Arma punio = new Punio();

        punio.atacar(origen, borde);

        assertEquals(1, vek.getVidaActual());
    }

    @Test
    public void test09PunioConGolpeLetalLimpiaLaCasillaYNoEmpujaElCadaver() {
        Casilla origen = new Casilla(new Suelo());
        Casilla objetivo = new Casilla(new Suelo());
        Casilla detras = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.OESTE, objetivo);
        objetivo.conectarVecina(Direccion.OESTE, detras);

        objetivo.colocarUnidad(new Saltador());
        Arma punio = new Punio();

        punio.atacar(origen, objetivo);

        assertFalse(detras.estaOcupada());
    }

    @Test
    public void test10PunioGolpeaUnEdificioYReduceLaRedElectricaEnUnPunto() {
        RedElectrica redElectrica = new RedElectrica(5);
        Casilla origen = new Casilla(new Suelo());
        Casilla casillaEdificio = new Casilla(new Edificio(redElectrica));
        origen.conectarVecina(Direccion.NORTE, casillaEdificio);

        Arma punio = new Punio();

        punio.atacar(origen, casillaEdificio);

        assertEquals(4, redElectrica.getEnergia());
    }
}
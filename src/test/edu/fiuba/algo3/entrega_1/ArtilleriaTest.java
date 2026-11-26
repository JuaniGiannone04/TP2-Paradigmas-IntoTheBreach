package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.arma.Artilleria;
import edu.fiuba.algo3.modelo.terreno.Montana;
import edu.fiuba.algo3.modelo.terreno.Suelo;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.unidad.Luciernaga;
import edu.fiuba.algo3.modelo.unidad.Unidad;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArtilleriaTest {

    @Test
    public void test01ArtilleriaRestaUnPuntoDeVidaALaUnidadEnLaCasillaObjetivo() {
        Casilla origen = new Casilla(new Suelo());
        Casilla puente = new Casilla(new Suelo());
        Casilla objetivo = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.ESTE, puente);
        puente.conectarVecina(Direccion.ESTE, objetivo);

        Unidad vek = new Luciernaga();
        objetivo.colocarUnidad(vek);
        Arma artilleria = new Artilleria();

        artilleria.atacar(origen, objetivo);

        assertEquals(2, vek.getVidaActual());
    }

    @Test
    public void test02ArtilleriaNoEmpujaALaUnidadUbicadaEnElCentroDelImpacto() {
        Casilla origen = new Casilla(new Suelo());
        Casilla puente = new Casilla(new Suelo());
        Casilla objetivo = new Casilla(new Suelo());
        Casilla detras = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.ESTE, puente);
        puente.conectarVecina(Direccion.ESTE, objetivo);
        objetivo.conectarVecina(Direccion.ESTE, detras);

        Unidad vek = new Luciernaga();
        objetivo.colocarUnidad(vek);
        Arma artilleria = new Artilleria();

        artilleria.atacar(origen, objetivo);

        assertSame(vek, objetivo.getUnidad());
    }

    @Test
    public void test03ArtilleriaAtacaPorEncimaDeUnaMontanaSinSerBloqueada() {
        Casilla origen = new Casilla(new Suelo());
        Casilla montana = new Casilla(new Montana());
        Casilla objetivo = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.ESTE, montana);
        montana.conectarVecina(Direccion.ESTE, objetivo);

        Unidad vek = new Luciernaga();
        objetivo.colocarUnidad(vek);
        Arma artilleria = new Artilleria();

        artilleria.atacar(origen, objetivo);

        assertEquals(2, vek.getVidaActual());
    }

    @Test
    public void test04ArtilleriaEmpujaHaciaAfueraAUnidadAdyacenteAlNorteDelObjetivo() {
        Casilla origen = new Casilla(new Suelo());
        Casilla puente = new Casilla(new Suelo());
        Casilla centro = new Casilla(new Suelo());
        Casilla norte = new Casilla(new Suelo());
        Casilla norteEmpuje = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, puente);
        puente.conectarVecina(Direccion.ESTE, centro);
        centro.conectarVecina(Direccion.NORTE, norte);
        norte.conectarVecina(Direccion.NORTE, norteEmpuje);

        Unidad vekNorte = new Luciernaga();
        norte.colocarUnidad(vekNorte);
        Arma artilleria = new Artilleria();

        artilleria.atacar(origen, centro);

        assertSame(vekNorte, norteEmpuje.getUnidad());
    }

    @Test
    public void test05ArtilleriaEmpujaHaciaAfueraAUnidadAdyacenteAlSurDelObjetivo() {
        Casilla origen = new Casilla(new Suelo());
        Casilla puente = new Casilla(new Suelo());
        Casilla centro = new Casilla(new Suelo());
        Casilla sur = new Casilla(new Suelo());
        Casilla surEmpuje = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, puente);
        puente.conectarVecina(Direccion.ESTE, centro);
        centro.conectarVecina(Direccion.SUR, sur);
        sur.conectarVecina(Direccion.SUR, surEmpuje);

        Unidad vekSur = new Luciernaga();
        sur.colocarUnidad(vekSur);
        Arma artilleria = new Artilleria();

        artilleria.atacar(origen, centro);

        assertSame(vekSur, surEmpuje.getUnidad());
    }

    @Test
    public void test06ArtilleriaEmpujaHaciaAfueraAUnidadAdyacenteAlEsteDelObjetivo() {
        Casilla origen = new Casilla(new Suelo());
        Casilla puente = new Casilla(new Suelo());
        Casilla centro = new Casilla(new Suelo());
        Casilla este = new Casilla(new Suelo());
        Casilla esteEmpuje = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, puente);
        puente.conectarVecina(Direccion.ESTE, centro);
        centro.conectarVecina(Direccion.ESTE, este);
        este.conectarVecina(Direccion.ESTE, esteEmpuje);

        Unidad vekEste = new Luciernaga();
        este.colocarUnidad(vekEste);
        Arma artilleria = new Artilleria();

        artilleria.atacar(origen, centro);

        assertSame(vekEste, esteEmpuje.getUnidad());
    }

    @Test
    public void test07ArtilleriaEmpujaHaciaAfueraAUnidadAdyacenteAlOesteDelObjetivo() {
        Casilla origen = new Casilla(new Suelo());
        Casilla puente = new Casilla(new Suelo());
        Casilla centro = new Casilla(new Suelo());
        Casilla oeste = new Casilla(new Suelo());
        Casilla oesteEmpuje = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.NORTE, puente);
        puente.conectarVecina(Direccion.NORTE, centro);
        centro.conectarVecina(Direccion.OESTE, oeste);
        oeste.conectarVecina(Direccion.OESTE, oesteEmpuje);

        Unidad vekOeste = new Luciernaga();
        oeste.colocarUnidad(vekOeste);
        Arma artilleria = new Artilleria();

        artilleria.atacar(origen, centro);

        assertSame(vekOeste, oesteEmpuje.getUnidad());
    }

    @Test
    public void test08ArtilleriaNoDaniaDirectamenteALasUnidadesAdyacentesEmpujadas() {
        Casilla origen = new Casilla(new Suelo());
        Casilla puente = new Casilla(new Suelo());
        Casilla centro = new Casilla(new Suelo());
        Casilla norte = new Casilla(new Suelo());
        Casilla norteEmpuje = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, puente);
        puente.conectarVecina(Direccion.ESTE, centro);
        centro.conectarVecina(Direccion.NORTE, norte);
        norte.conectarVecina(Direccion.NORTE, norteEmpuje);

        Unidad vekNorte = new Luciernaga();
        norte.colocarUnidad(vekNorte);
        Arma artilleria = new Artilleria();

        artilleria.atacar(origen, centro);

        assertEquals(3, vekNorte.getVidaActual());
    }

    @Test
    public void test09ArtilleriaSobreCasillaVaciaEmpujaAdyacenteContraObstaculoYAplicaColision() {
        Casilla origen = new Casilla(new Suelo());
        Casilla puente = new Casilla(new Suelo());
        Casilla centroVacio = new Casilla(new Suelo());
        Casilla adyacente = new Casilla(new Suelo());
        Casilla montana = new Casilla(new Montana());

        origen.conectarVecina(Direccion.NORTE, puente);
        puente.conectarVecina(Direccion.NORTE, centroVacio);
        centroVacio.conectarVecina(Direccion.ESTE, adyacente);
        adyacente.conectarVecina(Direccion.ESTE, montana);

        Unidad vekAdyacente = new Luciernaga();
        adyacente.colocarUnidad(vekAdyacente);
        Arma artilleria = new Artilleria();

        artilleria.atacar(origen, centroVacio);

        assertEquals(2, vekAdyacente.getVidaActual());
    }

    @Test
    public void test10ArtilleriaLanzaExcepcionSiElObjetivoEsAdyacente() {
        Casilla origen = new Casilla(new Suelo());
        Casilla adyacente = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.NORTE, adyacente);

        Arma artilleria = new Artilleria();

        assertThrows(IllegalArgumentException.class, () -> artilleria.atacar(origen, adyacente));
    }

    @Test
    public void test11ArtilleriaLanzaExcepcionSiElObjetivoEstaEnDiagonal() {
        Casilla origen = new Casilla(new Suelo());
        Casilla este = new Casilla(new Suelo());
        Casilla diagonal = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.ESTE, este);
        este.conectarVecina(Direccion.SUR, diagonal);

        Arma artilleria = new Artilleria();

        assertThrows(IllegalArgumentException.class, () -> artilleria.atacar(origen, diagonal));
    }

    @Test
    public void test12ArtilleriaEnElBordeDelMapaAbsorbeOndaExpansivaExteriorSinError() {
        Casilla origen = new Casilla(new Suelo());
        Casilla puente = new Casilla(new Suelo());
        Casilla esquinaBorde = new Casilla(new Suelo());
        origen.conectarVecina(Direccion.NORTE, puente);
        puente.conectarVecina(Direccion.NORTE, esquinaBorde);

        Arma artilleria = new Artilleria();

        assertDoesNotThrow(() -> artilleria.atacar(origen, esquinaBorde));
    }
}
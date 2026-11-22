package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.arma.Artilleria;
import edu.fiuba.algo3.modelo.arma.Canion;
import edu.fiuba.algo3.modelo.arma.Punio;
import edu.fiuba.algo3.modelo.terreno.Montana;
import edu.fiuba.algo3.modelo.terreno.Suelo;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.unidad.Luciernaga;
import edu.fiuba.algo3.modelo.unidad.Mech;
import edu.fiuba.algo3.modelo.unidad.Saltador;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AtaquesIntegracionTest {

    @Test
    public void test01PunioDania2YEmpuja1CasillaHaciaAtras() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        Casilla empuje = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, destino);
        destino.conectarVecina(Direccion.ESTE, empuje);

        Mech mech = new Mech(new Punio(), 3);
        Luciernaga vek = new Luciernaga();
        origen.colocarUnidad(mech);
        destino.colocarUnidad(vek);

        mech.atacar(origen, destino);

        assertEquals(1, vek.getVidaActual());
        assertFalse(destino.estaOcupada());
        assertSame(vek, empuje.getUnidad());
    }

    @Test
    public void test02CanionImpactaAlPrimerVekEnLineaRectaYLoEmpuja() {
        Casilla origen = new Casilla(new Suelo());
        Casilla vacia = new Casilla(new Suelo());
        Casilla ocupada = new Casilla(new Suelo());
        Casilla empuje = new Casilla(new Suelo());
        Casilla destinoLejano = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, vacia);
        vacia.conectarVecina(Direccion.ESTE, ocupada);
        ocupada.conectarVecina(Direccion.ESTE, empuje);
        empuje.conectarVecina(Direccion.ESTE, destinoLejano);

        Mech mech = new Mech(new Canion(), 3);
        Saltador vek = new Saltador();
        origen.colocarUnidad(mech);
        ocupada.colocarUnidad(vek);

        mech.atacar(origen, destinoLejano);

        assertEquals(1, vek.getVidaActual());
        assertFalse(ocupada.estaOcupada());
        assertSame(vek, empuje.getUnidad());
    }

    @Test
    public void test03ArtilleriaDaniaCentroYEmpujaHaciaAfuera() {
        Casilla origen = new Casilla(new Suelo());
        Casilla puente = new Casilla(new Suelo());
        Casilla centro = new Casilla(new Suelo());

        Casilla vecinaNorte = new Casilla(new Suelo());
        Casilla empujeNorte = new Casilla(new Suelo());

        Casilla vecinaEste = new Casilla(new Suelo());
        Casilla empujeEste = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.NORTE, puente);
        puente.conectarVecina(Direccion.NORTE, centro);

        centro.conectarVecina(Direccion.NORTE, vecinaNorte);
        vecinaNorte.conectarVecina(Direccion.NORTE, empujeNorte);

        centro.conectarVecina(Direccion.ESTE, vecinaEste);
        vecinaEste.conectarVecina(Direccion.ESTE, empujeEste);

        Mech mech = new Mech(new Artilleria(), 2);
        Luciernaga vekCentro = new Luciernaga();
        Luciernaga vekNorte = new Luciernaga();
        Luciernaga vekEste = new Luciernaga();

        origen.colocarUnidad(mech);
        centro.colocarUnidad(vekCentro);
        vecinaNorte.colocarUnidad(vekNorte);
        vecinaEste.colocarUnidad(vekEste);

        mech.atacar(origen, centro);

        assertEquals(2, vekCentro.getVidaActual());
        assertSame(vekCentro, centro.getUnidad());

        assertEquals(3, vekNorte.getVidaActual());
        assertFalse(vecinaNorte.estaOcupada());
        assertSame(vekNorte, empujeNorte.getUnidad());

        assertEquals(3, vekEste.getVidaActual());
        assertFalse(vecinaEste.estaOcupada());
        assertSame(vekEste, empujeEste.getUnidad());
    }

    @Test
    public void test04ColisionContraMontanaDestruyeAlVek() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        Casilla montana = new Casilla(new Montana());

        origen.conectarVecina(Direccion.ESTE, destino);
        destino.conectarVecina(Direccion.ESTE, montana);

        Mech mech = new Mech(new Punio(), 3);
        Luciernaga vek = new Luciernaga();

        origen.colocarUnidad(mech);
        destino.colocarUnidad(vek);

        mech.atacar(origen, destino);

        assertEquals(0, vek.getVidaActual());
        assertFalse(destino.estaOcupada());
        assertFalse(montana.estaOcupada());
    }

}
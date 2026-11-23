/*package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.arma.Punio;
import edu.fiuba.algo3.modelo.terreno.Suelo;
import edu.fiuba.algo3.modelo.turno.AtaqueAnunciado;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.unidad.Mech;
import edu.fiuba.algo3.modelo.unidad.Saltador;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AtaqueAnunciadoTest {

    @Test
    public void test01EmpujarVekActualizaSuPosicion() {

        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, destino);
        destino.conectarVecina(Direccion.OESTE, origen);

        Saltador saltador = new Saltador();
        origen.colocarUnidad(saltador);

        origen.empujarUnidadInterna(Direccion.ESTE);

        assertFalse(origen.estaOcupada());
        assertSame(saltador, destino.getUnidad());
        assertSame(destino, saltador.getCasillaActual());
    }

    @Test
    public void test02AtaqueAnunciadoSeDesplazaConVekEmpujado() {

        Casilla origen = new Casilla(new Suelo());
        Casilla intermedia = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, intermedia);
        intermedia.conectarVecina(Direccion.OESTE, origen);

        intermedia.conectarVecina(Direccion.ESTE, destino);
        destino.conectarVecina(Direccion.OESTE, intermedia);

        Saltador atacante = new Saltador();
        Saltador objetivo = new Saltador();

        origen.colocarUnidad(atacante);
        destino.colocarUnidad(objetivo);

        AtaqueAnunciado ataque = new AtaqueAnunciado(
                atacante, Direccion.ESTE
        );

        origen.empujarUnidadInterna(Direccion.ESTE);

        assertSame(intermedia, atacante.getCasillaActual());

        ataque.ejecutar();

        assertEquals(0, objetivo.getVidaActual());
    }

    @Test
    public void test03RetirarVekDeSuCasillaLeImpideEjecutarSuAtaqueAnunciado(){
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, destino);

        Mech mech = new Mech(new Punio(), 3);
        Saltador saltador = new Saltador();

        origen.colocarUnidad(saltador);
        destino.colocarUnidad(mech);

        AtaqueAnunciado ataque = new AtaqueAnunciado(saltador, Direccion.ESTE);

        origen.quitarUnidad();
        ataque.ejecutar();

        assertEquals(3, mech.getVidaActual());
        assertFalse(origen.estaOcupada());

    }






}
*/
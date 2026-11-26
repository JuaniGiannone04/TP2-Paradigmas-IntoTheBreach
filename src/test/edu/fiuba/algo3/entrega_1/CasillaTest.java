package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.arma.Punio;
import edu.fiuba.algo3.modelo.terreno.Montana;
import edu.fiuba.algo3.modelo.terreno.Suelo;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.unidad.Luciernaga;
import edu.fiuba.algo3.modelo.unidad.Mech;
import edu.fiuba.algo3.modelo.unidad.Saltador;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CasillaTest {

    @Test
    public void test01UnidadDestruidaPorColisionDejaLibreSuCasilla(){
        Casilla origen = new Casilla(new Suelo());
        Casilla montana = new Casilla(new Montana());

        Saltador saltador = new Saltador();

        origen.colocarUnidad(saltador);
        origen.conectarVecina(Direccion.ESTE, montana);

        origen.recibirDanio(1);
        origen.empujarUnidadInterna(Direccion.ESTE);

        assertEquals(0, saltador.getVidaActual());
        assertFalse(origen.estaOcupada());
        assertFalse(montana.estaOcupada());

    }

    @Test
    public void test05UnidadEmpujadaContraMontanaNoSeDesplaza(){
        Casilla origen = new Casilla(new Suelo());
        Casilla montana = new Casilla(new Montana());

        origen.conectarVecina(Direccion.ESTE, montana);

        Saltador saltador = new Saltador();
        origen.colocarUnidad(saltador);

        origen.empujarUnidadInterna(Direccion.ESTE);

        assertEquals(1, saltador.getVidaActual());
        assertSame(saltador, origen.getUnidad());
        assertFalse(montana.estaOcupada());
    }

}
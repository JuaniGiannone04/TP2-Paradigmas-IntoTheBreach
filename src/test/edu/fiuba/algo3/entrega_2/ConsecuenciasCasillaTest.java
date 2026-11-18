package edu.fiuba.algo3.entrega_2;

import edu.fiuba.algo3.modelo.RedElectrica;
import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.arma.Canion;
import edu.fiuba.algo3.modelo.terreno.*;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.unidad.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConsecuenciasCasillaTest {
@Test
public void test01EmpujarUnidadContraOtraUnidadNoLaMueveYAplicaDanioDeColisionAAmbas() {
    Casilla casillaOrigen = new Casilla(new Suelo());
    Casilla casillaOcupada = new Casilla(new Suelo());
    casillaOrigen.conectarVecina(Direccion.ESTE, casillaOcupada);

    Unidad unidadEmpujada = new Luciernaga();
    Unidad unidadQuieta = new Saltador();
    casillaOrigen.colocarUnidad(unidadEmpujada);
    casillaOcupada.colocarUnidad(unidadQuieta);

    casillaOrigen.empujarUnidadInterna(Direccion.ESTE);

    assertSame(unidadEmpujada, casillaOrigen.getUnidad());
    assertSame(unidadQuieta, casillaOcupada.getUnidad());
    assertEquals(2, unidadEmpujada.getVidaActual());
    assertEquals(1, unidadQuieta.getVidaActual());
}

    @Test
    public void test02EmpujarUnidadContraObstaculoNoLaMueveYAplicaDanioDeColision() {
        Casilla casillaOrigen = new Casilla(new Suelo());
        Casilla casillaMontana = new Casilla(new Montana());
        casillaOrigen.conectarVecina(Direccion.NORTE, casillaMontana);

        Unidad unidadEmpujada = new Luciernaga();
        casillaOrigen.colocarUnidad(unidadEmpujada);

        casillaOrigen.empujarUnidadInterna(Direccion.NORTE);

        assertSame(unidadEmpujada, casillaOrigen.getUnidad());
        assertEquals(2, unidadEmpujada.getVidaActual());
    }

    @Test
    public void test03DaniarUnEdificioReduceLaRedElectricaEnUnPunto() {
        RedElectrica redElectrica = new RedElectrica(5);
        Casilla casillaEdificio = new Casilla(new Edificio(redElectrica));

        casillaEdificio.recibirDanio(2);

        assertEquals(4, redElectrica.getEnergia());
    }

    @Test
    public void test04EmpujarUnidadContraEdificioReduceLaRedElectricaEnUnPunto() {
        RedElectrica redElectrica = new RedElectrica(5);
        Casilla casillaOrigen = new Casilla(new Suelo());
        Casilla casillaEdificio = new Casilla(new Edificio(redElectrica));
        casillaOrigen.conectarVecina(Direccion.SUR, casillaEdificio);

        Unidad vek = new Luciernaga();
        casillaOrigen.colocarUnidad(vek);

        casillaOrigen.empujarUnidadInterna(Direccion.SUR);

        assertEquals(4, redElectrica.getEnergia());
        assertEquals(2, vek.getVidaActual());
        assertSame(vek, casillaOrigen.getUnidad());
    }

    @Test
    public void test05VekTerrestreEmpujadoAlAguaMuereInmediatamente() {
        Casilla casillaSuelo = new Casilla(new Suelo());
        Casilla casillaAgua = new Casilla(new Agua());
        casillaSuelo.conectarVecina(Direccion.ESTE, casillaAgua);

        Unidad vekTerrestre = new Luciernaga();
        casillaSuelo.colocarUnidad(vekTerrestre);

        casillaSuelo.empujarUnidadInterna(Direccion.ESTE);

        assertEquals(0, vekTerrestre.getVidaActual());
    }
    @Test
    public void test06VekTerrestreEmpujadoAlAguaMuereInmediatamente() {
        Casilla casillaSuelo = new Casilla(new Suelo());
        Casilla casillaAgua = new Casilla(new Agua());
        casillaSuelo.conectarVecina(Direccion.ESTE, casillaAgua);

        Unidad vekTerrestre = new Saltador();
        casillaSuelo.colocarUnidad(vekTerrestre);

        casillaSuelo.empujarUnidadInterna(Direccion.ESTE);

        assertEquals(0, vekTerrestre.getVidaActual());
    }
    @Test
    public void test07VekAereoEmpujadoAlAguaMuereInmediatamente() {
        Casilla casillaSuelo = new Casilla(new Suelo());
        Casilla casillaAgua = new Casilla(new Agua());
        casillaSuelo.conectarVecina(Direccion.ESTE, casillaAgua);

        Unidad vekTerrestre = new Avispon();
        casillaSuelo.colocarUnidad(vekTerrestre);

        casillaSuelo.empujarUnidadInterna(Direccion.ESTE);

        assertNotEquals(0, vekTerrestre.getVidaActual());
    }
    @Test
    public void test08MechNoDisparaDesdeElAgua() {
        Casilla casillaSuelo = new Casilla(new Suelo());
        Casilla casillaAgua = new Casilla(new Agua());
        casillaSuelo.conectarVecina(Direccion.ESTE, casillaAgua);
        casillaAgua.conectarVecina(Direccion.OESTE,casillaSuelo);
        Arma canion=new Canion();
        Unidad mech = new Mech(canion,2);
        Unidad vekTerrestre = new Avispon();
        casillaAgua.colocarUnidad(mech);
        casillaSuelo.colocarUnidad(vekTerrestre);
        assertThrows(IllegalStateException .class, () -> mech.atacar(casillaAgua, casillaSuelo));
    }

    @Test
    public void test08UnidadTerrestreQueTerminaSobreAcidoQuedaContaminadaYRecibeDobleDeDanio() {
        Casilla casillaOrigen = new Casilla(new Suelo());
        Casilla casillaAcido = new Casilla(new Acido());
        casillaOrigen.conectarVecina(Direccion.ESTE, casillaAcido);
        casillaAcido.conectarVecina(Direccion.OESTE,casillaOrigen );

        Unidad vekTerrestre = new Luciernaga();
        casillaAcido.colocarUnidad(vekTerrestre);
        vekTerrestre.recibirDanio(1);

        assertEquals(1, vekTerrestre.getVidaActual());
    }
    @Test
    public void test09UnidadAereaQueTerminaSobreAcidoNoQuedaContaminada() {
        Casilla casillaOrigen = new Casilla(new Suelo());
        Casilla casillaAcido = new Casilla(new Acido());
        casillaOrigen.conectarVecina(Direccion.ESTE, casillaAcido);
        casillaAcido.conectarVecina(Direccion.OESTE,casillaOrigen );

        Unidad vekTerrestre = new Avispon();
        casillaAcido.colocarUnidad(vekTerrestre);
        vekTerrestre.recibirDanio(1);

        assertEquals(1, vekTerrestre.getVidaActual());
    }


}

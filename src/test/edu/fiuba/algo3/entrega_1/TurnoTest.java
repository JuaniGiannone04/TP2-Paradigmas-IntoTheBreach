package edu.fiuba.algo3.entrega_1;

import com.sun.javafx.scene.control.ControlAcceleratorSupport;
import edu.fiuba.algo3.modelo.GeneradorDeTablero;
import edu.fiuba.algo3.modelo.RedElectrica;
import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.arma.Punio;
import edu.fiuba.algo3.modelo.terreno.Acido;
import edu.fiuba.algo3.modelo.terreno.Edificio;
import edu.fiuba.algo3.modelo.terreno.Montana;
import edu.fiuba.algo3.modelo.terreno.Suelo;
import edu.fiuba.algo3.modelo.turno.*;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;
import edu.fiuba.algo3.modelo.unidad.Luciernaga;
import edu.fiuba.algo3.modelo.unidad.Mech;
import edu.fiuba.algo3.modelo.unidad.Saltador;
import edu.fiuba.algo3.modelo.unidad.Vek;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TurnoTest {

    private Turno crearTurno(Casilla ... casillas){
        if(casillas.length == 0){
            return new Turno(new GeneradorDeTablero().crearTablero());
        }
        return new Turno(new Tablero(List.of(casillas)));
    }


    @Test
    public void test01DeshacerMovimientoDeMechQueNoAtacoVuelveASuPosicionAnterior() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, destino);
        destino.conectarVecina(Direccion.OESTE, origen);

        Mech mech = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(mech);

        Turno turno = crearTurno(origen, destino);
        turno.avanzarFase();

        turno.moverMech(mech, origen, destino);
        turno.deshacerMovimiento(mech);

        assertSame(mech, origen.getUnidad());
        assertFalse(destino.estaOcupada());
    }

    @Test
    public void test02NoSePuedeDeshacerMovimientoDeMechQueYaAtaco() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        Casilla objetivo = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, destino);
        destino.conectarVecina(Direccion.OESTE, origen);

        Mech mech = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(mech);

        Turno turno = crearTurno(origen, destino, objetivo);
        turno.avanzarFase();

        turno.moverMech(mech, origen, destino);
        turno.atacarConMech(mech, destino, objetivo);

        assertThrows(IllegalStateException.class, () -> turno.deshacerMovimiento(mech));
        assertSame(mech, destino.getUnidad());
    }

    @Test
    public void test03NoSePuedeDeshacerDosVecesEnElMismoTurno() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino1 = new Casilla(new Suelo());
        Casilla destino2 = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, destino1);
        destino1.conectarVecina(Direccion.OESTE, origen);
        origen.conectarVecina(Direccion.OESTE, destino2);
        destino2.conectarVecina(Direccion.ESTE, origen);

        Mech mech = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(mech);

        Turno turno = crearTurno(origen, destino1, destino2);
        turno.avanzarFase();

        turno.moverMech(mech, origen, destino1);
        turno.deshacerMovimiento(mech);
        turno.moverMech(mech, origen, destino2);

        assertThrows(IllegalStateException.class, () -> turno.deshacerMovimiento(mech));
    }

    @Test
    public void test04ElTurnoEmpiezaEnFaseVek() {
        Turno turno = crearTurno();

        assertTrue(turno.obtenerFaseActual() instanceof FaseVek);
    }

    @Test
    public void test05AvanzarFaseCambiaDeFaseVekAFaseJugador() {
        Turno turno = crearTurno();

        turno.avanzarFase();

        assertTrue(turno.obtenerFaseActual() instanceof FaseJugador);
    }

    @Test
    public void test06AvanzarFaseCambiaDeFaseJugadorAFaseResolucion() {
        Turno turno = crearTurno();
        turno.avanzarFase();
        turno.avanzarFase();

        assertTrue(turno.obtenerFaseActual() instanceof FaseResolucion);
    }

    @Test
    public void test07AvanzarFaseCambiaDeFaseResolucionAFaseEmergencia() {
        Turno turno = crearTurno();
        turno.avanzarFase();
        turno.avanzarFase();

        turno.avanzarFase();

        assertTrue(turno.obtenerFaseActual() instanceof FaseEmergencia);
    }

    @Test
    public void test08AlNuevoTurnoSePuedeDeshacerDeNuevo() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, destino);
        destino.conectarVecina(Direccion.OESTE, origen);

        Mech mech = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(mech);

        Turno turno = crearTurno(origen, destino);

        turno.avanzarFase();
        turno.moverMech(mech, origen, destino);
        turno.deshacerMovimiento(mech);

        for (int i = 0; i < 4; i++) {
            turno.avanzarFase();
        }
        turno.moverMech(mech, origen, destino);
        turno.deshacerMovimiento(mech);

        assertSame(mech, origen.getUnidad());
    }

    @Test
    public void test09AlTerminarUnCicloCompletoPasaAlSiguienteTurno() {
        Turno turno = crearTurno();
        assertEquals(1, turno.obtenerNumero());

        for (int i = 0; i < 4; i++) {
            turno.avanzarFase();
        }

        assertEquals(2, turno.obtenerNumero());
    }

    @Test
    public void test10NoSePuedeDeshacerFueraDeLaFaseDelJugador() {
        Casilla origen = new Casilla(new Suelo());

        Mech mech = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(mech);

        Turno turno = crearTurno(origen);

        assertThrows(IllegalStateException.class, () -> turno.deshacerMovimiento(mech));
    }

    @Test
    public void test11NoSePuedeDeshacerSiElMechNoSeMovio() {
        Casilla origen = new Casilla(new Suelo());

        Mech mech = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(mech);

        Turno turno = crearTurno(origen);
        turno.avanzarFase();

        assertThrows(IllegalStateException.class, () -> turno.deshacerMovimiento(mech));
    }

    @Test
    public void test12NoSePuedeMoverUnMechQueYaAtaco() {
        Casilla origen = new Casilla(new Suelo());
        Casilla objetivo = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        Mech mech = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(mech);

        Turno turno = crearTurno(origen, objetivo, destino);
        turno.avanzarFase();

        turno.atacarConMech(mech, origen, objetivo);

        assertThrows(IllegalStateException.class, () -> turno.moverMech(mech, origen, destino));
    }

    @Test
    public void test13NoSePuedeMoverUnMechEnFaseVek() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        Mech mech = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(mech);

        Turno turno = crearTurno(origen, destino);

        assertThrows(IllegalStateException.class, () -> turno.moverMech(mech, origen, destino));
    }

    @Test
    public void test14DeshacerMovimientoSobreAcidoQuitaLaContaminacion() {
        Casilla origen = new Casilla(new Suelo());
        Casilla acido = new Casilla(new Acido());

        origen.conectarVecina(Direccion.ESTE, acido);
        acido.conectarVecina(Direccion.OESTE, origen);

        Mech mech = new Mech(new Punio(), 3);
        origen.colocarUnidad(mech);

        Turno turno = crearTurno(origen, acido);
        turno.avanzarFase();
        turno.moverMech(mech, origen, acido);
        assertTrue(mech.estaContaminado());

        turno.deshacerMovimiento(mech);

        assertFalse(mech.estaContaminado());
    }

    @Test
    public void test15DeshacerNoQuitaUnaContaminacionAnterior() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, destino);
        destino.conectarVecina(Direccion.OESTE, origen);

        Mech mech = new Mech(new Punio(), 3);
        mech.aplicarEfectoAcido();
        origen.colocarUnidad(mech);

        Turno turno = crearTurno(origen, destino);
        turno.avanzarFase();
        turno.moverMech(mech, origen, destino);
        turno.deshacerMovimiento(mech);

        assertTrue(mech.estaContaminado());
    }

    @Test
    public void test16DeshacerMovimientoEsRechazadoPorAtaqueYNoPierdeElMovimientoPendiente() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());
        Casilla objetivo = new Casilla(new Suelo());
        Casilla otroOrigen = new Casilla(new Suelo());
        Casilla otroDestino = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, destino);
        destino.conectarVecina(Direccion.OESTE, origen);
        destino.conectarVecina(Direccion.ESTE, objetivo);

        otroOrigen.conectarVecina(Direccion.NORTE, otroDestino);
        otroDestino.conectarVecina(Direccion.SUR, otroOrigen);

        Mech primero = new Mech(mock(Arma.class), 3);
        Mech segundo = new Mech(mock(Arma.class), 3);
        origen.colocarUnidad(primero);
        otroOrigen.colocarUnidad(segundo);

        Turno turno = crearTurno(origen, destino, objetivo, otroOrigen, otroDestino);
        turno.avanzarFase();
        turno.moverMech(primero, origen, destino);
        turno.atacarConMech(primero, destino, objetivo);
        turno.moverMech(segundo, otroOrigen, otroDestino);

        assertThrows(IllegalStateException.class, () -> turno.deshacerMovimiento(primero));

        turno.deshacerMovimiento(segundo);

        assertSame(segundo, otroOrigen.getUnidad());
        assertFalse(otroDestino.estaOcupada());
    }

    @Test
    public void test17VekAnunciaSuAtaqueEnLaFaseVek() {
        Casilla vekCasilla = new Casilla(new Suelo());
        Casilla mechCasilla = new Casilla(new Suelo());

        vekCasilla.conectarVecina(Direccion.ESTE, mechCasilla);
        mechCasilla.conectarVecina(Direccion.OESTE, vekCasilla);

        Vek vek = new Saltador();
        vekCasilla.colocarUnidad(vek);

        mechCasilla.colocarUnidad(new Mech(mock(Arma.class), 3));

        Turno turno = crearTurno(vekCasilla, mechCasilla);
        turno.registrarAtaque(vek.anunciaAtaque(vekCasilla));

        assertEquals(1, turno.cantidadDeAtaquesAnunciados());
    }

    @Test
    public void test18NoSePuedeAnunciarAtaqueFueraDeLaFaseVek() {
        Casilla vekCasilla = new Casilla(new Suelo());
        Casilla mechCasilla = new Casilla(new Suelo());

        vekCasilla.conectarVecina(Direccion.ESTE, mechCasilla);
        mechCasilla.conectarVecina(Direccion.OESTE, vekCasilla);

        Vek vek = new Saltador();
        vekCasilla.colocarUnidad(vek);

        mechCasilla.colocarUnidad(new Mech(mock(Arma.class), 3));

        AtaqueAnunciado ataque = vek.anunciaAtaque(vekCasilla);

        Turno turno = crearTurno(vekCasilla, mechCasilla);
        turno.avanzarFase();

        assertThrows(IllegalStateException.class, () -> turno.registrarAtaque(ataque));
    }

    @Test
    public void test19VekAnunciaAtaqueHaciaElMech() {
        Casilla vekCasilla = new Casilla(new Suelo());
        Casilla mechCasilla = new Casilla(new Suelo());
        vekCasilla.conectarVecina(Direccion.ESTE, mechCasilla);
        mechCasilla.conectarVecina(Direccion.OESTE, vekCasilla);

        Vek vek = new Saltador();
        vekCasilla.colocarUnidad(vek);
        Mech mech = new Mech(mock(Arma.class), 3);
        mechCasilla.colocarUnidad(mech);

        AtaqueAnunciado ataque = vek.anunciaAtaque(vekCasilla);

        assertEquals(Direccion.ESTE, ataque.getDireccion());
    }

    @Test
    public void test20VekAnunciaAtaqueHaciaElMechQueEstaAlOeste() {
        Casilla mechCasilla = new Casilla(new Suelo());
        Casilla vekCasilla = new Casilla(new Suelo());
        mechCasilla.conectarVecina(Direccion.ESTE, vekCasilla);
        vekCasilla.conectarVecina(Direccion.OESTE, mechCasilla);

        Vek vek = new Saltador();
        vekCasilla.colocarUnidad(vek);
        Mech mech = new Mech(mock(Arma.class), 3);
        mechCasilla.colocarUnidad(mech);

        AtaqueAnunciado ataque = vek.anunciaAtaque(vekCasilla);

        assertEquals(Direccion.OESTE, ataque.getDireccion());
    }

    @Test
    public void test21FaseResolucionEjecutaLosAtaquesEnElOrdenAnunciado() {
        Casilla casillaA = new Casilla(new Suelo());
        Casilla mechA = new Casilla(new Suelo());

        casillaA.conectarVecina(Direccion.ESTE, mechA);
        mechA.conectarVecina(Direccion.OESTE, casillaA);

        Vek primero = new Saltador();
        casillaA.colocarUnidad(primero);

        mechA.colocarUnidad(new Mech(mock(Arma.class), 3));

        Casilla casillaB = new Casilla(new Suelo());
        Casilla mechB = new Casilla(new Suelo());

        casillaB.conectarVecina(Direccion.OESTE, mechB);
        mechB.conectarVecina(Direccion.ESTE, casillaB);

        Vek segundo = new Luciernaga();
        casillaB.colocarUnidad(segundo);

        mechB.colocarUnidad(new Mech(mock(Arma.class), 3));

        Turno turno = crearTurno(casillaA, mechA, casillaB, mechB);
        turno.registrarAtaque(primero.anunciaAtaque(casillaA));
        turno.registrarAtaque(segundo.anunciaAtaque(casillaB));
        turno.avanzarFase();
        turno.avanzarFase();

        List<Vek> ejecutados = turno.ejecutarResolucion();

        assertEquals(List.of(primero, segundo), ejecutados);
    }

    @Test
    public void test22FaseResolucionEjecutaElAtaqueAnunciadoYHaceDanio() {
        Casilla vekCasilla = new Casilla(new Suelo());
        Casilla mechCasilla = new Casilla(new Suelo());

        vekCasilla.conectarVecina(Direccion.ESTE, mechCasilla);
        mechCasilla.conectarVecina(Direccion.OESTE, vekCasilla);

        Vek vek = new Saltador();
        vekCasilla.colocarUnidad(vek);

        Mech mech = new Mech(new Punio(), 3);
        mechCasilla.colocarUnidad(mech);
        int vidaInicialMech = mech.getVidaActual();

        Turno turno = crearTurno(vekCasilla, mechCasilla);
        turno.registrarAtaque(vek.anunciaAtaque(vekCasilla));
        turno.avanzarFase();
        turno.avanzarFase();

        turno.ejecutarResolucion();

        assertEquals(vidaInicialMech - 2, mech.getVidaActual());
    }

    @Test
    public void test23TurnoMueveAlVekHaciaElEdificioEnFaseVek() {
        Casilla origen = new Casilla(new Suelo());
        Casilla intermedia = new Casilla(new Suelo());
        Casilla edificio = new Casilla(new Edificio(new RedElectrica(5)));

        origen.conectarVecina(Direccion.ESTE, intermedia);
        intermedia.conectarVecina(Direccion.OESTE, origen);
        intermedia.conectarVecina(Direccion.ESTE, edificio);
        edificio.conectarVecina(Direccion.OESTE, intermedia);

        Vek vek = new Saltador();
        origen.colocarUnidad(vek);

        Turno turno = crearTurno(origen, intermedia, edificio);
        turno.moverVek(origen);

        assertSame(vek, intermedia.getUnidad());
    }

    @Test
    public void test24NoSePuedeMoverUnMechDesdeLaCasillaDeOtro(){
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, destino);
        destino.conectarVecina(Direccion.OESTE, origen);

        Mech mechSeleccionado = new  Mech(new Punio(), 3);
        Mech mechEnOrigen = new Mech(new Punio(), 3);

        origen.colocarUnidad(mechEnOrigen);

        Turno turno = crearTurno(origen, destino);
        turno.avanzarFase();

        assertThrows(IllegalStateException.class,
                ()-> turno.moverMech(mechSeleccionado, origen,  destino));

        assertSame(mechEnOrigen, origen.getUnidad());
        assertFalse(destino.estaOcupada());

        turno.moverMech(mechEnOrigen, origen, destino);

        assertSame(mechEnOrigen, destino.getUnidad());
        assertFalse(origen.estaOcupada());
    }

    @Test
    public void test25RechazarDeshacerConservaElMovimientoRegistrado(){
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, destino);
        destino.conectarVecina(Direccion.OESTE, origen);

        Mech mech = new Mech(new Punio(), 3);

        origen.colocarUnidad(mech);

        Turno turno = crearTurno(origen, destino);
        turno.avanzarFase();

        turno.moverMech(mech,  origen, destino);
        turno.deshacerMovimiento(mech);
        turno.moverMech(mech,  origen, destino);

        assertThrows(IllegalStateException.class, ()-> turno.deshacerMovimiento(mech));
        assertSame(mech, destino.getUnidad());
        assertFalse(origen.estaOcupada());

        assertThrows(IllegalStateException.class, ()-> turno.moverMech(mech, destino, origen));
        assertSame(mech, destino.getUnidad());
        assertFalse(origen.estaOcupada());

    }

    @Test
    public void test26NoSePuedeAtacarDesdeLaCasillaDeOtroMech(){
        Casilla origenReal = new Casilla(new Suelo());
        Casilla origenAjeno = new Casilla(new Suelo());
        Casilla objetivo = new Casilla(new Suelo());

        origenReal.conectarVecina(Direccion.ESTE, objetivo);
        objetivo.conectarVecina(Direccion.OESTE, origenReal);

        Mech mechSeleccionado = new Mech(new Punio(), 3);
        Mech otroMech = new Mech(new Punio(), 3);
        Saltador vek =  new Saltador();

        origenReal.colocarUnidad(mechSeleccionado);
        origenAjeno.colocarUnidad(otroMech);
        objetivo.colocarUnidad(vek);

        Turno turno = crearTurno(origenReal, origenAjeno, objetivo);
        turno.avanzarFase();

        assertThrows(IllegalStateException.class, () ->
                turno.atacarConMech(mechSeleccionado, origenAjeno, objetivo));

        assertEquals(2, vek.getVidaActual());

        turno.atacarConMech(mechSeleccionado, origenReal, objetivo);

        assertFalse(objetivo.estaOcupada());
    }

    @Test
    public void test27MechNoPuedeMoverseMasDeTresCasillas(){
        Casilla origen = new Casilla(new Suelo());
        Casilla distancia1 = new Casilla(new Suelo());
        Casilla distancia2 = new Casilla(new Suelo());
        Casilla distancia3 = new Casilla(new Suelo());
        Casilla distancia4 = new Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, distancia1);
        distancia1.conectarVecina(Direccion.ESTE, distancia2);
        distancia2.conectarVecina(Direccion.OESTE, distancia3);
        distancia3.conectarVecina(Direccion.ESTE, distancia4);

        Mech mech = new Mech(new Punio(), 3);
        origen.colocarUnidad(mech);

        Turno turno = crearTurno(origen, distancia1, distancia2, distancia3, distancia4);
        turno.avanzarFase();

        assertThrows(IllegalStateException.class,
                () -> turno.moverMech(mech, origen, distancia4));

        assertSame(mech, origen.getUnidad());

        turno.moverMech(mech, origen, distancia3);

        assertSame(mech, distancia3.getUnidad());
        assertFalse(origen.estaOcupada());
    }

    @Test
    public void test28MechNoPuedeAtravesarObstaculos(){
        Casilla origen = new Casilla(new Suelo());
        Casilla obstaculo = new Casilla(new Montana());
        Casilla destino = new  Casilla(new Suelo());

        origen.conectarVecina(Direccion.ESTE, obstaculo);
        obstaculo.conectarVecina(Direccion.OESTE, destino);

        Mech mech = new Mech(new Punio(), 3);
        origen.colocarUnidad(mech);

        Turno turno = crearTurno(origen, obstaculo, destino);
        turno.avanzarFase();

        assertThrows(IllegalStateException.class,
                () ->  turno.moverMech(mech, origen, destino));

        assertSame(mech, origen.getUnidad());
        assertFalse(destino.estaOcupada());
    }


}
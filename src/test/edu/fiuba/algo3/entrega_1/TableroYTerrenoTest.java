package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.GeneradorDeTablero;
import edu.fiuba.algo3.modelo.arma.Punio;
import edu.fiuba.algo3.modelo.terreno.Acido;
import edu.fiuba.algo3.modelo.terreno.Agua;
import edu.fiuba.algo3.modelo.terreno.Montana;
import edu.fiuba.algo3.modelo.terreno.Suelo;
import edu.fiuba.algo3.modelo.ubicacion.*;
import edu.fiuba.algo3.modelo.unidad.Mech;
import edu.fiuba.algo3.modelo.unidad.Saltador;
import edu.fiuba.algo3.modelo.unidad.Unidad;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TableroYTerrenoTest {

    @Test
    public void test03ColocarUnidadEnAcidoOAguaDisparaElEfectoDelTerreno() {
        Unidad unidadMock = mock(Unidad.class);
        Casilla casillaAcido = new Casilla(new Acido());
        Casilla casillaAgua = new Casilla(new Agua());

        casillaAcido.colocarUnidad(unidadMock);
        verify(unidadMock, times(1)).aplicarEfectoAcido();

        casillaAgua.colocarUnidad(unidadMock);
        verify(unidadMock, times(1)).aplicarEfectoAgua();
    }

    @Test
    public void test04NoSePuedeColocarUnaUnidadEnUnaCasillaYaOcupada() {
        Unidad unidad1 = mock(Unidad.class);
        when(unidad1.getVidaActual()).thenReturn(3);
        Unidad unidad2 = mock(Unidad.class);
        when(unidad2.getVidaActual()).thenReturn(3);
        Casilla casilla = new Casilla(new Suelo());

        casilla.colocarUnidad(unidad1);

        assertThrows(IllegalStateException.class, () -> {
            casilla.colocarUnidad(unidad2);
        });
    }

    @Test
    public void test05MoverUnidadEntreCasillasActualizaOrigenYDestino() {
        Unidad unidadMock = mock(Unidad.class);
        when(unidadMock.getVidaActual()).thenReturn(3);
        when(unidadMock.puedeTransitar(any())).thenReturn(true);
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        origen.colocarUnidad(unidadMock);
        origen.moverUnidadHacia(destino);

        assertFalse(origen.estaOcupada());
        assertTrue(destino.estaOcupada());
        assertSame(unidadMock, destino.getUnidad());
    }

    @Test
    public void test06EmpujarUnidadHaciaElBordeDelMapaNoLaMueveNiLaDania() {
        GeneradorDeTablero fabrica = new GeneradorDeTablero();
        Tablero tablero = fabrica.crearTablero();

        Casilla esquinaNoroeste = (Casilla) tablero.getCasillaInicial();
        Unidad unidadMock = mock(Unidad.class);
        when(unidadMock.getVidaActual()).thenReturn(3);
        esquinaNoroeste.colocarUnidad(unidadMock);

        esquinaNoroeste.empujarUnidadInterna(Direccion.NORTE);

        assertSame(unidadMock, esquinaNoroeste.getUnidad());
        verify(unidadMock, never()).recibirDanio(anyInt());
    }

    @Test
    public void test07MoverHaciaCasillaOcupadaConservaAmbasUnidades() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Suelo());

        Mech mechOrigen = new Mech(new Punio(), 3);
        Mech mechDestino = new Mech(new Punio(), 3);

        origen.colocarUnidad(mechOrigen);
        destino.colocarUnidad(mechDestino);

        assertThrows(IllegalStateException.class,
                () -> origen.moverUnidadHacia(destino));

        assertSame(mechOrigen, origen.getUnidad());
        assertSame(mechDestino, destino.getUnidad());
    }

    @Test
    public void test08NoSePuedeMoverUnMechHaciaUnaMontana() {
        Casilla origen = new Casilla(new Suelo());
        Casilla destino = new Casilla(new Montana());

        Mech mech = new Mech(new Punio(), 3);

        origen.colocarUnidad(mech);

        assertThrows(IllegalStateException.class,
                () -> origen.moverUnidadHacia(destino));

        assertSame(mech, origen.getUnidad());
        assertFalse(destino.estaOcupada());
    }

    @Test
    public void test09TableroCalculaCasillasAlcanzablesSegunMovimiento(){
        Tablero tablero = new GeneradorDeTablero().crearTablero();
        Casilla origen = tablero.getCasillaInicial();

        Celda tresPasos = origen.vecinaAl(Direccion.ESTE).vecinaAl(Direccion.ESTE).vecinaAl(Direccion.ESTE);
        Celda cuatroPasos = tresPasos.vecinaAl(Direccion.ESTE);

        Saltador saltador = new Saltador();
        origen.colocarUnidad(saltador);

        List<Casilla> alcanzables = tablero.casillasAlcanzablesDesde(origen, saltador, saltador.getMovimientoMaximo());

        assertTrue(alcanzables.contains(tresPasos));
        assertFalse(alcanzables.contains(cuatroPasos));
    }

    @Test
    public void test10TableroPermiteaUnidadAlcanzarDestinoATravesDeObstaculos(){
        Tablero tablero = new GeneradorDeTablero().crearTablero();
        Casilla origen = tablero.getCasillaInicial();

        Casilla bloqueada = (Casilla) origen.vecinaAl(Direccion.ESTE);
        Celda destino = origen.vecinaAl(Direccion.SUR).vecinaAl(Direccion.ESTE);

        Saltador saltador = new Saltador();
        origen.colocarUnidad(saltador);
        bloqueada.colocarUnidad(new Saltador());

        List<Casilla> alcanzables = tablero.casillasAlcanzablesDesde(origen, saltador, saltador.getMovimientoMaximo());

        assertTrue(alcanzables.contains(destino));
        assertFalse(alcanzables.contains(bloqueada));
    }

    @Test
    public void test11TableroEncuentraUnidadDespuesQueSeMueva(){
        Tablero tablero = new GeneradorDeTablero().crearTablero();

        Casilla origen = tablero.getCasillaInicial();
        Casilla destino = (Casilla) origen.vecinaAl(Direccion.ESTE);

        Saltador saltador = new  Saltador();
        origen.colocarUnidad(saltador);

        assertEquals(Optional.of(origen), tablero.buscarCasillaDe(saltador));

        origen.moverUnidadHacia(destino);

        assertEquals(Optional.of(destino), tablero.buscarCasillaDe(saltador));
    }
}
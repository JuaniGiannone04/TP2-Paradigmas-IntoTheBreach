package edu.fiuba.algo3.entrega_1;

import edu.fiuba.algo3.modelo.*;
import edu.fiuba.algo3.modelo.arma.Artilleria;
import edu.fiuba.algo3.modelo.terreno.Montana;
import edu.fiuba.algo3.modelo.ubicacion.Posicion;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;
import edu.fiuba.algo3.modelo.unidad.Luciernaga;
import edu.fiuba.algo3.modelo.unidad.Mech;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArtilleriaTest {

    @Test
    public void test01ArtilleriaDaniaCentroYEmpujaUnidadesAdyacentesHaciaAfuera() {
        Tablero tablero = new Tablero();
        Mech mechArtilleria = new Mech(new Artilleria(), 2);

        Luciernaga vekCentro = new Luciernaga(); // Vida inicial: 3
        Luciernaga vekNorte = new Luciernaga();
        Luciernaga vekSur = new Luciernaga();
        Luciernaga vekEste = new Luciernaga();
        Luciernaga vekOeste = new Luciernaga();

        Posicion posMech = new Posicion(4, 0);
        Posicion posCentro = new Posicion(4, 4);

        tablero.colocarUnidad(posMech, mechArtilleria);
        tablero.colocarUnidad(posCentro, vekCentro);
        tablero.colocarUnidad(new Posicion(3, 4), vekNorte); // Norte
        tablero.colocarUnidad(new Posicion(5, 4), vekSur);   // Sur
        tablero.colocarUnidad(new Posicion(4, 5), vekEste);  // Este
        tablero.colocarUnidad(new Posicion(4, 3), vekOeste); // Oeste

        // Act
        mechArtilleria.atacar(posMech, posCentro, tablero);

        // Assert - Centro
        assertEquals(2, vekCentro.getVidaActual());
        assertEquals(vekCentro, tablero.obtenerCasilla(posCentro).getUnidad());

        // Assert - Adyacentes empujados hacia afuera sin recibir daño de explosión
        assertEquals(3, vekNorte.getVidaActual());
        assertEquals(vekNorte, tablero.obtenerCasilla(new Posicion(2, 4)).getUnidad());

        assertEquals(3, vekSur.getVidaActual());
        assertEquals(vekSur, tablero.obtenerCasilla(new Posicion(6, 4)).getUnidad());

        assertEquals(3, vekEste.getVidaActual());
        assertEquals(vekEste, tablero.obtenerCasilla(new Posicion(4, 6)).getUnidad());

        assertEquals(3, vekOeste.getVidaActual());
        assertEquals(vekOeste, tablero.obtenerCasilla(new Posicion(4, 2)).getUnidad());
    }

    @Test
    public void test02ArtilleriaLanzaExcepcionSiIntentaAtacarACasillaAdyacente() {
        Tablero tablero = new Tablero();
        Mech mechArtilleria = new Mech(new Artilleria(), 2);

        Posicion posMech = new Posicion(3, 3);
        Posicion posAdyacente = new Posicion(3, 4); // Distancia 1

        tablero.colocarUnidad(posMech, mechArtilleria);

        // Assert: No dispara a ad6yacentes
        assertThrows(IllegalArgumentException.class, () -> {
            mechArtilleria.atacar(posMech, posAdyacente, tablero);
        });
    }

    @Test
    public void test03ArtilleriaLanzaExcepcionSiNoAtacaEnLineaRecta() {
        Tablero tablero = new Tablero();
        Mech mechArtilleria = new Mech(new Artilleria(), 2);

        Posicion posMech = new Posicion(3, 3);
        Posicion posDiagonal = new Posicion(5, 5); // No está en línea recta

        tablero.colocarUnidad(posMech, mechArtilleria);

        assertThrows(IllegalArgumentException.class, () -> {
            mechArtilleria.atacar(posMech, posDiagonal, tablero);
        });
    }

    @Test
    public void test04ArtilleriaAtacaPorEncimaDeObstaculosEIgnoraUnidadesIntermedias() {
        Tablero tablero = new Tablero();
        Mech mechArtilleria = new Mech(new Artilleria(), 2);
        Luciernaga vekObjetivo = new Luciernaga();

        Posicion posMech = new Posicion(1, 1);
        Posicion posMontana = new Posicion(1, 2);
        Posicion posVekIntermedio = new Posicion(1, 3);
        Posicion posDestino = new Posicion(1, 5);

        tablero.colocarUnidad(posMech, mechArtilleria);
        tablero.configurarTerreno(posMontana, new Montana());
        tablero.colocarUnidad(posVekIntermedio, new Luciernaga());
        tablero.colocarUnidad(posDestino, vekObjetivo);

        // Act
        mechArtilleria.atacar(posMech, posDestino, tablero);

        // Assert: El disparo pasó por encima de la montaña y la unidad intermedia sin afectarlos
        assertEquals(2, vekObjetivo.getVidaActual());
    }

    @Test
    public void test05ArtilleriaEmpujaUnidadAdyacenteContraObstaculoCausandoColision() {
        Tablero tablero = new Tablero();
        Mech mechArtilleria = new Mech(new Artilleria(), 2);

        Luciernaga vekEste = new Luciernaga(); // Unidad que será empujada

        Posicion posMech = new Posicion(2, 0);
        Posicion posCentro = new Posicion(2, 3); // Dispara acá
        Posicion posEste = new Posicion(2, 4); // Acá está el Vek
        Posicion posObstaculo = new Posicion(2, 5); // Acá hay una montaña

        tablero.colocarUnidad(posMech, mechArtilleria);
        tablero.colocarUnidad(posEste, vekEste);
        tablero.configurarTerreno(posObstaculo, new Montana());

        // Act
        mechArtilleria.atacar(posMech, posCentro, tablero);

        // Assert
        // El Vek en el Este fue empujado hacia (2, 5), pero chocó con la montaña.
        // Regla: No se mueve, y recibe 1 de daño de colisión
        assertEquals(vekEste, tablero.obtenerCasilla(posEste).getUnidad());
        assertEquals(2, vekEste.getVidaActual());
    }
}
package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GeneradorVistaTablero {
    private static final int DIMENSION = 8;

    public VistaTablero generar(Tablero tablero) {
        List<Casilla> casillas = tablero.obtenerCasillas();
        Map<Casilla, VistaCasilla> asociacion = new LinkedHashMap<>();

        for (int i = 0; i < casillas.size(); i++) {
            int fila = i / DIMENSION;
            int columna = i % DIMENSION;
            Casilla casillaModelo = casillas.get(i);

            VistaCasilla vistaCasilla = new VistaCasilla(casillaModelo, fila, columna);
            asociacion.put(casillaModelo, vistaCasilla);
        }

        return new VistaTablero(asociacion);
    }
}
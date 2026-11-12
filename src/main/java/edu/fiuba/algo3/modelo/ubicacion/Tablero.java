package edu.fiuba.algo3.modelo.ubicacion;

import edu.fiuba.algo3.modelo.terreno.Terreno;
import edu.fiuba.algo3.modelo.unidad.Unidad;
import edu.fiuba.algo3.modelo.terreno.Suelo;

import java.util.HashMap;
import java.util.Map;


public class Tablero {
    public static final int DIMENSION = 8;
    private final Map<Posicion, Casilla> casillas;

    public Tablero() {
        this.casillas = new HashMap<>();
        inicializarConSuelo();
    }

    public Tablero(Map<Posicion, Casilla> casillasPredefinidas) {
        this.casillas = new HashMap<>(casillasPredefinidas);
    }

    private void inicializarConSuelo() {
        for (int fila = 0; fila < DIMENSION; fila++) {
            for (int col = 0; col < DIMENSION; col++) {
                Posicion pos = new Posicion(fila, col);
                this.casillas.put(pos, new Casilla(new Suelo()));
            }
        }
    }

    public boolean contienePosicion(Posicion posicion) {
        return this.casillas.containsKey(posicion);
    }

    public Casilla obtenerCasilla(Posicion posicion) {
        if (!contienePosicion(posicion)) {
            throw new IllegalArgumentException("La posición está fuera de los límites del tablero: " + posicion);
        }
        return this.casillas.get(posicion);
    }

    public void configurarTerreno(Posicion posicion, Terreno terreno) {
        obtenerCasilla(posicion).cambiarTerreno(terreno);
    }

    public void colocarUnidad(Posicion posicion, Unidad unidad) {
        obtenerCasilla(posicion).colocarUnidad(unidad);
    }

    public void moverUnidad(Posicion origen, Posicion destino) {
        Casilla casillaOrigen = obtenerCasilla(origen);
        Casilla casillaDestino = obtenerCasilla(destino);

        Unidad unidad = casillaOrigen.quitarUnidad();
        casillaDestino.colocarUnidad(unidad);
    }

    public int cantidadDeCasillas() {
        return this.casillas.size();
    }

    public void empujarUnidad(Posicion posOrigen, Posicion posDestino) {
        if (!contienePosicion(posOrigen) || !contienePosicion(posDestino)) {
            return;
        }
        Casilla casillaOrigen = obtenerCasilla(posOrigen);
        Casilla casillaDestino = obtenerCasilla(posDestino);
        if (!casillaOrigen.estaOcupada()) {
            return;
        }
        Unidad unidadEmpujada = casillaOrigen.quitarUnidad();
        boolean pudoEntrar = casillaDestino.recibirUnidadOColisionar(unidadEmpujada);
        if (!pudoEntrar) {
            casillaOrigen.colocarUnidad(unidadEmpujada);
        }
    }

    public void aplicarDanio(Posicion posicion, int danio) {
        if (contienePosicion(posicion)) {
            obtenerCasilla(posicion).recibirDanio(danio);
        }
    }

    public boolean detieneDisparoDirecto(Posicion posicion) {
        return contienePosicion(posicion) && obtenerCasilla(posicion).detieneDisparoDirecto();
    }
}


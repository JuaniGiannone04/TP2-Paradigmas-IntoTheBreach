package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.Posicion;
import edu.fiuba.algo3.modelo.Tablero;
import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.terreno.Terreno;

public class Mech implements Unidad {

    private final Arma arma;
    private int vidaActual;
    private boolean contaminado;
    private EstadoAccion estado;
    private Posicion posicionAnterior;
    private boolean yaDeshizoMovimiento;

    public Mech(Arma arma, int vidaInicial) {
        this.arma = arma;
        this.vidaActual = vidaInicial;
        this.contaminado = false;
        this.estado = EstadoAccion.DISPONIBLE;
        this.yaDeshizoMovimiento = false;
    }


    @Override
    public void recibirDanio(int danio) {
        int danioFinal = danio;

        if (contaminado) {
            danioFinal *= 2;
        }

        vidaActual = Math.max(0, vidaActual - danioFinal);
    }

    @Override
    public void aplicarEfectoAgua() {

    }

    @Override
    public void aplicarEfectoAcido() {
        this.contaminado = true;
    }

    @Override
    public boolean puedeTransitar(Terreno terreno) {
        return false;
    }

    @Override
    public int getVidaActual() {
        return this.vidaActual;
    }

    public void atacar(Posicion origen, Posicion destino, Tablero tablero) {
        if( estado == EstadoAccion.ATACO ) {
            throw new IllegalStateException("El Mech ya atacó en este turno");
        }

        arma.atacar(origen, destino, tablero);
        estado = EstadoAccion.ATACO;
    }

    public void mover(Posicion origen, Posicion destino, Tablero tablero) {

        if (estado != EstadoAccion.DISPONIBLE) {
            throw new IllegalStateException(
                    "El Mech ya se movió en este turno"
            );
        }

        if (origen.distanciaOrtogonalA(destino) > 3) {
            throw new IllegalArgumentException(
                    "El destino supera el rango de movimiento"
            );
        }

        tablero.moverUnidad(origen, destino);
        posicionAnterior = origen;
        estado = EstadoAccion.MOVIDO;
    }

    public void deshacerMovimiento(Posicion posicionActual, Tablero tablero) {

        if (estado != EstadoAccion.MOVIDO) {
            throw new IllegalStateException("El Mech no puede deshacer su movimiento");
        }

        if (yaDeshizoMovimiento) {
            throw new IllegalStateException("El Mech ya deshizo un movimiento");
        }

        tablero.moverUnidad(posicionActual, posicionAnterior);

        estado = EstadoAccion.DISPONIBLE;
        yaDeshizoMovimiento = true;
    }

}
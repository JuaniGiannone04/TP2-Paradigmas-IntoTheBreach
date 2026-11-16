package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.terreno.Terreno;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;

public class Mech implements Unidad {
    private final Arma arma;
    private int vidaActual;
    private boolean contaminado;
    private EstadoAccion estado;

    public Mech(Arma arma, int vidaInicial) {
        this.arma = arma;
        this.vidaActual = vidaInicial;
        this.contaminado = false;
        this.estado = EstadoAccion.DISPONIBLE;
    }

    @Override
    public void recibirDanio(int danio) {
        int danioFinal = this.contaminado ? danio * 2 : danio;
        this.vidaActual = Math.max(0, this.vidaActual - danioFinal);
    }

    @Override
    public void aplicarEfectoAgua() {
        this.estado= EstadoAccion.PACIFICO;
    }

    @Override
    public void aplicarEfectoAcido() {
        this.contaminado = true;
    }

    @Override
    public boolean puedeTransitar(Terreno terreno) {
        return terreno.esTransitablePorTerrestre();
    }

    @Override
    public int getVidaActual() {
        return this.vidaActual;
    }
    public void atacar(Celda origen, Celda destino) {

        if (estado == EstadoAccion.ATACO) {
            throw new IllegalStateException("El Mech ya atacó en este turno");
        }
        if (estado != estado.PACIFICO) {
            arma.atacar(origen, destino);
            estado = EstadoAccion.ATACO;
        }

    }

    public void mover(Casilla origen, Casilla destino) {
        if(estado != EstadoAccion.DISPONIBLE) {
            throw new IllegalStateException("El Mech ya se movió en este turno");
        }

        origen.moverUnidadHacia(destino);
        estado = EstadoAccion.MOVIDO;
    }

}
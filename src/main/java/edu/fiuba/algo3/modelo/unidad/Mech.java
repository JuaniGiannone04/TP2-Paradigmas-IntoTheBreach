package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.terreno.Terreno;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.unidad.estadosalud.Contaminado;
import edu.fiuba.algo3.modelo.unidad.estadosalud.EstadoSalud;
import edu.fiuba.algo3.modelo.unidad.estadosalud.Sano;

public class Mech implements Unidad {
    private final Arma arma;
    private int vidaActual;
    private EstadoSalud estadoSalud = new Sano();
    private EstadoAccion estado;

    public Mech(Arma arma, int vidaInicial) {
        this.arma = arma;
        this.vidaActual = vidaInicial;
        this.estado = EstadoAccion.DISPONIBLE;
    }

    @Override
    public void recibirDanio(int danio) {
        int danioFinal = this.estadoSalud.calcularDanio(danio);
        this.vidaActual = Math.max(0, this.vidaActual - danioFinal);
    }

    @Override
    public void aplicarEfectoAgua() {
        this.estado= EstadoAccion.PACIFICO;
    }

    @Override
    public void aplicarEfectoAcido() {this.estadoSalud = new Contaminado();}

    @Override
    public boolean puedeTransitar(Terreno terreno) {
        return terreno.esTransitablePorTerrestre();
    }

    @Override
    public int getVidaActual() {
        return this.vidaActual;
    }

    public void atacar(Celda origen, Celda destino) {
        if (this.estado == EstadoAccion.ATACO) {
            throw new IllegalStateException("El Mech ya ataco");
        }

        if (this.estado == EstadoAccion.PACIFICO) {
            throw new IllegalStateException("El Mech no puede atacar mientras esta en el agua");
        }

        this.arma.atacar(origen, destino);
        this.estado = EstadoAccion.ATACO;
    }

    public boolean estaContaminado() {
        return this.estadoSalud instanceof Contaminado;
    }

    public EstadoSalud obtenerEstadoSalud() {
        return this.estadoSalud;
    }

    public void restaurarEstadoSalud(EstadoSalud estado) {
        this.estadoSalud = estado;
    }
}
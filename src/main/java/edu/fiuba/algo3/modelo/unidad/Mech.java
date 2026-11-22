package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.terreno.Terreno;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;

public class Mech implements Unidad {
    private final Arma arma;
    private int vidaActual;
    private EstadoAccion estado;
    private EstadoSalud estadoSalud;
    private Casilla casillaActual;

    public Mech(Arma arma, int vidaInicial) {
        this.arma = arma;
        this.vidaActual = vidaInicial;
        this.estadoSalud = new Sano();
        this.estado = EstadoAccion.DISPONIBLE;
    }

    @Override
    public void recibirDanio(int danio) {
        int danioFinal = this.estadoSalud.calcularDanioFinal(danio);
        this.vidaActual = Math.max(0, this.vidaActual - danioFinal);
    }

    @Override
    public void aplicarEfectoAgua() {
        this.estado= EstadoAccion.PACIFICO;
    }

    @Override
    public void aplicarEfectoAcido() {
        this.estadoSalud = this.estadoSalud.recibirAcido();
    }

    @Override
    public boolean puedeTransitar(Terreno terreno) {
        return terreno.esTransitablePorTerrestre();
    }

    @Override
    public int getVidaActual() {
        return this.vidaActual;
    }

    @Override
    public boolean estaContaminado() {
        return this.estadoSalud instanceof Contaminado;
    }

    public void atacar(Celda origen, Celda destino) {
        if (this.estado == EstadoAccion.PACIFICO) {
            throw new IllegalStateException("El Mech no puede atacar mientras esta en el agua");
        }

        this.arma.atacar(origen, destino);
        this.estado = EstadoAccion.ATACO;
    }

    public EstadoSalud obtenerEstadoSalud() {
        return this.estadoSalud;
    }

    public void restaurarEstadoSalud(EstadoSalud estado) {
        this.estadoSalud = estado;
    }

    @Override
    public void asignarCasilla(Casilla casilla) {
        this.casillaActual = casilla;
    }

    @Override
    public void abandonarCasilla() {
        this.casillaActual = null;
    }

}
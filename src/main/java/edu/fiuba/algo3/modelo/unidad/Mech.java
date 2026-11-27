package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.arma.Arma;
import edu.fiuba.algo3.modelo.terreno.Terreno;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;

public class Mech implements Unidad {
    private static final int MOVIMIENTO_POR_DEFECTO = 3;

    private final Arma arma;
    private final int movimientoMaximo;
    private int pasosRestantes;
    private int vidaActual;
    private EstadoAtaque estadoAtaque;
    private EstadoSalud estadoSalud;

    public Mech(Arma arma, int vidaInicial) {
        this(arma, vidaInicial, MOVIMIENTO_POR_DEFECTO);
    }

    public Mech(Arma arma, int vidaInicial, int movimientoMaximo) {
        this.arma = arma;
        this.vidaActual = vidaInicial;
        this.movimientoMaximo = movimientoMaximo;
        this.pasosRestantes = movimientoMaximo;
        this.estadoSalud = new Sano();
        this.estadoAtaque = new PuedeAtacar();
    }

    @Override
    public void darPaso() {
        if (this.vidaActual == 0) {
            throw new IllegalStateException("Un Mech destruido no puede moverse.");
        }
        if (this.pasosRestantes == 0) {
            throw new IllegalStateException("El Mech no tiene más pasos disponibles.");
        }
        this.pasosRestantes--;
    }

    @Override
    public int guardarPasos() {
        return this.pasosRestantes;
    }

    @Override
    public void restaurarPasos(int pasos) {
        this.pasosRestantes = pasos;
    }

    public void reiniciarPasos() {
        this.pasosRestantes = this.movimientoMaximo;
    }

    @Override
    public void recibirDanio(int danio) {
        int danioFinal = this.estadoSalud.calcularDanioFinal(danio);
        this.vidaActual = Math.max(0, this.vidaActual - danioFinal);
    }

    @Override
    public void aplicarEfectoAgua() {
        this.estadoAtaque = new InhabilitadoPorAgua();
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
        return this.estadoSalud.estaContaminado();
    }

    @Override
    public void atacar(Celda origen, Celda destino) {
        this.estadoAtaque.atacar(this.arma, origen, destino);
    }

    public EstadoSalud obtenerEstadoSalud() {
        return this.estadoSalud;
    }

    public void restaurarEstadoSalud(EstadoSalud estado) {
        this.estadoSalud = estado;
    }

    @Override
    public void asignarCasilla(Casilla casilla) {
        this.estadoAtaque = new PuedeAtacar();
    }

    @Override
    public void abandonarCasilla() {
        this.estadoAtaque = new PuedeAtacar();
    }
}
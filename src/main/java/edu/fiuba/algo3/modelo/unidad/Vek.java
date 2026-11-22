package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.terreno.Edificio;
import edu.fiuba.algo3.modelo.terreno.Terreno;
import edu.fiuba.algo3.modelo.turno.AtaqueAnunciado;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;

public abstract class Vek implements Unidad {
    private int vidaActual;
    private Casilla casillaActual;
    private EstadoSalud estadoSalud ;
    private EstadoAccion estado ;
    private int movimientoMaximo;


    protected Vek(int vidaInicial, int movimientoMaximo) {
        this.vidaActual = vidaInicial;
        this.estadoSalud = new Sano();
        this.estado = EstadoAccion.DISPONIBLE;
        this.movimientoMaximo = movimientoMaximo;
    }

    public void asignarCasilla(Casilla casilla) {
        this.casillaActual = casilla;
    }

    @Override
    public void abandonarCasilla() {
        this.casillaActual = null;
    }

    public Casilla getCasillaActual() {
        return this.casillaActual;
    }

    @Override
    public void recibirDanio(int danio) {
        int danioFinal = this.estadoSalud.calcularDanioFinal(danio);
        this.vidaActual = Math.max(0, this.vidaActual - danioFinal);
    }

    @Override
    public void aplicarEfectoAgua() {
    }

    @Override
    public void aplicarEfectoAcido() {
        this.estadoSalud = this.estadoSalud.recibirAcido();

    }

    @Override
    public boolean puedeTransitar(Terreno terreno) {
        return true;
    }

    @Override
    public void atacar(Celda origen, Celda destino) {
    }

    @Override
    public int getVidaActual() {
        return this.vidaActual;
    }

    @Override
    public boolean estaContaminado() {
        return false;
    }

    public AtaqueAnunciado anunciaAtaque(Casilla origen) {
        for (Direccion direccion : Direccion.values()) {
            Celda vecina = origen.vecinaAl(direccion);
            if (vecina instanceof Casilla && ((Casilla) vecina).getUnidad() instanceof Mech) {
                return new AtaqueAnunciado(this, direccion);
            }
        }
        throw new IllegalStateException("El Vek no tiene un objetivo adyacente");
    }

    public void moverse(Casilla origen, Casilla destino) {
        origen.moverUnidadHacia(destino);
    }

    public int getMovimientoMaximo(){ return movimientoMaximo; }


}
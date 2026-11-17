package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.terreno.Terreno;
import edu.fiuba.algo3.modelo.ubicacion.Celda;

public class Vek implements Unidad {
    private int vidaActual;
    private EstadoSalud estadoSalud ;
    private EstadoAccion estado ;


    protected Vek(int vidaInicial) {
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


}
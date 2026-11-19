package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.terreno.Terreno;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;

public class Luciernaga extends Vek {

    public Luciernaga() {
        super(3, 2);

    }

    @Override
    public void aplicarEfectoAgua() {
        this.recibirDanio(this.getVidaActual());
    }

    @Override
    public void atacar(Celda origen, Celda destino) {
        origen.direccionHacia(destino);
        if (destino instanceof Casilla) {
            Casilla casillaDestino = (Casilla) destino;
            if (casillaDestino.estaOcupada()) {
                Unidad unidadAfectada = casillaDestino.getUnidad();
                unidadAfectada.recibirDanio(1);
                unidadAfectada.aplicarEfectoAcido();
            }
        }
    }

    @Override
    public boolean puedeTransitar(Terreno terreno) {
        return terreno.esTransitablePorTerrestre();
    }




}

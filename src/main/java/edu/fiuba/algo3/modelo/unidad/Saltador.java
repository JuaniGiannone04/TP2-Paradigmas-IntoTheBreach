package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.terreno.Terreno;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;

public class Saltador extends Vek {

    public Saltador() {
        super(2, 3);
    }

    @Override
    public void aplicarEfectoAgua() {
        this.recibirDanio(this.getVidaActual());
    }

    @Override
    public void atacar(Celda origen, Celda destino) {
        if (!origen.esAdyacenteA(destino)) {
            throw new IllegalArgumentException("El Saltador solo ataca casillas adyacentes");
        }
        if (destino instanceof Casilla) {
            Casilla casillaDestino = (Casilla) destino;
            if (casillaDestino.estaOcupada()) {
                casillaDestino.getUnidad().recibirDanio(2);
            }
        }
    }

    @Override
    public boolean puedeTransitar(Terreno terreno) {
        return terreno.esTransitablePorTerrestre();
    }


}
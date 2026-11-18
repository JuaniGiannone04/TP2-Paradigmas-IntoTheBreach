package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;

public class Avispon extends Vek {

    public Avispon() {
        super(2);
    }



    @Override
    public void atacar(Celda origen, Celda destino) {
        if (destino instanceof Casilla) {
            Casilla casillaDestino = (Casilla) destino;
            if (casillaDestino.estaOcupada()) {
                casillaDestino.getUnidad().recibirDanio(1);
            }
        }
    }

    public void moverse(Casilla origen, Casilla destino) {
        origen.moverUnidadHacia(destino);
    }
}
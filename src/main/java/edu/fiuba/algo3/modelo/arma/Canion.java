package edu.fiuba.algo3.modelo.arma;

import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;

public class Canion implements Arma {
    private static final int DANIO = 1;
    private static final int DISTANCIA_MINIMA = 1;

    @Override
    public void atacar(Celda origen, Direccion direccion, int distancia) {
        if (distancia < DISTANCIA_MINIMA) {
            throw new IllegalArgumentException("La distancia de disparo debe ser al menos 1");
        }
        origen.vecinaAl(direccion).recibirProyectilRecto(direccion, DANIO);
    }
}
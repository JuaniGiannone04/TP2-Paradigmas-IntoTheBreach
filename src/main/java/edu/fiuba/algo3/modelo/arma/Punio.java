package edu.fiuba.algo3.modelo.arma;

import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;

public class Punio implements Arma {
    private static final int DANIO = 2;
    private static final int DISTANCIA_VALIDA = 1;

    @Override
    public void atacar(Celda origen, Direccion direccion, int distancia) {
        if (distancia != DISTANCIA_VALIDA) {
            throw new IllegalArgumentException("El Puño solo puede atacar casillas adyacentes");
        }
        origen.vecinaAl(direccion).recibirGolpeCuerpoACuerpo(direccion, DANIO);
    }
}
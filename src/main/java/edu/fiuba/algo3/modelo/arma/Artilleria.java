package edu.fiuba.algo3.modelo.arma;

import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;

public class Artilleria implements Arma {
    private static final int DANIO = 1;
    private static final int DISTANCIA_MINIMA = 2;

    @Override
    public void atacar(Celda origen, Direccion direccion, int distancia) {
        if (distancia < DISTANCIA_MINIMA) {
            throw new IllegalArgumentException("La artillería debe atacar a 2 o más casillas");
        }
        origen.vecinaAl(direccion).recibirProyectilEnArco(direccion, distancia - 1, DANIO);
    }
}
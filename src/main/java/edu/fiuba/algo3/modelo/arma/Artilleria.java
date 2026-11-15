package edu.fiuba.algo3.modelo.arma;

import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;

public class Artilleria implements Arma {
    private static final int DANIO = 1;

    @Override
    public void atacar(Celda origen, Celda destino) {
        if (origen.esAdyacenteA(destino)) {
            throw new IllegalArgumentException("La artillería debe atacar a 2 o más casillas");
        }
        Direccion direccion = origen.direccionHacia(destino);
        origen.vecinaAl(direccion).recibirProyectilEnArco(destino, direccion, DANIO);
    }
}
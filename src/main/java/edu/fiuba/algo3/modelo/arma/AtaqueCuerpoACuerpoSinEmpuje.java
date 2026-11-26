package edu.fiuba.algo3.modelo.arma;

import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;

public class AtaqueCuerpoACuerpoSinEmpuje implements Arma {
    private final int danio;

    public AtaqueCuerpoACuerpoSinEmpuje(int danio) {
        this.danio = danio;
    }

    @Override
    public void atacar(Celda origen, Celda destino) {
        if (!origen.esAdyacenteA(destino)) {
            throw new IllegalArgumentException("El ataque cuerpo a cuerpo solo puede apuntar a casillas adyacentes");
        }
        Direccion direccion = origen.direccionHacia(destino);
        origen.propagar(direccion, new GolpeSinEmpuje(this.danio));
    }
}
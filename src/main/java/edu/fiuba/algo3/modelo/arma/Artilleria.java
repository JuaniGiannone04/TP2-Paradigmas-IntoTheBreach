package edu.fiuba.algo3.modelo.arma;

import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;

public class Artilleria implements Arma {
    private static final int DANIO = 1;

    @Override
    public void atacar(Celda origen, Celda destino) {
        Direccion direccion = origen.direccionHacia(destino);

        if(origen.vecinaAl(direccion) == destino) {
            throw new IllegalArgumentException("La artilleria debe atacar a 2 o mas casillas de distancia");
        }

        destino.recibirDanio(DANIO);
        for(Direccion direccionEmpuje : Direccion.values()){
            destino.vecinaAl(direccionEmpuje).empujarUnidadInterna(direccionEmpuje);
        }

    }
}
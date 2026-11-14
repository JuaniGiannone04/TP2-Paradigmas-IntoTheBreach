package edu.fiuba.algo3.modelo.ubicacion;

import edu.fiuba.algo3.modelo.unidad.Unidad;

public class BordeDelMapa implements Celda {

    @Override
    public Celda vecinaAl(Direccion direccion) {
        return this;
    }

    @Override
    public void recibirGolpeCuerpoACuerpo(Direccion direccion, int danio) {
        throw new IllegalArgumentException("No se puede golpear fuera del tablero");
    }

    @Override
    public void recibirProyectilRecto(Direccion direccion, int danio) {
    }

    @Override
    public void recibirProyectilEnArco(Direccion direccion, int pasosRestantes, int danio) {
        throw new IllegalArgumentException("No se puede disparar fuera del tablero");
    }

    @Override
    public void empujarUnidadInterna(Direccion direccion) {
    }

    @Override
    public boolean recibirUnidadEmpujada(Unidad unidadEmpujada) {
        return false;
    }
}
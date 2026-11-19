package edu.fiuba.algo3.modelo.ubicacion;

import edu.fiuba.algo3.modelo.unidad.Unidad;

public class BordeDelMapa implements Celda {

    @Override
    public Celda vecinaAl(Direccion direccion) {
        return this;
    }

    @Override
    public boolean esAdyacenteA(Celda otraCelda) {
        return false;
    }

    @Override
    public Direccion direccionHacia(Celda destino) {
        throw new IllegalArgumentException("El borde no puede ser origen de un ataque");
    }

    @Override
    public boolean estaEnLineaRectaHacia(Direccion direccion, Celda destino) {
        return false;
    }

    @Override
    public void recibirGolpeCuerpoACuerpo(Direccion direccion, int danio) {
        throw new IllegalArgumentException("No se puede golpear fuera del tablero");
    }

    @Override
    public void recibirProyectilRecto(Direccion direccion, int danio) {
    }

    @Override
    public void recibirProyectilEnArco(Celda destino, Direccion direccion, int danio) {
        throw new IllegalArgumentException("No se puede disparar fuera del tablero");
    }

    @Override
    public void empujarUnidadInterna(Direccion direccion) {
    }

    @Override
    public boolean recibirUnidadEmpujada(Unidad unidadEmpujada) {
        return false;
    }
    @Override
    public void recibirProyectilContaminante(Direccion direccion, int danio) {

    }
}
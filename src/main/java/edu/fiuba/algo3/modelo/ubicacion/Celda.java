package edu.fiuba.algo3.modelo.ubicacion;

import edu.fiuba.algo3.modelo.unidad.Unidad;

public interface Celda {
    Celda vecinaAl(Direccion direccion);
    void recibirGolpeCuerpoACuerpo(Direccion direccion, int danio);
    void recibirProyectilRecto(Direccion direccion, int danio);
    void recibirProyectilEnArco(Direccion direccion, int pasosRestantes, int danio);
    void empujarUnidadInterna(Direccion direccion);
    boolean recibirUnidadEmpujada(Unidad unidadEmpujada);
}
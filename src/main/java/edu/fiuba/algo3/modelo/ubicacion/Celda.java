package edu.fiuba.algo3.modelo.ubicacion;

import edu.fiuba.algo3.modelo.unidad.Unidad;

public interface Celda {
    Celda vecinaAl(Direccion direccion);
    boolean esAdyacenteA(Celda otraCelda);
    Direccion direccionHacia(Celda destino);
    boolean estaEnLineaRectaHacia(Direccion direccion, Celda destino);
    void recibirDanio(int danio);
    void recibirProyectilRecto(Direccion direccion, int danio);
    void recibirProyectilEnArco(Celda destino, Direccion direccion, int danio);
    void empujarUnidadInterna(Direccion direccion);
    boolean recibirUnidadEmpujada(Unidad unidadEmpujada);
    void recibirProyectilContaminante(Direccion direccion, int danio);
}
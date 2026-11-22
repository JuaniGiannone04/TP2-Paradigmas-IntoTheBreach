package edu.fiuba.algo3.modelo.ubicacion;

import edu.fiuba.algo3.modelo.unidad.Unidad;

import java.util.Optional;

public interface Celda {
    Celda vecinaAl(Direccion direccion);
    boolean esAdyacenteA(Celda otraCelda);
    Direccion direccionHacia(Celda destino);
    boolean estaEnLineaRectaHacia(Direccion direccion, Celda destino);
    void recibirDanio(int danio);
    void empujarUnidadInterna(Direccion direccion);
    boolean recibirUnidadEmpujada(Unidad unidadEmpujada);
    Optional <Casilla> primerBloqueoEn(Direccion direccion);

}
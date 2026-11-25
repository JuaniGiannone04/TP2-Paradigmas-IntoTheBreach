package edu.fiuba.algo3.modelo.ubicacion;

import edu.fiuba.algo3.modelo.arma.Ataque;
import edu.fiuba.algo3.modelo.unidad.Unidad;

public interface Celda {
    Celda vecinaAl(Direccion direccion);
    boolean esAdyacenteA(Celda otraCelda);
    Direccion direccionHacia(Celda destino);
    boolean estaEnLineaRectaHacia(Direccion direccion, Celda destino);

    void recibir(Ataque ataque);
    void propagar(Direccion direccion, Ataque ataque);

    void empujarUnidadInterna(Direccion direccion);
    boolean recibirUnidadEmpujada(Unidad unidadEmpujada);
}
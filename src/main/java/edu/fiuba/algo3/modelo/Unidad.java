package edu.fiuba.algo3.modelo;

public interface Unidad {
    void recibirDanio(int danio);
    void aplicarEfectoAgua();
    void aplicarEfectoAcido();
    boolean puedeTransitar(Terreno terreno);
}
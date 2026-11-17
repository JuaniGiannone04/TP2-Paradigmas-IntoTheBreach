package edu.fiuba.algo3.modelo.unidad.estadosalud;

public class Sano implements EstadoSalud {
    @Override
    public int calcularDanio(int cantidad) {
        return cantidad;
    }
}
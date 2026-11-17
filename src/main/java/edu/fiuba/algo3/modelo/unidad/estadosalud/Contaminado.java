package edu.fiuba.algo3.modelo.unidad.estadosalud;

public class Contaminado implements EstadoSalud {
    @Override
    public int calcularDanio(int cantidad) {
        return cantidad * 2;
    }
}
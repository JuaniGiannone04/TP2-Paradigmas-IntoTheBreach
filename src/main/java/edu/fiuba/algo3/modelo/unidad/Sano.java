package edu.fiuba.algo3.modelo.unidad;

public class Sano implements EstadoSalud {
    @Override
    public int calcularDanioFinal(int danioBase) {
        return danioBase;
    }

    @Override
    public EstadoSalud recibirAcido() {
        return new Contaminado();
    }

    @Override
    public EstadoSalud sanar() {
        return this;
    }
}
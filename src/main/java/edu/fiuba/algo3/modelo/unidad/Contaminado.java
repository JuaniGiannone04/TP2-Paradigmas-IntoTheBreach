package edu.fiuba.algo3.modelo.unidad;

public class Contaminado implements EstadoSalud {
    @Override
    public int calcularDanioFinal(int danioBase) {
        return danioBase * 2;
    }

    @Override
    public EstadoSalud recibirAcido() {
        return this;
    }

    @Override
    public EstadoSalud sanar() {
        return new Sano();
    }
}
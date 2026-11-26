package edu.fiuba.algo3.modelo.unidad;

public interface EstadoSalud {
    int calcularDanioFinal(int danioBase);
    EstadoSalud recibirAcido();
    EstadoSalud sanar();
    boolean estaContaminado();
}
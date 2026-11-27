package edu.fiuba.algo3.modelo.turno;


import java.util.List;

public class FaseResolucion implements Fase {

    private final List<AtaqueAnunciado> ataquesAnunciados;
    private final FaseEmergencia emergencia;

    public FaseResolucion(List<AtaqueAnunciado> ataquesAnunciados) {
        this.ataquesAnunciados = ataquesAnunciados;
        this.emergencia = new FaseEmergencia();
    }

    public FaseResolucion(List<AtaqueAnunciado> ataquesAnunciados, FaseEmergencia emergencia) {
        this.ataquesAnunciados = ataquesAnunciados;
        this.emergencia = emergencia;
    }

    @Override
    public FaseEmergencia  ejecutar(Turno turno) {
        for (AtaqueAnunciado ataque : ataquesAnunciados) {
            ataque.ejecutar();
        }
        return emergencia;
    }


}
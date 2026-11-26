package edu.fiuba.algo3.modelo.turno;


import java.util.List;

public class FaseResolucion implements Fase {

    private final List<AtaqueAnunciado> ataquesAnunciados;

    public FaseResolucion(List<AtaqueAnunciado> ataquesAnunciados) {
        this.ataquesAnunciados = ataquesAnunciados;
    }

    @Override
    public FaseEmergencia  ejecutar(Turno turno) {
        for (AtaqueAnunciado ataque : ataquesAnunciados) {
            ataque.ejecutar();
        }
        return new FaseEmergencia();
    }


}
package edu.fiuba.algo3.modelo.turno;



public class Turno {

    private int numero = 1;
    private Fase faseActual;


    public Turno() {
        this.faseActual = new FaseVek();
    }

    public Fase avanzarFase() {
        faseActual = faseActual.ejecutar(this);
        return faseActual;
    }

    public int obtenerNumero() {
        return numero;
    }

    void terminarTurno() {
        numero++;
    }

    public Fase faseActual() {
        return faseActual;
    }





}
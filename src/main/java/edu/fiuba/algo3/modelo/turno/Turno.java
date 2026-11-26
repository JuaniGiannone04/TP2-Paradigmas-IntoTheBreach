package edu.fiuba.algo3.modelo.turno;


import edu.fiuba.algo3.modelo.ubicacion.Tablero;

import java.util.Optional;

public class Turno {

    private int numero = 1;
    private Fase faseActual;
    private int ultimoTurnoCompletado = 0;
    private Optional<Tablero> tablero;


    public Turno() {
        this.faseActual = new FaseVek();
        this.tablero = Optional.empty();
    }

    public Turno(Tablero tablero) {
       this.tablero = Optional.of(tablero);
       this.faseActual = new FaseVek();
    }

    public Fase avanzarFase() {
        faseActual = faseActual.ejecutar(this);
        return faseActual;
    }

    public int ultimoTurnoCompletado() {
        return ultimoTurnoCompletado;
    }

    public void avanzarTurno() {
        int turnoInicial = numero;
        while (numero == turnoInicial) {
            avanzarFase();
        }
    }

    public int obtenerNumero() {
        return numero;
    }

    void terminarTurno() {
        ultimoTurnoCompletado = numero;
        numero++;
    }


    public Fase faseActual() {
        return faseActual;
    }

    public Optional<Tablero> tablero() {
        return tablero;
    }




}
package edu.fiuba.algo3.modelo.turno;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.unidad.Mech;
import edu.fiuba.algo3.modelo.unidad.Vek;

import java.util.List;
import java.util.ArrayList;


public class Turno {

    private int numero = 1;
    private Fase faseActual;
    private final List<AtaqueAnunciado> ataquesAnunciados = new ArrayList<>();

    public Turno() {
        this.faseActual = new FaseVek();
    }

    public void avanzarFase() {
        faseActual.ejecutar(this);
    }

    public Fase obtenerFaseActual() {
        return faseActual;
    }

    public int obtenerNumero() {
        return numero;
    }

    void cambiarFase(Fase nuevaFase) {
        this.faseActual = nuevaFase;
    }

    public void terminarTurno() {
        numero++;
        cambiarFase(new FaseVek());
    }

    public void moverMech(Mech mech, Casilla origen, Casilla destino) {
        faseJugador().moverMech(mech, origen, destino);
    }

    public void deshacerMovimiento(Mech mech) {
        faseJugador().deshacerMovimiento(mech);
    }

    public void atacarConMech(Mech mech, Celda origen, Celda destino) {
        faseJugador().atacarConMech(mech, origen, destino);
    }

    private FaseJugador faseJugador() {
        if (!(faseActual instanceof FaseJugador)) {
            throw new IllegalStateException("Solo se puede actuar en la Fase del Jugador");
        }
        return (FaseJugador) faseActual;
    }


    public int cantidadDeAtaquesAnunciados() {
        return ataquesAnunciados.size();
    }

    public void registrarAtaque(AtaqueAnunciado ataque) {
        if (!(faseActual instanceof FaseVek)) {
            throw new IllegalStateException("No se puede anunciar ataques fuera de la Fase Vek");
        }
        ataquesAnunciados.add(ataque);
    }

    public List<Vek> ejecutarResolucion() {
        if (!(faseActual instanceof FaseResolucion)) {
            throw new IllegalStateException("Solo se ejecutan ataques en la Fase Resolución");
        }
        List<Vek> ejecutados = new ArrayList<>();
        for (AtaqueAnunciado ataque : ataquesAnunciados) {
            Vek vek = ataque.getAtacante();
            if (vek.getVidaActual() > 0) {
                ataque.ejecutar();
                ejecutados.add(vek);
            }
        }
        return ejecutados;
    }




}
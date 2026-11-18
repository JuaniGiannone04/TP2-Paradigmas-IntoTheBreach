package edu.fiuba.algo3.modelo.turno;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.unidad.Vek;

public class AtaqueAnunciado {

    private final Vek atacante;
    private final Direccion direccion;
    private final Casilla origen;

    public AtaqueAnunciado(Vek atacante, Direccion direccion, Casilla origen) {
        this.atacante = atacante;
        this.direccion = direccion;
        this.origen = origen;
    }

    public Direccion getDireccion() {
        return this.direccion;
    }

    public Vek getAtacante() {
        return this.atacante;
    }

    public void ejecutar() {
        Celda destino = origen.vecinaAl(direccion);
        if (destino != null) {
            atacante.atacar(origen, destino);
        }
    }
}
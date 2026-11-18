package edu.fiuba.algo3.modelo.turno;

import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.unidad.Vek;

public class AtaqueAnunciado {

    private final Vek atacante;
    private final Direccion direccion;

    public AtaqueAnunciado(Vek atacante, Direccion direccion) {
        this.atacante = atacante;
        this.direccion = direccion;
    }

    public Direccion getDireccion() {
        return this.direccion;
    }

    public Vek getAtacante() {
        return this.atacante;
    }

}
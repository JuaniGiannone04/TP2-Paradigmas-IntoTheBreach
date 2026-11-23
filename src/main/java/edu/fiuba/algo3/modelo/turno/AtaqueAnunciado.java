package edu.fiuba.algo3.modelo.turno;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
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

    public void ejecutar() {
        Casilla origenActual = atacante.getCasillaActual();

        if (origenActual == null || atacante.getVidaActual() == 0) {
            return;
        }

        Celda destino = origenActual.vecinaAl(direccion);

        atacante.atacar(origenActual, destino);
    }
}

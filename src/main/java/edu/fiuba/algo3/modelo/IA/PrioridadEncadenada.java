package edu.fiuba.algo3.modelo.IA;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;
import edu.fiuba.algo3.modelo.unidad.Vek;

import java.util.List;

public abstract class PrioridadEncadenada implements PrioridadVek {

    private final PrioridadVek siguiente;

    protected PrioridadEncadenada(PrioridadVek siguiente) {
        this.siguiente = siguiente;
    }

    protected DecisionVek evaluarSiguiente(Vek vek, Tablero tablero, List<Casilla> alcanzables) {
        if (siguiente == null) {
            return null;
        }

        return siguiente.evaluar(vek, tablero, alcanzables);
    }
}
package edu.fiuba.algo3.modelo.IA;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;
import edu.fiuba.algo3.modelo.unidad.Vek;

import java.util.List;

public class PrioridadAcercamiento extends PrioridadEncadenada {

    public PrioridadAcercamiento(PrioridadVek siguiente) {
        super(siguiente);
    }

    @Override
    public DecisionVek evaluar(Vek vek, Tablero tablero, List<Casilla> alcanzables) {
        DecisionVek decision = buscarAcercamiento(vek, tablero, alcanzables);

        if (decision != null) {
            return decision;
        }

        return evaluarSiguiente(vek, tablero, alcanzables);
    }

    private DecisionVek buscarAcercamiento(Vek vek, Tablero tablero, List<Casilla> alcanzables) {
        // PENDIENTE LA FORMA PARA QUE, ANTE LA AUSENCIA DE EDIFICIO O MECH PARA ATACAR, PUEDA ACERCARSE
        return null;
    }
}

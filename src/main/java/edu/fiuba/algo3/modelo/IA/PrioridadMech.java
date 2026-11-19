package edu.fiuba.algo3.modelo.IA;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;
import edu.fiuba.algo3.modelo.unidad.Vek;

import java.util.List;

public class PrioridadMech extends PrioridadEncadenada {

    public PrioridadMech(PrioridadVek siguiente) {
        super(siguiente);
    }

    @Override
    public DecisionVek evaluar(Vek vek, Tablero tablero, List<Casilla> alcanzables) {

        DecisionVek decision = buscarAtaqueMech(vek, tablero, alcanzables);

        if (decision != null) {
            return decision;
        }

        return evaluarSiguiente(vek, tablero, alcanzables);
        }

        private DecisionVek buscarAtaqueMech(Vek vek, Tablero tablero, List<Casilla> alcanzables) {
            // PENDIENTE LA FORM PARA QUE PUEDA BUSCARSE UN MECH PARA ATACAR
            return null;
        }
    }


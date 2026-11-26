package edu.fiuba.algo3.modelo.IA;

import edu.fiuba.algo3.modelo.terreno.Edificio;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;
import edu.fiuba.algo3.modelo.unidad.Mech;
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
        List<Casilla> mechs = tablero.obtenerCasillasConMechs();
        for (Casilla origen : alcanzables) {
            for (Casilla mech : mechs) {
                if (vek.puedeAtacarDesde(origen, mech)) {
                    Direccion direccion = origen.direccionHacia(mech);
                    return new DecisionVek(origen, direccion);
                }
            }
        }
        return null;
    }
}

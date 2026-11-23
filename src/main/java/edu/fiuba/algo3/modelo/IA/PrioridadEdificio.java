package edu.fiuba.algo3.modelo.IA;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;
import edu.fiuba.algo3.modelo.unidad.Vek;

import java.util.List;

public class PrioridadEdificio extends PrioridadEncadenada {

    public PrioridadEdificio(PrioridadVek siguiente) {
        super(siguiente);
    }

    @Override
    public DecisionVek evaluar(Vek vek, Tablero tablero, List<Casilla> alcanzables) {
        DecisionVek decision = buscarAtaqueEdificio(vek, tablero, alcanzables);
        if (decision != null) {
            return decision;
        }
        return evaluarSiguiente(vek, tablero, alcanzables);
    }

    private DecisionVek buscarAtaqueEdificio(
            Vek vek,
            Tablero tablero,
            List<Casilla> alcanzables) {

        List<Casilla> edificios = tablero.obtenerCasillasConEdificios();

        for (Casilla origen : alcanzables) {
            for (Casilla edificio : edificios) {

                if (vek.puedeAtacarDesde(origen, edificio)) {
                    Direccion direccion = origen.direccionHacia(edificio);

                    return new DecisionVek(origen, direccion);
                }
            }
        }

        return null;
    }
}
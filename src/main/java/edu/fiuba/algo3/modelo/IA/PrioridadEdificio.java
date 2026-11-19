package edu.fiuba.algo3.modelo.IA;

import edu.fiuba.algo3.modelo.terreno.Edificio;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
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

    private DecisionVek buscarAtaqueEdificio(Vek vek, Tablero tablero, List<Casilla> alcanzables) {
        // PENDIENTE LA FORMA PARA QUE PUEDA BUSCARSE UN EDIFICIO PARA ATACAR

        return null;
    }

    private boolean esEdificio(Casilla casilla) {
        return casilla.getTerreno() instanceof Edificio;
    }
}
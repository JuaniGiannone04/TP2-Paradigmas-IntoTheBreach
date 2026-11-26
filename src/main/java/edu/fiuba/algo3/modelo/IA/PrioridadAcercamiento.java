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
        List<Casilla> edificios = tablero.obtenerCasillasConEdificios();
        Casilla mejorCasilla = null;
        int menorDistancia = Integer.MAX_VALUE;
        for (Casilla edificio : edificios) {
            int distancia = tablero.distanciaEntre(vek.getCasillaActual(), edificio);

            if (distancia < menorDistancia) {
                menorDistancia = distancia;
            }
        }

        for (Casilla origen : alcanzables) {
            for (Casilla edificio : edificios) {
                int distancia = tablero.distanciaEntre(origen, edificio);
                if (distancia < menorDistancia) {
                    menorDistancia = distancia;
                    mejorCasilla = origen;
                }
            }
        }
        if (mejorCasilla == null) {
            return null;
        }
        return new DecisionVek(mejorCasilla, null);
    }


}
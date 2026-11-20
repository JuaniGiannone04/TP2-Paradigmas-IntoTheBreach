package edu.fiuba.algo3.modelo.IA;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;
import edu.fiuba.algo3.modelo.unidad.Vek;

import java.util.List;

public interface PrioridadVek {

    DecisionVek evaluar(Vek vek, Tablero tablero, List<Casilla> alcanzables
    );
}

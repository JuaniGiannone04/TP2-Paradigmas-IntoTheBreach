package edu.fiuba.algo3.modelo.IA;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;
import edu.fiuba.algo3.modelo.unidad.Vek;

import java.util.ArrayList;
import java.util.List;

public class IAVek {

    private final Tablero tablero;
    private final Vek vek;
    private final PrioridadVek prioridades;

    public IAVek(Tablero tablero, Vek vek) {
        this.tablero = tablero;
        this.vek = vek;
        this.prioridades = new PrioridadEdificio(new PrioridadMech(new PrioridadAcercamiento(null)));}

    public DecisionVek decidir() {

        List<Casilla> alcanzables = new ArrayList<>();

        alcanzables.add(vek.getCasillaActual());

        alcanzables.addAll(tablero.casillasAlcanzablesDesde(vek.getCasillaActual(), vek, vek.getMovimientoMaximo()));

        return prioridades.evaluar(vek, tablero, alcanzables);
    }
}

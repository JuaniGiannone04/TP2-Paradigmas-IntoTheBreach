package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

import java.util.Map;

public class VistaTablero extends GridPane {
    private final Map<Casilla, VistaCasilla> vistasPorCasilla;

    public VistaTablero(Map<Casilla, VistaCasilla> vistasPorCasilla) {
        this.vistasPorCasilla = vistasPorCasilla;
        this.setAlignment(Pos.CENTER);
        this.setHgap(2);
        this.setVgap(2);

        for (VistaCasilla vistaCasilla : vistasPorCasilla.values()) {
            this.add(vistaCasilla, vistaCasilla.getColumna(), vistaCasilla.getFila());
        }
    }

    public void actualizar() {
        this.vistasPorCasilla.values().forEach(VistaCasilla::actualizar);
    }

    public VistaCasilla obtenerVistaDe(Casilla casilla) {
        return this.vistasPorCasilla.get(casilla);
    }
}
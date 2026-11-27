package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.terreno.*;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Map;

public class VistaCasilla extends StackPane {
    private static  int TAMANIO = 70;
    private static final Map<Class<? extends Terreno>, Color> COLORES_TERRENO = Map.of(
            Suelo.class, Color.BEIGE,
            Montana.class, Color.DIMGRAY,
            Edificio.class, Color.STEELBLUE,
            Agua.class, Color.DEEPSKYBLUE,
            Acido.class, Color.YELLOWGREEN
    );

    private final Casilla casilla;
    private final int fila;
    private final int columna;
    private final Rectangle fondo;
    private final Label etiquetaTerreno;
    private final Label etiquetaUnidad;

    public VistaCasilla(Casilla casilla, int fila, int columna) {
        this.casilla = casilla;
        this.fila = fila;
        this.columna = columna;

        this.fondo = new Rectangle(TAMANIO, TAMANIO);
        this.fondo.setStroke(Color.DARKSLATEGRAY);

        this.etiquetaTerreno = new Label();
        this.etiquetaTerreno.setStyle("-fx-font-size: 10px; -fx-text-fill: #333333;");
        StackPane.setAlignment(this.etiquetaTerreno, Pos.TOP_LEFT);

        this.etiquetaUnidad = new Label();
        this.etiquetaUnidad.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");

        this.getChildren().addAll(this.fondo, this.etiquetaTerreno, this.etiquetaUnidad);
        this.actualizar();
    }

    public void actualizar() {
        Terreno terreno = this.casilla.getTerreno();
        this.fondo.setFill(COLORES_TERRENO.getOrDefault(terreno.getClass(), Color.BEIGE));
        this.etiquetaTerreno.setText(terreno.getClass().getSimpleName());

        if (this.casilla.estaOcupada()) {
            String nombre = this.casilla.getUnidad().getClass().getSimpleName();
            int vida = this.casilla.getUnidad().getVidaActual();
            this.etiquetaUnidad.setText(nombre + " (" + vida + ")");
        } else {
            this.etiquetaUnidad.setText("");
        }
    }

    public int getFila() {
        return this.fila;
    }

    public int getColumna() {
        return this.columna;
    }

    public Casilla getCasilla() {
        return this.casilla;
    }
}
package edu.fiuba.algo3.modelo.IA;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;
import edu.fiuba.algo3.modelo.unidad.Vek;

import java.util.List;

public class IAVek {
    private Tablero tablero;
    private Vek vek;
    private final PrioridadVek prioridades;

    public IAVek(Tablero tablero, Vek vek) {
        this.tablero = tablero;
        this.vek = vek;
        this.prioridades = new PrioridadEdificio(
                new PrioridadMech(
                        new PrioridadAcercamiento(null) //EN ESTE CASO LA SIGUIENTE PRIORIDAD ES NULL PERO
                        // DEBERIA DEFINIRSE UN CRITERIO A DETALLAR EN LOS SUPUESTOS
                )
        );
    }

    public List<Casilla> calcularCasillasAlcanzables() {
        return tablero.buscarCasillaDe(vek).map(origen ->
                tablero.casillasAlcanzablesDesde(origen, vek, vek.getMovimientoMaximo()
                )).orElse(List.of());
    }



}

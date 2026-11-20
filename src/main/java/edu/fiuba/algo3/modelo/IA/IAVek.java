package edu.fiuba.algo3.modelo.IA;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;
import edu.fiuba.algo3.modelo.unidad.Vek;

import java.util.ArrayList;
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
        List<Casilla> alcanzables = new ArrayList<>();

        Casilla casillaActual = vek.getCasillaActual();
        int movimientoMaximo = vek.getMovimientoMaximo();

        explorarCasillas(casillaActual, movimientoMaximo, alcanzables);

        return alcanzables;
    }

    private void explorarCasillas(Casilla actual, int movimientosRestantes, List<Casilla> alcanzables) {

        if (movimientosRestantes == 0) {
            return;
        }

        for (Direccion direccion : Direccion.values()) {
            Celda vecina = actual.vecinaAl(direccion);

            if (!(vecina instanceof Casilla)) {
                continue;
            }

            Casilla casillaVecina = (Casilla) vecina;

            if (!casillaVecina.puedeSerOcupadaPor(vek)) {
                continue;
            }

            if (!alcanzables.contains(casillaVecina)) {
                alcanzables.add(casillaVecina);
            }

            explorarCasillas(casillaVecina, movimientosRestantes - 1, alcanzables);
        }
    }

}

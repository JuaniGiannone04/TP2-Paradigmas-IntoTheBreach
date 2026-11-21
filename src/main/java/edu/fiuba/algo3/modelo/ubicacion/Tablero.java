package edu.fiuba.algo3.modelo.ubicacion;

import edu.fiuba.algo3.modelo.unidad.Unidad;

import java.util.ArrayList;
import java.util.List;

public class Tablero {
    private final List<Casilla> casillas;

    public Tablero(List<Casilla> casillas) {
        this.casillas = casillas;
    }

    public List<Casilla> casillasAlcanzablesDesde(Casilla origen, Unidad unidad, int alcance){
        List<Casilla> alcanzables = new ArrayList<>();

        explorarCasillas(origen, unidad, alcance, alcanzables);

        return alcanzables;
    }

    public void explorarCasillas(Casilla actual, Unidad unidad, int movimientosRestantes, List<Casilla> alcanzables){
        if(movimientosRestantes == 0){
            return;
        }

        for(Direccion direccion : Direccion.values()){
            Celda vecina = actual.vecinaAl(direccion);

            if(!(vecina instanceof Casilla) ){
                continue;
            }

            Casilla siguiente = (Casilla) vecina;

            if(!siguiente.puedeSerOcupadaPor(unidad)){
                continue;
            }

            if(!alcanzables.contains(siguiente)){
                alcanzables.add(siguiente);
            }

            explorarCasillas(siguiente, unidad, movimientosRestantes - 1, alcanzables);
        }
    }


    public Casilla getCasillaInicial() {
        return this.casillas.get(0);
    }
}
package edu.fiuba.algo3.modelo.ubicacion;

import edu.fiuba.algo3.modelo.unidad.Unidad;

import java.util.*;

public class Tablero {
    private final List<Casilla> casillas;

    public Tablero(List<Casilla> casillas) {
        this.casillas = casillas;
    }

    public List<Casilla> casillasAlcanzablesDesde(Casilla origen, Unidad unidad, int alcance){

        List<Casilla> alcanzables = new ArrayList<>();
        Queue<Casilla> pendientes = new ArrayDeque<>();
        Map<Casilla, Integer> distancias = new HashMap<>();

        pendientes.add(origen);
        distancias.put(origen, 0);

        while(!pendientes.isEmpty()){
            Casilla actual =  pendientes.remove();
            int pasos =  distancias.get(actual);

            if(pasos >= alcance){
                continue;
            }

            for(Direccion direccion : Direccion.values()) {
                Celda vecina = actual.vecinaAl(direccion);
                if(!(vecina instanceof Casilla)){
                    continue;
                }
                Casilla siguiente = (Casilla) vecina;

                if(!(siguiente.puedeSerOcupadaPor(unidad)) || distancias.containsKey(siguiente)){
                    continue;
                }
                distancias.put(siguiente, pasos + 1);
                pendientes.add(siguiente);
                alcanzables.add(siguiente);
            }
        }

        return alcanzables;
    }

    public Casilla getCasillaInicial() {
        return this.casillas.get(0);
    }

    public Optional<Casilla> buscarCasillaDe(Unidad unidad){
        for(Casilla casilla : this.casillas){
            if(casilla.contieneUnidad(unidad)){
                return Optional.of(casilla);
            }
        }

        return Optional.empty();
    }
}
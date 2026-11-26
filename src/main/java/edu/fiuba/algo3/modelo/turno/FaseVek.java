
package edu.fiuba.algo3.modelo.turno;

import edu.fiuba.algo3.modelo.terreno.Edificio;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.unidad.Unidad;
import edu.fiuba.algo3.modelo.unidad.Vek;

import java.util.ArrayList;
import java.util.List;

public class FaseVek implements Fase {

    private final List<AtaqueAnunciado> ataquesAnunciados = new ArrayList<>();

    @Override
    public FaseJugador ejecutar(Turno turno) {
        return new FaseJugador(List.copyOf(ataquesAnunciados));
    }


    public void moverVek(Casilla origen) {
        Casilla destino = destinoHaciaEdificio(origen);

        if (destino != null) {
            if (destino.estaOcupada()) {
                throw new IllegalStateException(
                        "La casilla destino está ocupada"
                );
            }
            origen.moverUnidadHacia(destino);
        }
    }


    public void registrarAtaque(AtaqueAnunciado ataque) {
        ataquesAnunciados.add(ataque);
    }



    public Casilla destinoHaciaEdificio(Casilla origen) {
        for (Direccion direccion : Direccion.values()) {
            Celda actual = origen.vecinaAl(direccion);

            while (actual instanceof Casilla) {
                Casilla casilla = (Casilla) actual;

                if (casilla.getTerreno() instanceof Edificio) {
                    return (Casilla) origen.vecinaAl(direccion);
                }

                actual = casilla.vecinaAl(direccion);
            }
        }
        return null;
    }

    public void moverHaciaEdificio(Casilla origen) {
        Unidad unidad = origen.getUnidad();

        if (!(unidad instanceof Vek)) {
            throw new IllegalArgumentException("No hay un Vek en la casilla de origen");
        }
        for (Direccion direccion : Direccion.values()) {
            Celda actual = origen.vecinaAl(direccion);
            while (actual instanceof Casilla) {
                Casilla casilla = (Casilla) actual;
                if (casilla.getTerreno() instanceof Edificio) {
                    Casilla siguiente = (Casilla) origen.vecinaAl(direccion);
                    if (siguiente.estaOcupada()) {
                        throw new IllegalStateException("La casilla destino está ocupada");
                    }
                    origen.moverUnidadHacia(siguiente);
                    return;
                }
                actual = casilla.vecinaAl(direccion);
            }
        }
    }


}

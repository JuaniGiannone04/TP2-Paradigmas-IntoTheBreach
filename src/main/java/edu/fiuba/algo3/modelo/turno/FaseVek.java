package edu.fiuba.algo3.modelo.turno;


import edu.fiuba.algo3.modelo.terreno.Edificio;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.unidad.Unidad;
import edu.fiuba.algo3.modelo.unidad.Vek;

public class FaseVek implements Fase {

    @Override
    public void ejecutar(Turno turno) {
        turno.cambiarFase(new FaseJugador());

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
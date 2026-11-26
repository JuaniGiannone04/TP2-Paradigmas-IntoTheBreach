package edu.fiuba.algo3.modelo.turno;


import edu.fiuba.algo3.modelo.terreno.Edificio;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;
import edu.fiuba.algo3.modelo.unidad.Unidad;
import edu.fiuba.algo3.modelo.unidad.Vek;
import edu.fiuba.algo3.modelo.IA.IAVek;
import edu.fiuba.algo3.modelo.IA.DecisionVek;

import java.util.ArrayList;
import java.util.List;

public class FaseVek implements Fase {

    private final List<AtaqueAnunciado> ataquesAnunciados = new ArrayList<>();
    private final FaseEmergencia emergencia = new FaseEmergencia();

    @Override
    public FaseJugador ejecutar(Turno turno) {
        turno.tablero().ifPresent(this :: ejecutarIA);
        return new FaseJugador(List.copyOf(ataquesAnunciados), emergencia);
    }

    private void ejecutarIA(Tablero tablero) {
        for (Vek vek : tablero.obtenerVeks()) {
            DecisionVek decision = new IAVek(tablero, vek).decidir();
            ejecutarDecision(vek, decision);
        }

    }

    private void ejecutarDecision(Vek vek, DecisionVek decision) {
        Casilla origen = vek.getCasillaActual();
        Casilla destino = decision.getDestino();

        if(origen != destino){
            origen.moverUnidadHacia(destino);
        }

        if(vek.getVidaActual() > 0){
            Direccion direccionAtaque = decision.getDireccionAtaque();
            AtaqueAnunciado ataqueAnunciado = new AtaqueAnunciado(vek, direccionAtaque);
            registrarAtaque(ataqueAnunciado);
        }
    }

    public void programarEmergencia(Casilla casilla, Vek vek){
        emergencia.programar(casilla, vek);
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



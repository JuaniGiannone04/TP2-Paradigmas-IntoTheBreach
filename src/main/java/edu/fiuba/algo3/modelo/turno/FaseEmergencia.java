package edu.fiuba.algo3.modelo.turno;

import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.unidad.Vek;

public class FaseEmergencia implements Fase {

    private Vek vekEmergente;
    private Casilla casillaMarcada;

    public void programar(Casilla casilla, Vek vek){
        if(this.casillaMarcada != null){
            throw new IllegalStateException("Ya se programo una emergencia");
        }
        if((casilla == null) || (vek == null)){
            throw new IllegalArgumentException("Valores de entrada invalidos");
        }

        Casilla destino = casilla;
        Vek nuevoVek = vek;

        casillaMarcada = destino;
        vekEmergente = nuevoVek;
    }

    @Override
    public Fase ejecutar(Turno turno) {
        if(casillaMarcada != null){
            if(casillaMarcada.estaOcupada()){
                casillaMarcada.recibirDanio(1);
            } else {
                casillaMarcada.colocarUnidad(vekEmergente);
            }
        }
        turno.terminarTurno();
        return new FaseVek();
    }
}
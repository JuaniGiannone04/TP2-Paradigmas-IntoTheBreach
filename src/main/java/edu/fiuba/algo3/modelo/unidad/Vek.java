package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.terreno.Edificio;
import edu.fiuba.algo3.modelo.terreno.Terreno;
import edu.fiuba.algo3.modelo.turno.AtaqueAnunciado;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;

public class Vek implements Unidad {
    private int vidaActual;
    private Casilla casillaActual;

    protected Vek(int vidaInicial) {
        this.vidaActual = vidaInicial;
    }

    public void asignarCasilla(Casilla casilla) {
        this.casillaActual = casilla;
    }

    public Casilla getCasillaActual() {
        return this.casillaActual;
    }

    @Override
    public void recibirDanio(int danio) {
        this.vidaActual = Math.max(0, vidaActual - danio);
    }

    @Override
    public void aplicarEfectoAgua() {
    }

    @Override
    public void aplicarEfectoAcido() {
    }

    @Override
    public boolean puedeTransitar(Terreno terreno) {
        return true;
    }

    @Override
    public void atacar(Celda origen, Celda destino) {
    }

    @Override
    public int getVidaActual() {
        return this.vidaActual;
    }

    @Override
    public boolean estaContaminado() {
        return false;
    }

    public AtaqueAnunciado anunciaAtaque(Casilla origen) {
        for (Direccion direccion : Direccion.values()) {
            Celda vecina = origen.vecinaAl(direccion);
            if (vecina instanceof Casilla && ((Casilla) vecina).getUnidad() instanceof Mech) {
                return new AtaqueAnunciado(this, direccion, origen);
            }
        }
        throw new IllegalStateException("El Vek no tiene un objetivo adyacente");
    }

    public void moverse(Casilla origen, Casilla destino) {
        origen.moverUnidadHacia(destino);
    }

    public void moverHaciaEdificio(Casilla origen) {
        for (Direccion direccion : Direccion.values()) {
            Celda actual = origen.vecinaAl(direccion);
            while (actual instanceof Casilla) {
                Casilla casilla = (Casilla) actual;
                if (casilla.getTerreno() instanceof Edificio) {
                    origen.moverUnidadHacia((Casilla) origen.vecinaAl(direccion));
                    return;
                }
                actual = casilla.vecinaAl(direccion);
            }
        }
    }
}
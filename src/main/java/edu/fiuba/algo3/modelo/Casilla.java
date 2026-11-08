package edu.fiuba.algo3.modelo;

public class Casilla {
    private Terreno terreno;
    private Unidad unidad;

    public Casilla(Terreno terrenoInicial) {
        this.terreno = terrenoInicial;
        this.unidad = null;
    }

    public boolean estaOcupada() {
        return this.unidad != null;
    }

    public boolean puedeSerOcupadaPor(Unidad nuevaUnidad) {
        return !this.estaOcupada() && nuevaUnidad.puedeTransitar(this.terreno);
    }

    public void colocarUnidad(Unidad nuevaUnidad) {
        if (this.estaOcupada()) {
            throw new IllegalStateException("La casilla ya está ocupada por otra unidad.");
        }
        this.unidad = nuevaUnidad;
        this.terreno.aplicarEfecto(nuevaUnidad);
    }

    public Unidad quitarUnidad() {
        Unidad unidadRetirada = this.unidad;
        this.unidad = null;
        return unidadRetirada;
    }

    public void recibirDanio(int danio) {
        if (this.estaOcupada()) {
            this.unidad.recibirDanio(danio);
        }
        this.terreno = this.terreno.recibirDanio(danio);
    }

    public void cambiarTerreno(Terreno nuevoTerreno) {
        this.terreno = nuevoTerreno;
    }

    public Terreno getTerreno() {
        return this.terreno;
    }

    public Unidad getUnidad() {
        return this.unidad;
    }
}
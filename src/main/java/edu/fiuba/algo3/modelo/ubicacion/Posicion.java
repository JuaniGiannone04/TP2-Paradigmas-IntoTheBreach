package edu.fiuba.algo3.modelo.ubicacion;

import java.util.Objects;

public class Posicion {
    private final int fila;
    private final int columna;

    public Posicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public Direccion direccionHacia(Posicion destino) {
        int difFila = Integer.signum(destino.getFila() - this.fila);
        int difCol = Integer.signum(destino.getColumna() - this.columna);

        for (Direccion dir : Direccion.values()) {
            if (dir.getDesplazamientoFila() == difFila && dir.getDesplazamientoColumna() == difCol) {
                return dir;
            }
        }
        throw new IllegalArgumentException("No están en línea recta");
    }

    public Posicion sumar(Direccion direccion) {
        return new Posicion(this.fila + direccion.getDesplazamientoFila(), 
                            this.columna + direccion.getDesplazamientoColumna());
    }


    public int distanciaOrtogonalA(Posicion otra) {
        return Math.abs(this.fila - otra.fila) + Math.abs(this.columna - otra.columna);
    }


    public boolean esAdyacenteA(Posicion otra) {
        return this.distanciaOrtogonalA(otra) == 1;
    }


    public boolean estaEnLineaRectaCon(Posicion otra) {
        return this.fila == otra.fila || this.columna == otra.columna;
    }

    // --- MÉTODOS OBLIGATORIOS PARA USAR COMO CLAVE EN UN HASHMAP ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Posicion posicion = (Posicion) o;
        return fila == posicion.fila && columna == posicion.columna;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fila, columna);
    }

    @Override
    public String toString() {
        return "(" + fila + ", " + columna + ")";
    }

    
}
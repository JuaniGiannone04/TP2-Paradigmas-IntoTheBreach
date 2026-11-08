package edu.fiuba.algo3.modelo;

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

    /**
     * Retorna una nueva Posicion resultante de sumar el desplazamiento
     * de la dirección indicada. Garantiza la inmutabilidad.
     */
    public Posicion sumar(Direccion direccion) {
        return new Posicion(this.fila + direccion.getDesplazamientoFila(), 
                            this.columna + direccion.getDesplazamientoColumna());
    }

    /**
     * Calcula la distancia ortogonal (Manhattan) hacia otra posición.
     * Útil para validar rangos de movimiento.
     */
    public int distanciaOrtogonalA(Posicion otra) {
        return Math.abs(this.fila - otra.fila) + Math.abs(this.columna - otra.columna);
    }

    /**
     * Verifica si dos posiciones están pegadas ortogonalmente.
     * Útil para el ataque del Mech de Combate o los Vek.
     */
    public boolean esAdyacenteA(Posicion otra) {
        return this.distanciaOrtogonalA(otra) == 1;
    }

    /**
     * Verifica si está en la misma fila o columna.
     * Útil para validar los ataques en línea recta del Cañón y la Artillería.
     */
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
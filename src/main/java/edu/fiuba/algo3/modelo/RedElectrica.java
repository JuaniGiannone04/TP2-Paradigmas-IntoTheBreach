package edu.fiuba.algo3.modelo;

public class RedElectrica {
    private int energia;

    public RedElectrica(int energiaInicial) {
        this.energia = energiaInicial;
    }

    public void consumir(int cantidad) {
        this.energia = Math.max(0, this.energia - cantidad);
    }

    public int getEnergia() {
        return this.energia;
    }

    public boolean estaDestruida() {
        return this.energia == 0;
    }
}
package edu.fiuba.algo3.modelo.unidad;

public class Luciernaga extends Vek {

    public Luciernaga() {
        super(3);
    }
    @Override
    public void aplicarEfectoAgua() {
        this.recibirDanio(this.getVidaActual());
    }

}

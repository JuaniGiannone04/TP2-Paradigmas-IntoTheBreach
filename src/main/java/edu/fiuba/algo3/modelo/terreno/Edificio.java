package edu.fiuba.algo3.modelo.terreno;

import edu.fiuba.algo3.modelo.RedElectrica;
import edu.fiuba.algo3.modelo.unidad.Unidad;

public class Edificio implements Terreno {
    private final RedElectrica redElectrica;

    public Edificio(RedElectrica redElectrica) {
        this.redElectrica = redElectrica;
    }

    @Override
    public boolean esTransitablePorTerrestre() {
        return false;
    }

    @Override
    public boolean bloqueaDisparoDirecto() {
        return true;
    }

    @Override
    public void aplicarEfecto(Unidad unidad) {
        // No es transitable por unidades terrestres
    }

    @Override
    public Terreno recibirDanio(int danio) {
        if (danio > 0) {
            this.redElectrica.consumir(1);
            return new Suelo(); // State: Al destruirse pasa a ser Suelo
        }
        return this;
    }
}
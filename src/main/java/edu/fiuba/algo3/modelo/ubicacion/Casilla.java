package edu.fiuba.algo3.modelo.ubicacion;


import edu.fiuba.algo3.modelo.terreno.Terreno;
import edu.fiuba.algo3.modelo.unidad.Unidad;


public class Casilla {
    private Terreno terreno;
    private Unidad unidad;

    public Casilla(Terreno terrenoInicial) {
        this.terreno = terrenoInicial;
        this.unidad = null;
    }
    public void empujarHacia(Casilla casillaDestino) {
        if (!this.estaOcupada()) return;

        Unidad unidadEmpujada = this.unidad;

        boolean pudoEntrar = casillaDestino.recibirChoqueOEntrar(unidadEmpujada);

        if (pudoEntrar) {
            casillaDestino.colocarUnidad(this.quitarUnidad());
        }
    }

    public boolean recibirChoqueOEntrar(Unidad unidadEmpujada) {
        if (this.estaOcupada() || this.terreno.bloqueaDisparoDirecto()) {
            this.recibirDanio(1);
            unidadEmpujada.recibirDanio(1);
            return false;
        }
        return true;
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
    public boolean recibirUnidadOColisionar(Unidad unidadEmpujada) {
        if (this.estaOcupada() || this.terreno.bloqueaDisparoDirecto()) {
            this.recibirDanio(1);
            unidadEmpujada.recibirDanio(1);

            return false;
        }

        this.colocarUnidad(unidadEmpujada);
        return true;
    }

    public boolean detieneDisparoDirecto() {
        return this.estaOcupada() || this.terreno.bloqueaDisparoDirecto();
    }
}
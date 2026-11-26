package edu.fiuba.algo3.modelo.ubicacion;

import edu.fiuba.algo3.modelo.arma.Ataque;
import edu.fiuba.algo3.modelo.unidad.Unidad;

import java.util.Optional;

public class BordeDelMapa implements Celda {

    @Override
    public Celda vecinaAl(Direccion direccion) {
        return this;
    }

    @Override
    public boolean esAdyacenteA(Celda otraCelda) {
        return false;
    }

    @Override
    public Direccion direccionHacia(Celda destino) {
        throw new IllegalArgumentException("El borde no puede ser origen de un ataque");
    }

    @Override
    public boolean estaEnLineaRectaHacia(Direccion direccion, Celda destino) {
        return false;
    }

    @Override
    public void recibir(Ataque ataque) {
        ataque.impactarEn(this);
    }

    @Override
    public void propagar(Direccion direccion, Ataque ataque) {
        throw new IllegalArgumentException("No se puede disparar desde fuera del tablero");
    }



    @Override
    public void empujarUnidadInterna(Direccion direccion) {
    }

    @Override
    public boolean recibirUnidadEmpujada(Unidad unidadEmpujada) {
        return false;
    }


    @Override
    public Optional<Casilla> primerBloqueoEn(Direccion direccion) {return Optional.empty();}


}

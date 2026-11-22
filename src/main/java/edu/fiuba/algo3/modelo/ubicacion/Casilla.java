package edu.fiuba.algo3.modelo.ubicacion;

import edu.fiuba.algo3.modelo.terreno.Terreno;
import edu.fiuba.algo3.modelo.unidad.Unidad;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class Casilla implements Celda {
    private Terreno terreno;
    private Unidad unidad;
    private final  Map<Direccion, Celda> vecinas;

    public Casilla(Terreno terrenoInicial) {
        this.terreno = terrenoInicial;
        this.unidad = null;
        this.vecinas = new EnumMap<>(Direccion.class);
        BordeDelMapa borde = new BordeDelMapa();
        for (Direccion direccion : Direccion.values()) {
            this.vecinas.put(direccion, borde);
        }
    }

    public void conectarVecina(Direccion direccion, Celda vecina) {
        this.vecinas.put(direccion, vecina);
    }

    @Override
    public Celda vecinaAl(Direccion direccion) {
        return this.vecinas.get(direccion);
    }

    @Override
    public boolean esAdyacenteA(Celda otraCelda) {
        return this.vecinas.containsValue(otraCelda);
    }

    @Override
    public Direccion direccionHacia(Celda destino) {
        for (Direccion direccion : Direccion.values()) {
            if (this.vecinaAl(direccion).estaEnLineaRectaHacia(direccion, destino)) {
                return direccion;
            }
        }
        throw new IllegalArgumentException("El objetivo no se encuentra en línea recta");
    }

    @Override
    public boolean estaEnLineaRectaHacia(Direccion direccion, Celda destino) {
        if (this == destino) {
            return true;
        }
        return this.vecinaAl(direccion).estaEnLineaRectaHacia(direccion, destino);
    }

    @Override
    public void recibirProyectilEnArco(Celda destino, Direccion direccion, int danio) {
        if (this == destino) {
            this.recibirDanio(danio);
            for (Direccion direccionEmpuje : Direccion.values()) {
                this.vecinaAl(direccionEmpuje).empujarUnidadInterna(direccionEmpuje);
            }
        } else {
            this.vecinaAl(direccion).recibirProyectilEnArco(destino, direccion, danio);
        }
    }

    @Override
    public void empujarUnidadInterna(Direccion direccion) {
        if (!this.estaOcupada()) {
            return;
        }
        Celda celdaDestino = this.vecinaAl(direccion);
        if (celdaDestino.recibirUnidadEmpujada(this.unidad)) {
            this.unidad = null;
        }
    }

    private void limpiarSiEstaMuerta() {
        if (this.estaOcupada() && this.unidad.getVidaActual() == 0) {
            this.unidad = null;
        }
    }

    @Override
    public boolean recibirUnidadEmpujada(Unidad unidadEmpujada) {
        if (this.detieneProyectil()) {
            this.recibirDanio(1);
            unidadEmpujada.recibirDanio(1);
            return false;
        }
        this.colocarUnidad(unidadEmpujada);
        return true;
    }

    @Override
    public void recibirDanio(int danio) {
        if (this.estaOcupada()) {
            this.unidad.recibirDanio(danio);
            this.limpiarSiEstaMuerta();
        }
        this.terreno = this.terreno.recibirDanio(danio);
    }

    public void colocarUnidad(Unidad nuevaUnidad) {
        if (this.estaOcupada()) {
            throw new IllegalStateException(
                    "La casilla ya está ocupada por otra unidad."
            );
        }
        this.unidad = nuevaUnidad;
        nuevaUnidad.asignarCasilla(this);
        this.terreno.aplicarEfecto(nuevaUnidad);
        this.limpiarSiEstaMuerta();
    }

    public Unidad quitarUnidad() {
        Unidad unidadRetirada = this.unidad;
        this.unidad = null;
        return unidadRetirada;
    }

    public void moverUnidadHacia(Casilla destino) {
        if (!this.estaOcupada()) {
            throw new IllegalStateException("No hay unidad para mover en la casilla de origen.");
        }
        if(!destino.puedeSerOcupadaPor(this.unidad)) {
            throw new IllegalStateException("La unidad no puede ocupar la casilla de destino.");
        }

        destino.colocarUnidad(this.quitarUnidad());
    }

    @Override
    public Optional<Casilla> primerBloqueoEn(Direccion direccion){
        if(this.detieneProyectil()){
            return Optional.of(this);
        }

        return this.vecinaAl(direccion).primerBloqueoEn(direccion);
    }


    public boolean puedeSerOcupadaPor(Unidad nuevaUnidad) {
        return !this.estaOcupada() && nuevaUnidad.puedeTransitar(this.terreno);
    }

    public boolean estaOcupada() {
        return this.unidad != null;
    }

    private boolean detieneProyectil() {
        return this.estaOcupada() || this.terreno.bloqueaDisparoDirecto();
    }

    public void contaminarOcupante(){
        if(this.estaOcupada()){
            this.unidad.aplicarEfectoAcido();
        }
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
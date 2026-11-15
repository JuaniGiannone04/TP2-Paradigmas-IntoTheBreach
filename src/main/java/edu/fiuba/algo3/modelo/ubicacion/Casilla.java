package edu.fiuba.algo3.modelo.ubicacion;

import edu.fiuba.algo3.modelo.terreno.Terreno;
import edu.fiuba.algo3.modelo.unidad.Unidad;
import java.util.EnumMap;
import java.util.Map;

public class Casilla implements Celda {
    private Terreno terreno;
    private Unidad unidad;
    private final Map<Direccion, Celda> vecinas;

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
    public Direccion direccionEnLineaRectaHacia(Celda destino) {
        for (Direccion direccion : Direccion.values()) {
            if (this.vecinaAl(direccion).distanciaEnDireccionHacia(direccion, destino) != -1) {
                return direccion;
            }
        }
        throw new IllegalArgumentException("El objetivo no se encuentra en línea recta");
    }

    @Override
    public int distanciaEnDireccionHacia(Direccion direccion, Celda destino) {
        if (this == destino) {
            return 1;
        }
        int distanciaRestante = this.vecinaAl(direccion).distanciaEnDireccionHacia(direccion, destino);
        if (distanciaRestante == -1) {
            return -1;
        }
        return 1 + distanciaRestante;
    }
    @Override
    public Celda vecinaAl(Direccion direccion) {
        return this.vecinas.get(direccion);
    }

    @Override
    public void recibirGolpeCuerpoACuerpo(Direccion direccion, int danio) {
        this.recibirDanio(danio);
        this.empujarUnidadInterna(direccion);
    }

    @Override
    public void recibirProyectilRecto(Direccion direccion, int danio) {
        if (this.detieneProyectil()) {
            this.recibirDanio(danio);
            this.empujarUnidadInterna(direccion);
        } else {
            this.vecinaAl(direccion).recibirProyectilRecto(direccion, danio);
        }
    }

    @Override
    public void recibirProyectilEnArco(Direccion direccion, int pasosRestantes, int danio) {
        if (pasosRestantes > 0) {
            this.vecinaAl(direccion).recibirProyectilEnArco(direccion, pasosRestantes - 1, danio);
        } else {
            this.recibirDanio(danio);
            for (Direccion direccionEmpuje : Direccion.values()) {
                this.vecinaAl(direccionEmpuje).empujarUnidadInterna(direccionEmpuje);
            }
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


    public void recibirDanio(int danio) {
        if (this.estaOcupada()) {
            this.unidad.recibirDanio(danio);
        }
        this.terreno = this.terreno.recibirDanio(danio);
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

    public void moverUnidadHacia(Casilla destino) {
        if (!this.estaOcupada()) {
            throw new IllegalStateException("No hay unidad para mover en la casilla de origen.");
        }
        destino.colocarUnidad(this.quitarUnidad());
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
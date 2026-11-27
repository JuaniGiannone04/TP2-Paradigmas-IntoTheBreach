package edu.fiuba.algo3.modelo.ubicacion;

import edu.fiuba.algo3.modelo.arma.Ataque;
import edu.fiuba.algo3.modelo.terreno.Terreno;
import edu.fiuba.algo3.modelo.unidad.Unidad;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public Celda vecinaAl(Direccion direccion) {
        return this.vecinas.get(direccion);
    }

    @Override
    public boolean esAdyacenteA(Celda otraCelda) {
        return this.vecinas.containsValue(otraCelda);
    }

    @Override
    public Direccion direccionHacia(Celda destino) {
        if (this == destino) {
            throw new IllegalArgumentException("El origen y el destino no pueden ser la misma casilla.");
        }
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

    public boolean estaEnLineaRectaCon(Celda destino) {
        for (Direccion direccion : Direccion.values()) {
            if (this.vecinaAl(direccion).estaEnLineaRectaHacia(direccion, destino)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void recibir(Ataque ataque) {
        ataque.impactarEn(this);
    }

    @Override
    public void propagar(Direccion direccion, Ataque ataque) {
        this.vecinaAl(direccion).recibir(ataque);
    }

    @Override
    public Optional<Casilla> primerBloqueoEn(Direccion direccion) {
        if (this.detieneProyectil()) {
            return Optional.of(this);
        }
        return this.vecinaAl(direccion).primerBloqueoEn(direccion);
    }

    public void empujarEnTodasLasDirecciones() {
        for (Direccion direccionEmpuje : Direccion.values()) {
            this.vecinaAl(direccionEmpuje).empujarUnidadInterna(direccionEmpuje);
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
        } else {
            this.limpiarSiEstaMuerta();
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
            this.limpiarSiEstaMuerta();
        }
        this.terreno = this.terreno.recibirDanio(danio);
    }

    public void contaminarOcupante() {
        if (this.estaOcupada()) {
            this.unidad.aplicarEfectoAcido();
        }
    }

    public void colocarUnidad(Unidad nuevaUnidad) {
        if (this.estaOcupada()) {
            throw new IllegalStateException("La casilla ya está ocupada por otra unidad.");
        }
        this.unidad = nuevaUnidad;
        nuevaUnidad.asignarCasilla(this);
        this.terreno.aplicarEfecto(nuevaUnidad);
        this.limpiarSiEstaMuerta();
    }

    public Unidad quitarUnidad() {
        Unidad unidadRetirada = this.unidad;
        this.unidad = null;
        if (unidadRetirada != null) {
            unidadRetirada.abandonarCasilla();
        }
        return unidadRetirada;
    }

    public void moverUnidadHacia(Casilla destino) {
        if (!this.estaOcupada()) {
            throw new IllegalStateException("No hay unidad para mover en la casilla de origen.");
        }
        if (!destino.puedeSerOcupadaPor(this.unidad)) {
            throw new IllegalStateException("La unidad no puede ocupar la casilla de destino.");
        }
        destino.colocarUnidad(this.quitarUnidad());
    }

    private void limpiarSiEstaMuerta() {
        if (this.estaOcupada() && this.unidad.getVidaActual() == 0) {
            this.quitarUnidad();
        }
    }

    public boolean puedeSerOcupadaPor(Unidad nuevaUnidad) {
        return !this.estaOcupada() && nuevaUnidad.puedeTransitar(this.terreno);
    }

    public boolean estaOcupada() {
        return this.unidad != null;
    }

    public boolean contieneUnidad(Unidad unidad) {
        return this.unidad == unidad;
    }

    public boolean detieneProyectil() {
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

    public Casilla moverUnidadPor(List<Direccion> camino) {
        if (!this.estaOcupada()) {
            throw new IllegalStateException("No hay unidad para mover en la casilla de origen.");
        }
        if (camino == null || camino.isEmpty()) {
            throw new IllegalArgumentException("El movimiento debe tener al menos un paso.");
        }

        int pasosPrevios = this.unidad.guardarPasos();
        try {
            Casilla destino = this.vecinaAl(camino.get(0)).avanzarMovimiento(this.unidad, camino, 0);
            this.unidad = null;
            return destino;
        } catch (RuntimeException e) {
            this.unidad.restaurarPasos(pasosPrevios);
            throw e;
        }
    }

    @Override
    public Casilla avanzarMovimiento(Unidad unidad, List<Direccion> camino, int pasoActual) {
        unidad.darPaso();

        boolean esUltimoPaso = (pasoActual == camino.size() - 1);
        if (esUltimoPaso) {
            if (!this.puedeSerOcupadaPor(unidad)) {
                throw new IllegalStateException("La casilla destino está ocupada o no es transitable.");
            }
            this.colocarUnidad(unidad);
            return this;
        }

        if (!this.puedeSerAtravesadaPor(unidad)) {
            throw new IllegalStateException("El camino está bloqueado.");
        }

        Direccion siguienteDireccion = camino.get(pasoActual + 1);
        return this.vecinaAl(siguienteDireccion).avanzarMovimiento(unidad, camino, pasoActual + 1);
    }

    public boolean puedeSerAtravesadaPor(Unidad unidad) {
        return this.puedeSerOcupadaPor(unidad);
    }
}
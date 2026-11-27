package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.arma.ProyectilContaminante;
import edu.fiuba.algo3.modelo.terreno.Terreno;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;

public class Luciernaga extends Vek {
// que reciba un arma por parametro la luciernaga tambien?
    public Luciernaga() {
        super(3, 2);

    }

    @Override
    public void aplicarEfectoAgua() {
        this.recibirDanio(this.getVidaActual());
    }
    @Override
    public boolean puedeAtacarDesde(Casilla origen, Casilla destino) {
        if (origen == destino || !origen.estaEnLineaRectaCon(destino)) {
            return false;
        }

        // revisar la almda funciont
        Direccion direccion = origen.direccionHacia(destino);
        return origen.vecinaAl(direccion)
                .primerBloqueoEn(direccion)
                .map(bloqueo -> bloqueo == destino)
                .orElse(false);
    }

    @Override
    public void atacar(Celda origen, Celda destino) {
        Direccion direccion = origen.direccionHacia(destino);
        origen.propagar(direccion, new ProyectilContaminante(direccion, 1));
    }
    @Override
    public boolean puedeTransitar(Terreno terreno) {
        return terreno.esTransitablePorTerrestre();
    }




}

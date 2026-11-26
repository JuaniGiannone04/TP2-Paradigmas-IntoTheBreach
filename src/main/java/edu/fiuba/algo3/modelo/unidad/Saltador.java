package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.arma.GolpeCuerpoACuerpo;
import edu.fiuba.algo3.modelo.arma.ProyectilRecto;
import edu.fiuba.algo3.modelo.terreno.Terreno;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;

public class Saltador extends Vek {

    public Saltador() {
        super(2, 3);
    }
    @Override
    public boolean puedeAtacarDesde(Casilla origen, Casilla objetivo) {
        return origen.esAdyacenteA(objetivo);
    }

    @Override
    public void aplicarEfectoAgua() {
        this.recibirDanio(this.getVidaActual());
    }

    @Override
    public void atacar(Celda origen, Celda destino) {
        Direccion direccion = origen.direccionHacia(destino);
        origen.propagar(direccion, new GolpeCuerpoACuerpo(direccion, 2));
    }

    @Override
    public boolean puedeTransitar(Terreno terreno) {
        return terreno.esTransitablePorTerrestre();
    }


}
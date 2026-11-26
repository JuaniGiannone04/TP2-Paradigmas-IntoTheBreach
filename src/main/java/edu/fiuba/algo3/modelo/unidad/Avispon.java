package edu.fiuba.algo3.modelo.unidad;

import edu.fiuba.algo3.modelo.arma.GolpeCuerpoACuerpo;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;

public class Avispon extends Vek {

    public Avispon() {
        super(2, 4);
    }

//Hay que refactorizar la logica esta que rompe tell dont ask, demeter y todo lo qu es sagrado

    @Override
    public void atacar(Celda origen, Celda destino) {
        Direccion direccion = origen.direccionHacia(destino);
        origen.propagar(direccion, new GolpeCuerpoACuerpo(direccion, 1));
    }

    @Override
    public void aplicarEfectoAcido() {

    }
    @Override
    public boolean puedeAtacarDesde(Casilla origen, Casilla objetivo) {
        return origen.esAdyacenteA(objetivo);
    }
}



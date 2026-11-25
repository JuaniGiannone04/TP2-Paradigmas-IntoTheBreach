package edu.fiuba.algo3.modelo.arma;

import edu.fiuba.algo3.modelo.ubicacion.BordeDelMapa;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Celda;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;

public class ProyectilEnArco implements Ataque {
    private final Celda objetivo;
    private final Direccion direccion;
    private final int danio;

    public ProyectilEnArco(Celda objetivo, Direccion direccion, int danio) {
        this.objetivo = objetivo;
        this.direccion = direccion;
        this.danio = danio;
    }

    @Override
    public void impactarEn(Casilla casilla) {
        if (casilla == this.objetivo) {
            casilla.recibirDanio(this.danio);
            casilla.empujarEnTodasLasDirecciones();
        } else {
            casilla.propagar(this.direccion, this);
        }
    }

    @Override
    public void impactarEn(BordeDelMapa borde) {
        throw new IllegalArgumentException("No se puede disparar fuera del tablero");
    }
}
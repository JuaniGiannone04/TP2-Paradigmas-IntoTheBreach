package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.partida.EnCurso;
import edu.fiuba.algo3.modelo.partida.EstadoPartida;
import edu.fiuba.algo3.modelo.turno.Turno;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;

public class Juego {
    private static final int ENERGIA_INICIAL = 5;
    private final Tablero tablero;
    private final RedElectrica redElectrica;
    private final Turno turno;
    private EstadoPartida estado = new EnCurso();



    public Juego() {
        this.redElectrica = new RedElectrica(ENERGIA_INICIAL);
        GeneradorDeTablero fabrica = new GeneradorDeTablero();
        this.tablero = fabrica.crearTablero(this.redElectrica);
        this.turno = new Turno();
    }

    public Juego(Tablero tableroPersonalizado, RedElectrica redElectrica) {
        this.tablero = tableroPersonalizado;
        this.redElectrica = redElectrica;
        this.turno = new Turno();
    }

    public Tablero getTablero() {
        return this.tablero;
    }

    public int getEnergiaRedElectrica() {
        return this.redElectrica.getEnergia();
    }

    public void avanzarTurno() {
        if (!estado.permiteAvanzar()) {
            throw new IllegalStateException("La partida ya terminó");
        }
        turno.avanzarTurno();
        estado = estado.evaluar(turno.ultimoTurnoCompletado(), redElectrica.getEnergia(), tablero.cantidadDeMechs());
    }


}
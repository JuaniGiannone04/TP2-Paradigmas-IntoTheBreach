package edu.fiuba.algo3.modelo;

import edu.fiuba.algo3.modelo.RedElectrica;
import edu.fiuba.algo3.modelo.terreno.*;
import edu.fiuba.algo3.modelo.ubicacion.Casilla;
import edu.fiuba.algo3.modelo.ubicacion.Direccion;
import edu.fiuba.algo3.modelo.ubicacion.Tablero;

import java.util.ArrayList;
import java.util.List;

public class GeneradorDeTablero {
    private static final int DIMENSION = 8;

    public Tablero crearTableroVacio() {
        Casilla[][] grillaTemporal = this.construirGrafoBase();
        return new Tablero(this.convertirALista(grillaTemporal));
    }

    public Tablero crearTableroPredefinido(RedElectrica redElectrica) {
        Casilla[][] grillaTemporal = this.construirGrafoBase();

        grillaTemporal[0][0].cambiarTerreno(new Montana());
        grillaTemporal[0][1].cambiarTerreno(new Montana());
        grillaTemporal[1][0].cambiarTerreno(new Montana());

        grillaTemporal[2][2].cambiarTerreno(new Edificio(redElectrica));
        grillaTemporal[2][3].cambiarTerreno(new Edificio(redElectrica));
        grillaTemporal[5][4].cambiarTerreno(new Edificio(redElectrica));

        grillaTemporal[7][6].cambiarTerreno(new Agua());
        grillaTemporal[7][7].cambiarTerreno(new Agua());

        grillaTemporal[3][5].cambiarTerreno(new Acido());
        grillaTemporal[4][5].cambiarTerreno(new Acido());

        return new Tablero(this.convertirALista(grillaTemporal));
    }

    private Casilla[][] construirGrafoBase() {
        Casilla[][] grilla = new Casilla[DIMENSION][DIMENSION];

        for (int fila = 0; fila < DIMENSION; fila++) {
            for (int columna = 0; columna < DIMENSION; columna++) {
                grilla[fila][columna] = new Casilla(new Suelo());
            }
        }

        this.conectarVecinas(grilla);
        return grilla;
    }

    private void conectarVecinas(Casilla[][] grilla) {
        for (int fila = 0; fila < DIMENSION; fila++) {
            for (int columna = 0; columna < DIMENSION; columna++) {
                Casilla actual = grilla[fila][columna];
                if (fila > 0) {
                    actual.conectarVecina(Direccion.NORTE, grilla[fila - 1][columna]);
                }
                if (fila < DIMENSION - 1) {
                    actual.conectarVecina(Direccion.SUR, grilla[fila + 1][columna]);
                }
                if (columna > 0) {
                    actual.conectarVecina(Direccion.OESTE, grilla[fila][columna - 1]);
                }
                if (columna < DIMENSION - 1) {
                    actual.conectarVecina(Direccion.ESTE, grilla[fila][columna + 1]);
                }
            }
        }
    }

    private List<Casilla> convertirALista(Casilla[][] grilla) {
        List<Casilla> lista = new ArrayList<>();
        for (int fila = 0; fila < DIMENSION; fila++) {
            for (int columna = 0; columna < DIMENSION; columna++) {
                lista.add(grilla[fila][columna]);
            }
        }
        return lista;
    }
}
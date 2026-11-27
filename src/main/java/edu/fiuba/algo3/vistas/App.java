package edu.fiuba.algo3.vistas;

import edu.fiuba.algo3.modelo.Juego;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        Juego juego = new Juego();
        GeneradorVistaTablero generadorVista = new GeneradorVistaTablero();
        VistaTablero vistaTablero = generadorVista.generar(juego.getTablero());

        Scene scene = new Scene(new StackPane(vistaTablero), 800, 650);
        stage.setTitle("Into the Breach - Tablero");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
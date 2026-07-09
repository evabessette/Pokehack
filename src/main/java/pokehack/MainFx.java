package pokehack;

import pokehack.controller.PokemonController;
import pokehack.view.PokemonViewFx;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class MainFx extends Application {

    @Override
    public void start(Stage stage){

        javafx.scene.text.Font.loadFont(
                getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"),
                24);

        PokemonViewFx view = new PokemonViewFx();

        PokemonController ctrl = new PokemonController(view);

        Scene scene = new Scene(view.getRoot(), 400, 650);
        scene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm()
        );
        stage.setTitle("Pokédex - Recherche de Pokémon");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
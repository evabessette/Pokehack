package pokehack;

import pokehack.view.PokemonViewFx;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class MainFx extends Application {

    @Override
    public void start(Stage stage){
        PokemonViewFx view = new PokemonViewFx();

//        PokemonController ctrl = new PokemonController(view);

        Scene scene = new Scene(view.getRoot(), 400, 600);
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
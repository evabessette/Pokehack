package Pokehack;

import Pokehack.Controller.PokemonController;
import Pokehack.View.PokemonViewFx;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MainFx extends Application {

    @Override
    public void start(Stage stage){
        PokemonViewFx view = new PokemonViewFx();

        PokemonController ctrl = new PokemonController(view);

        Scene scene = new Scene(view.getRoot(), 900, 500);

    }
}
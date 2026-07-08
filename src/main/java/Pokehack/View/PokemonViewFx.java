package Pokehack.View;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PokemonViewFx {

    public final Label statistiquesTitre;
    public final Label typePokemon;
    public final Label nomPokemon;
    public final Label idPokemon;
    public final Label hp;
    public final Label attack;
    public final Label defense;
    public final Label speed;
    public final Label weight;
    public final Label height;
    public final TextField champNomPokemon;
    public final ImageView imagePokemon;
    public final Label messageErreur;

    private final VBox racine;

    public PokemonViewFx() {

        imagePokemon = new ImageView();
        imagePokemon.setFitWidth(200);
        imagePokemon.setPreserveRatio(true);

        champNomPokemon = new TextField();
        champNomPokemon.setPromptText("nom d'un Pokémon");
        HBox.setHgrow(champNomPokemon, Priority.ALWAYS);

        messageErreur = new Label();

    }

    public Parent getRoot(){
        return racine;
    }
}

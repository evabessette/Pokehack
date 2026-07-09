package Pokehack.View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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
    public final Button catchButton;

    private final VBox racine;
    private final HBox measurements;
    private final VBox stats;
    private final HBox recherche;
    private final VBox card;


    public PokemonViewFx() {

        imagePokemon = new ImageView();
        imagePokemon.setFitWidth(200);
        imagePokemon.setPreserveRatio(true);

        champNomPokemon = new TextField();
        champNomPokemon.setPromptText("nom d'un Pokémon");
        HBox.setHgrow(champNomPokemon, Priority.ALWAYS);
        champNomPokemon.getStyleClass().add("champ-nom-pokemon");

        catchButton = new Button("Attraper");
        catchButton.getStyleClass().add("card");

        messageErreur = new Label();
        messageErreur.getStyleClass().add("message-erreur");

        statistiquesTitre = new Label("Statistiques");
        typePokemon = new Label();
        nomPokemon = new Label();
        idPokemon = new Label();
        hp = new Label();
        attack = new Label();
        defense = new Label();
        speed = new Label();
        weight = new Label();
        height = new Label();

        // Containers

        measurements = new HBox(16, weight, height);
        measurements.setAlignment(Pos.CENTER);
        stats = new VBox(10, statistiquesTitre, hp, attack, defense, speed);
        recherche = new HBox(16, champNomPokemon, catchButton);
        card = new VBox(10, nomPokemon, imagePokemon, idPokemon, typePokemon, measurements, stats);
        card.setAlignment(Pos.CENTER);
        racine = new VBox(card, recherche, messageErreur);
        racine.setPadding(new Insets(24));





    }

    public Parent getRoot(){
        return racine;
    }
}

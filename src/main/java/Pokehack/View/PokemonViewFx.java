package Pokehack.View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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

        imagePokemon = new ImageView(new Image(
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                true));
        imagePokemon.setFitWidth(200);
        imagePokemon.setPreserveRatio(true);
        imagePokemon.setSmooth(false);

        champNomPokemon = new TextField();
        champNomPokemon.setPromptText("nom d'un Pokémon");
        HBox.setHgrow(champNomPokemon, Priority.ALWAYS);
        champNomPokemon.getStyleClass().add("champ-nom-pokemon");

        catchButton = new Button("Attraper");
        catchButton.getStyleClass().add("bouton-catch");

        messageErreur = new Label();
        messageErreur.getStyleClass().add("message-erreur");

        statistiquesTitre = new Label("Statistiques");
        typePokemon = new Label("Plante");
        nomPokemon = new Label("Bulbizarre");
        idPokemon = new Label("#01");
        hp = new Label("HP");
        attack = new Label("ATK");
        defense = new Label("DEF");
        speed = new Label("SPD");
        weight = new Label("Poids");
        height = new Label("Taille");

        // Containers

        measurements = new HBox(16, weight, height);
        measurements.setAlignment(Pos.CENTER);
        stats = new VBox(10, statistiquesTitre, hp, attack, defense, speed);
        recherche = new HBox(16, champNomPokemon, catchButton);
        card = new VBox(10, nomPokemon, imagePokemon, idPokemon, typePokemon, measurements, stats);
        card.setAlignment(Pos.CENTER);
        racine = new VBox(card, recherche, messageErreur);
        racine.setPadding(new Insets(24));

        card.getStyleClass().add("card");
        stats.getStyleClass().add("stats");
        measurements.getStyleClass().add("measurements");
        recherche.getStyleClass().add("recherche");
        racine.getStyleClass().add("racine");





    }

    public Parent getRoot(){
        return racine;
    }
}

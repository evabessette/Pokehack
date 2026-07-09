package pokehack.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PokemonViewFx {

    public final Label nomPokemon, idPokemon, typePokemon;
    public final Label hp, attack, defense, speed;
//    public final Label weight, height;
    public final Label statistiquesTitre, messageErreur;
    public final Label weightLabel, heightLabel;
    public final Label weightValue, heightValue;
    public final TextField champNomPokemon;
    public final ImageView imagePokemon;
    public final Button catchButton;

    private final VBox racine;
    private final HBox measurements;
    private final HBox recherche;
    private final VBox card;
    private final VBox stats;
    private final VBox weight;
    private final VBox height;

    private final HBox statsBarHP;
    public final ProgressBar hpProgress;
    public final Label hpNumber;

    private final HBox statsBarATK;
    public final ProgressBar atkProgress;
    public final Label atkNumber;

    private final HBox statsBarDEF;
    public final ProgressBar defProgress;
    public final Label defNumber;

    private final HBox statsBarSPD;
    public final ProgressBar spdProgress;
    public final Label spdNumber;


    public PokemonViewFx() {

        imagePokemon = new ImageView(new Image(
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
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
        typePokemon.getStyleClass().add("type-pokemon");
        nomPokemon = new Label("Bulbizarre");
        nomPokemon.getStyleClass().add("nom-pokemon");
        idPokemon = new Label("#01");
        idPokemon.getStyleClass().add("id-pokemon");

        hp = new Label("HP");
        hpProgress = new ProgressBar(0.50);
        hpNumber = new Label("50");

        attack = new Label("ATK");
        atkProgress = new ProgressBar(0.40);
        atkNumber = new Label("40");

        defense = new Label("DEF");
        defProgress = new ProgressBar(0.30);
        defNumber = new Label("30");

        speed = new Label("SPD");
        spdProgress = new ProgressBar(0.60);
        spdNumber = new Label("60");


        weightLabel = new Label("Weight");
        heightLabel = new Label("Height");

        weightValue = new Label("6.5 kg");
        heightValue = new Label("50 cm");

        weight = new VBox(weightLabel, weightValue);
        height = new VBox(heightLabel, heightValue);

        // Containers

        measurements = new HBox(16, weight, height);
        measurements.setAlignment(Pos.CENTER);
//        stats = new VBox(10, statistiquesTitre, hp, attack, defense, speed);
        statsBarHP = new HBox(10, hp, hpProgress, hpNumber);
        statsBarHP.setAlignment(Pos.CENTER);
        statsBarATK = new HBox(10, attack, atkProgress, atkNumber);
        statsBarATK.setAlignment(Pos.CENTER);
        statsBarDEF = new HBox(10, defense, defProgress, defNumber);
        statsBarDEF.setAlignment(Pos.CENTER);
        statsBarSPD = new HBox(10, speed, spdProgress, spdNumber);
        statsBarSPD.setAlignment(Pos.CENTER);

        stats = new VBox(10, statsBarHP, statsBarATK, statsBarDEF, statsBarSPD);
        stats.setAlignment(Pos.CENTER);

        recherche = new HBox(16, champNomPokemon, catchButton);
        card = new VBox(10, nomPokemon, imagePokemon, idPokemon, typePokemon, measurements, stats);
        card.setAlignment(Pos.CENTER);
        racine = new VBox(recherche, card, messageErreur);
        racine.setPadding(new Insets(24));

        card.getStyleClass().add("card");
        statsBarHP.getStyleClass().add("stats-bar-hp");
        measurements.getStyleClass().add("measurements");
        recherche.getStyleClass().add("recherche");
        racine.getStyleClass().add("racine");





    }

    public Parent getRoot(){
        return racine;
    }
}

package pokehack.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PokemonViewFx {

    public final Label nomPokemon, idPokemon, typePokemon;
    public final Label hp, attack, defense, speed;
    public final Label statistiquesTitre, messageErreur;
    public final Label weightLabel, heightLabel;
    public final Label weightValue, heightValue;
    public final TextField champNomPokemon;
    public final ImageView imagePokemon;
    public final Button catchButton;
    public final ToggleButton inventoryToggle;

    private final HBox racine;
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

    // Making horizontal layout

    private final HBox layoutPrincipal;
    private final VBox inventairePanel;
    private static final double LARGEUR_CONTENU_PRINCIPAL = 400;
    private static final double LARGEUR_INVENTAIRE = 200;


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

        inventoryToggle = new ToggleButton("Montrer l'inventaire");
        inventoryToggle.getStyleClass().add("toggle-inventaire");


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
        hpProgress.getStyleClass().add("hp-progress");
        hpNumber = new Label("50");

        attack = new Label("ATK");
        atkProgress = new ProgressBar(0.40);
        atkProgress.getStyleClass().add("atk-progress");
        atkNumber = new Label("40");

        defense = new Label("DEF");
        defProgress = new ProgressBar(0.30);
        defProgress.getStyleClass().add("def-progress");
        defNumber = new Label("30");

        speed = new Label("SPD");
        spdProgress = new ProgressBar(0.60);
        spdProgress.getStyleClass().add("spd-progress");
        spdNumber = new Label("60");


        weightLabel = new Label("Weight");
        heightLabel = new Label("Height");

        weightValue = new Label("6.5 kg");
        heightValue = new Label("50 cm");

        weight = new VBox(weightLabel, weightValue);
        height = new VBox(heightLabel, heightValue);

        weight.getStyleClass().add("measurement-box");
        height.getStyleClass().add("measurement-box");

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
        card = new VBox(10, nomPokemon, imagePokemon, idPokemon, typePokemon, measurements, statistiquesTitre, stats, inventoryToggle);
        card.setAlignment(Pos.CENTER);

        card.getStyleClass().add("card");
        statsBarHP.getStyleClass().add("stats-bar-hp");
        measurements.getStyleClass().add("measurements");
        recherche.getStyleClass().add("recherche");

        // Horizontal layout

        Label inventaireTitre = new Label ("Pokémon attrapés");

        VBox listePokemon = new VBox(8, new Label("Bulbizarre"), new Label("Salamèche"), new Label("Carapuce"));

        inventairePanel = new VBox(12, inventaireTitre, listePokemon);
        inventairePanel.setPrefWidth(LARGEUR_INVENTAIRE);
        inventairePanel.setMinWidth(LARGEUR_INVENTAIRE);
        inventaireTitre.setPadding(new Insets(16));
        inventairePanel.getStyleClass().add("inventaire-panel");

        VBox contenuPrincipal = new VBox(recherche, card, messageErreur);
        contenuPrincipal.setPrefWidth(LARGEUR_CONTENU_PRINCIPAL);
        contenuPrincipal.setMinWidth(LARGEUR_CONTENU_PRINCIPAL);
        contenuPrincipal.setPadding(new Insets(24));
        layoutPrincipal = new HBox(contenuPrincipal, inventairePanel);
        HBox.setHgrow(contenuPrincipal, Priority.NEVER);
        HBox.setHgrow(inventairePanel, Priority.NEVER);

        inventairePanel.setVisible(false);
        inventairePanel.setManaged(false);

        inventoryToggle.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            inventairePanel.setVisible(isSelected);
            inventairePanel.setManaged(isSelected);
            Platform.runLater(() -> resizeWindowForInventory(isSelected));
        });

        racine = layoutPrincipal;
        racine.getStyleClass().add("racine");
    }

    public Parent getRoot(){
        return racine;
    }

    private void resizeWindowForInventory(boolean inventoryVisible) {
            Stage stage = (Stage) racine.getScene().getWindow();

            double changementLargeur;

            if (inventoryVisible) {
                changementLargeur = LARGEUR_INVENTAIRE;
            } else {
                changementLargeur = -LARGEUR_INVENTAIRE;
            }
            stage.setWidth(stage.getWidth() + changementLargeur);
        }
}

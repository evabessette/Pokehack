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
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pokehack.modele.Pokemon;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class PokemonViewFx {

    public final Label nomPokemon, idPokemon, typePokemon, typePokemon2;
    public final Label hp, attack, defense, speed;
    public final Label statistiquesTitre, messageErreur;
    public final Label weightLabel, heightLabel;
    public final Label weightValue, heightValue;
    public final TextField champNomPokemon;
    public final ImageView imagePokemon;
    public final ImageView primaryTypeIcon;
    public final ImageView primaryTypeName;
    public final ImageView secondaryTypeIcon;
    public final ImageView secondaryTypeName;
    public final Button catchButton;
    public final ToggleButton inventoryToggle;

    private final HBox racine;
    private final HBox measurements;
    private final HBox recherche;
    private final VBox card;
    private final VBox stats;
    private final VBox weight;
    private final VBox height;
    private final HBox types;

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

    private final HBox layoutPrincipal;
    private final VBox inventairePanel;
    private final VBox listePokemon;

    private static final double LARGEUR_MIN_CONTENU_PRINCIPAL = 280;
    private static final double LARGEUR_MIN_INVENTAIRE = 160;
    private static final double LARGEUR_PREF_INVENTAIRE = 220;
    private static final double LARGEUR_MAX_INVENTAIRE = 260;

    public PokemonViewFx() {

        imagePokemon = new ImageView(new Image(
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
            true
        ));
        imagePokemon.setFitWidth(200);
        imagePokemon.setPreserveRatio(true);
        imagePokemon.setSmooth(false);

        champNomPokemon = new TextField();
        champNomPokemon.setPromptText("Entrez le nom ou l'ID du Pokémon");
        HBox.setHgrow(champNomPokemon, Priority.ALWAYS);
        champNomPokemon.getStyleClass().add("champ-nom-pokemon");

        // Button attraper
        catchButton = new Button("Attraper");
        ImageView pokeballIcon = new ImageView(
            Objects.requireNonNull(
                    getClass().getResource("/images/pokeball.png"),
                    "Image introuvable : /images/pokeball.png"
            ).toExternalForm()
        );
        pokeballIcon.setFitWidth(24);
        pokeballIcon.setFitHeight(24);
        pokeballIcon.setPreserveRatio(true);
        catchButton.setGraphic(pokeballIcon);
        catchButton.setGraphicTextGap(8);
        catchButton.getStyleClass().add("bouton-catch");

        inventoryToggle = new ToggleButton("Montrer l'inventaire");
        inventoryToggle.getStyleClass().add("toggle-inventaire");

        messageErreur = new Label();
        messageErreur.getStyleClass().add("message-erreur");

        statistiquesTitre = new Label("Statistiques");
        statistiquesTitre.getStyleClass().add("statistiques-titre");

        typePokemon = new Label("Plante");
        typePokemon.getStyleClass().add("type-pokemon");

        typePokemon2 = new Label("Poison");
        typePokemon2.getStyleClass().add("type-pokemon2");

        primaryTypeIcon = new ImageView();
        primaryTypeIcon.setFitHeight(24);
        primaryTypeIcon.setFitWidth(24);
        primaryTypeIcon.setPreserveRatio(true);
        primaryTypeIcon.setSmooth(true);

        primaryTypeName = new ImageView();
        primaryTypeName.setFitHeight(24);
        primaryTypeName.setFitWidth(24);
        primaryTypeName.setPreserveRatio(true);
        primaryTypeName.setSmooth(true);

        secondaryTypeIcon = new ImageView();
        secondaryTypeIcon.setFitHeight(24);
        secondaryTypeIcon.setFitWidth(24);
        secondaryTypeIcon.setPreserveRatio(true);
        secondaryTypeIcon.setSmooth(true);

        secondaryTypeName = new ImageView();
        secondaryTypeName.setFitHeight(24);
        secondaryTypeName.setFitWidth(24);
        secondaryTypeName.setPreserveRatio(true);
        secondaryTypeName.setSmooth(true);

        types = new HBox(10, primaryTypeIcon, primaryTypeName, secondaryTypeIcon, secondaryTypeName);
        types.getStyleClass().add("types");
        types.setAlignment(Pos.CENTER);

        nomPokemon = new Label("Bulbizarre");
        nomPokemon.getStyleClass().add("nom-pokemon");

        idPokemon = new Label("#1");
        idPokemon.getStyleClass().add("id-pokemon");

        hp = new Label("HP ");
        hpProgress = new ProgressBar(0.50);
        hpProgress.getStyleClass().add("hp-progress");
        hpProgress.setMaxWidth(Double.MAX_VALUE);
        hpNumber = new Label("50");

        attack = new Label("ATK");
        atkProgress = new ProgressBar(0.40);
        atkProgress.getStyleClass().add("atk-progress");
        atkProgress.setMaxWidth(Double.MAX_VALUE);
        atkNumber = new Label("40");

        defense = new Label("DEF");
        defProgress = new ProgressBar(0.30);
        defProgress.getStyleClass().add("def-progress");
        defProgress.setMaxWidth(Double.MAX_VALUE);
        defNumber = new Label("30");

        speed = new Label("SPD");
        spdProgress = new ProgressBar(0.60);
        spdProgress.getStyleClass().add("spd-progress");
        spdProgress.setMaxWidth(Double.MAX_VALUE);
        spdNumber = new Label("60");

        weightLabel = new Label("Weight");
        heightLabel = new Label("Height");

        weightValue = new Label("6.5 kg");
        heightValue = new Label("50 cm");

        weight = new VBox(weightValue, weightLabel);
        weight.setAlignment(Pos.CENTER);

        height = new VBox(heightValue, heightLabel);
        height.setAlignment(Pos.CENTER);

        weightValue.getStyleClass().add("weight-value");
        heightValue.getStyleClass().add("height-value");
        weightLabel.getStyleClass().add("weight-label");
        heightLabel.getStyleClass().add("height-label");

        weight.getStyleClass().add("measurement-box");
        height.getStyleClass().add("measurement-box");

        measurements = new HBox(16, weight, height);
        measurements.setAlignment(Pos.CENTER);

        statsBarHP = new HBox(10, hp, hpProgress, hpNumber);
        statsBarHP.setAlignment(Pos.CENTER);
        statsBarHP.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(hpProgress, Priority.ALWAYS);

        statsBarATK = new HBox(10, attack, atkProgress, atkNumber);
        statsBarATK.setAlignment(Pos.CENTER);
        statsBarATK.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(atkProgress, Priority.ALWAYS);

        statsBarDEF = new HBox(10, defense, defProgress, defNumber);
        statsBarDEF.setAlignment(Pos.CENTER);
        statsBarDEF.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(defProgress, Priority.ALWAYS);

        statsBarSPD = new HBox(10, speed, spdProgress, spdNumber);
        statsBarSPD.setAlignment(Pos.CENTER);
        statsBarSPD.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(spdProgress, Priority.ALWAYS);

        stats = new VBox(10, statsBarHP, statsBarATK, statsBarDEF, statsBarSPD);
        stats.setAlignment(Pos.CENTER);
        stats.setMaxWidth(Double.MAX_VALUE);

        recherche = new HBox(16, champNomPokemon, catchButton);
        recherche.setMaxWidth(Double.MAX_VALUE);

        card = new VBox(
                10,
                nomPokemon,
                imagePokemon,
                idPokemon,
                types,
                measurements,
                statistiquesTitre,
                stats,
                inventoryToggle
        );
        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(Double.MAX_VALUE);

        card.getStyleClass().add("card");
        statsBarHP.getStyleClass().add("stats-bar-hp");
        measurements.getStyleClass().add("measurements");
        recherche.getStyleClass().add("recherche");

        Label inventaireTitre = new Label("Pokémon attrapés");
        inventaireTitre.getStyleClass().add("inventaire-titre");

        listePokemon = new VBox(8);
        listePokemon.setAlignment(Pos.TOP_LEFT);

        inventairePanel = new VBox(12, inventaireTitre, listePokemon);
        inventairePanel.setMinWidth(LARGEUR_MIN_INVENTAIRE);
        inventairePanel.setPrefWidth(LARGEUR_PREF_INVENTAIRE);
        inventairePanel.setMaxWidth(LARGEUR_MAX_INVENTAIRE);
        inventaireTitre.setPadding(new Insets(16));
        inventairePanel.getStyleClass().add("inventaire-panel");
        inventairePanel.setAlignment(Pos.TOP_CENTER);

        VBox contenuPrincipal = new VBox(recherche, card, messageErreur);
        contenuPrincipal.setMinWidth(LARGEUR_MIN_CONTENU_PRINCIPAL);
        contenuPrincipal.setMaxWidth(Double.MAX_VALUE);
        contenuPrincipal.setPadding(new Insets(24));

        layoutPrincipal = new HBox(contenuPrincipal, inventairePanel);
        layoutPrincipal.setMaxWidth(Double.MAX_VALUE);
        layoutPrincipal.setMaxHeight(Double.MAX_VALUE);

        HBox.setHgrow(contenuPrincipal, Priority.ALWAYS);
        HBox.setHgrow(inventairePanel, Priority.NEVER);

        inventairePanel.setVisible(false);
        inventairePanel.setManaged(false);

        inventoryToggle.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                resizeWindowForInventory(true);

                Platform.runLater(() -> {
                    inventairePanel.setVisible(true);
                    inventairePanel.setManaged(true);
                });
            } else {
                inventairePanel.setVisible(false);
                inventairePanel.setManaged(false);
                resizeWindowForInventory(false);
            }
        });

        racine = layoutPrincipal;
        racine.getStyleClass().add("racine");
    }

    public void setInventaireItems(
            List<Pokemon> pokemons,
            Consumer<Pokemon> onVoir,
            Consumer<Pokemon> onSupprimer
    ) {
        listePokemon.getChildren().clear();

        if (pokemons == null || pokemons.isEmpty()) {
            listePokemon.getChildren().add(new Label("Aucun Pokémon capturé"));
            return;
        }

        for (Pokemon pokemon : pokemons) {
            Label nom = new Label(capitaliser(pokemon.name) + " #" + pokemon.id);

            Button voir = new Button("Voir");
            Button supprimer = new Button("Suppr.");

            Region espace = new Region();
            HBox.setHgrow(espace, Priority.ALWAYS);

            HBox ligne = new HBox(8, nom, espace, voir, supprimer);
            ligne.setAlignment(Pos.CENTER_LEFT);

            voir.setOnAction(e -> onVoir.accept(pokemon));
            supprimer.setOnAction(e -> onSupprimer.accept(pokemon));

            listePokemon.getChildren().add(ligne);
        }
    }

    public Parent getRoot() {
        return racine;
    }

    private String capitaliser(String texte) {
        if (texte == null || texte.isBlank()) {
            return "";
        }

        return texte.substring(0, 1).toUpperCase() + texte.substring(1);
    }

    private void resizeWindowForInventory(boolean inventoryVisible) {
        Stage stage = (Stage) racine.getScene().getWindow();

        double changementLargeur;

        if (inventoryVisible) {
            changementLargeur = LARGEUR_PREF_INVENTAIRE;
        } else {
            changementLargeur = -LARGEUR_PREF_INVENTAIRE;
        }

        stage.setWidth(stage.getWidth() + changementLargeur);
    }
}

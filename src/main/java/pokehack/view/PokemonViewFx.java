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
import javafx.scene.control.ScrollPane;

import pokehack.modele.Pokemon;
import javafx.animation.PauseTransition;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class PokemonViewFx {

    public final Label nomPokemon, idPokemon, typePokemon, typePokemon2;
    public final Label hp, attack, defense, speed;
    public final Label statistiquesTitre, messageErreur;
    public final Label weightValue, heightValue;
    public final TextField champNomPokemon;
    public final ImageView imagePokemon;
    public final ImageView primaryTypeIcon;
    public final ImageView primaryTypeName;
    public final ImageView secondaryTypeIcon;
    public final ImageView secondaryTypeName;
    public final ImageView gifCapture;
    public final Button catchButton;
    public final ToggleButton inventoryToggle;
    public final Button randomButton;

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
    private final HBox typesico;

    private static final double LARGEUR_MIN_CONTENU_PRINCIPAL = 350;
    private static final double LARGEUR_MIN_INVENTAIRE = 300;
    private static final double LARGEUR_PREF_INVENTAIRE = 320;
    private static final double LARGEUR_MAX_INVENTAIRE = 340;
    private static final double HAUTEUR = 750;

    public PokemonViewFx() {

        imagePokemon = new ImageView(new Image(
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
            true
        ));
        imagePokemon.setFitWidth(200);
        imagePokemon.setPreserveRatio(true);
        imagePokemon.setSmooth(false);

        // ----- Input nom pokemon pour recherche -----
        champNomPokemon = new TextField();
        champNomPokemon.setPromptText("Entrez le nom ou l'ID du Pokémon");
        HBox.setHgrow(champNomPokemon, Priority.ALWAYS);
        champNomPokemon.getStyleClass().add("champ-nom-pokemon");

        // ----- Button attraper -----
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

        // ----- Animation GIF pokeball de capture ------
        Image gif = new Image(
            Objects.requireNonNull(
                getClass().getResourceAsStream("/images/capture.gif"),
                "GIF introuvable : /images/capture.gif")
            );
        gifCapture = new ImageView(gif);
        gifCapture.setFitWidth(200);
        gifCapture.setFitHeight(200);
        gifCapture.setPreserveRatio(true);
        gifCapture.setVisible(false);
        gifCapture.setManaged(false);
        gifCapture.setMouseTransparent(true);

        StackPane animationPokemon = new StackPane(
                imagePokemon,
                gifCapture
        );
        animationPokemon.setAlignment(Pos.CENTER);

        // ----- Inventaire -----
        inventoryToggle = new ToggleButton("Montrer l'inventaire");
        inventoryToggle.getStyleClass().add("toggle-inventaire");
        VBox.setMargin(inventoryToggle, new Insets(12, 0, 0, 0));

        inventoryToggle.setPrefWidth(165);
        inventoryToggle.setMinWidth(165);
        inventoryToggle.setAlignment(Pos.CENTER);

        // ----- Message -----
        messageErreur = new Label();
        messageErreur.getStyleClass().add("message-erreur");

        // ----- Statistique -----
        statistiquesTitre = new Label("Statistiques");
        statistiquesTitre.getStyleClass().add("statistiques-titre");

        // ----- Type du pokemon -----
        typePokemon = new Label("Plante");
        typePokemon.getStyleClass().add("type-pokemon");

        typePokemon2 = new Label("Poison");
        typePokemon2.getStyleClass().add("type-pokemon2");

        types = new HBox(10, typePokemon, typePokemon2);
        types.getStyleClass().add("types");
        types.setAlignment(Pos.CENTER);

        // ----- Informations du pokemon -----
        nomPokemon = new Label("Bulbizarre");
        nomPokemon.getStyleClass().add("nom-pokemon");

        idPokemon = new Label("#1");
        idPokemon.getStyleClass().add("id-pokemon");

        hp = new Label("HP");
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

        weightValue = new Label("6.5 kg");
        heightValue = new Label("50 cm");

        weight = new VBox(weightValue);
        weight.setAlignment(Pos.CENTER);

        height = new VBox(heightValue);
        height.setAlignment(Pos.CENTER);

        weightValue.getStyleClass().add("weight-value");
        heightValue.getStyleClass().add("height-value");

        weight.getStyleClass().add("measurement-box");
        height.getStyleClass().add("measurement-box");

        measurements = new HBox(16, weight, height);
        measurements.setAlignment(Pos.CENTER);

        // ----- Statistiques du pokemon -----

        // ------ 1) aligner les barres de progrès ----

        hp.setPrefWidth(40);
        attack.setPrefWidth(40);
        defense.setPrefWidth(40);
        speed.setPrefWidth(40);

        hpNumber.setPrefWidth(35);
        atkNumber.setPrefWidth(35);
        defNumber.setPrefWidth(35);
        spdNumber.setPrefWidth(35);

        hp.setAlignment(Pos.CENTER_RIGHT);
        attack.setAlignment(Pos.CENTER_RIGHT);
        defense.setAlignment(Pos.CENTER_RIGHT);
        speed.setAlignment(Pos.CENTER_RIGHT);

        hpNumber.setAlignment(Pos.CENTER_LEFT);
        atkNumber.setAlignment(Pos.CENTER_LEFT);
        defNumber.setAlignment(Pos.CENTER_LEFT);
        spdNumber.setAlignment(Pos.CENTER_LEFT);

        // ------2) Création des HBox pour chaque stat -----

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


        // ---- Bouton random ----

        randomButton = new Button();
        randomButton.getStyleClass().add("random-button");

        ImageView diceIcon = new ImageView(
                Objects.requireNonNull(
                        getClass().getResource("/images/dice.png"),
                        "Image introuvable : /images/dice.png"
                ).toExternalForm()
        );

        randomButton.setGraphic(diceIcon);

        diceIcon.setFitWidth(25);
        diceIcon.setFitHeight(25);
        diceIcon.setPreserveRatio(true);

        champNomPokemon.setMaxWidth(Double.MAX_VALUE);
        champNomPokemon.setPadding(new Insets(12, 48, 12, 16));

        randomButton.setMinSize(36, 36);
        randomButton.setPrefSize(36, 36);
        randomButton.setMaxSize(36, 36);
        randomButton.setFocusTraversable(false);

        StackPane champAvecRandom = new StackPane(champNomPokemon, randomButton);
        StackPane.setAlignment(randomButton, Pos.CENTER_RIGHT);
        StackPane.setMargin(randomButton, new Insets(0, 6, 0, 0));

        HBox.setHgrow(champAvecRandom, Priority.ALWAYS);

        // ------ Input rechercher -----
        recherche = new HBox(16, champAvecRandom, catchButton);
        recherche.setMaxWidth(Double.MAX_VALUE);

        // ----- Card principal -----
        card = new VBox(
                10,
                nomPokemon,
                idPokemon,
                animationPokemon,
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

        // ------ INVENTAIRE -------
        // ----- Types -----
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

        typesico = new HBox(10, primaryTypeIcon, primaryTypeName, secondaryTypeIcon, secondaryTypeName);
        typesico.getStyleClass().add("types");
        typesico.setAlignment(Pos.CENTER);

        // ----- Image pokemon -----
        ImageView imageInventaire = new ImageView(
                Objects.requireNonNull(
                        getClass().getResource("/images/pokemon.png"),
                        "Image introuvable : /images/pokemon.png"
                ).toExternalForm()
        );
        imageInventaire.setFitWidth(120);
        imageInventaire.setFitHeight(120);
        imageInventaire.setPreserveRatio(true);
        imageInventaire.setSmooth(true);

        // ----- Titre -----
        Label inventaireTitre = new Label("Mes Pokémon");
        inventaireTitre.getStyleClass().add("inventaire-titre");

        // ----- Liste -----
        listePokemon = new VBox(8);
        listePokemon.setAlignment(Pos.TOP_LEFT);

        ScrollPane scrollInventaire = new ScrollPane(listePokemon);
        scrollInventaire.setFitToWidth(true);
        scrollInventaire.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollInventaire.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollInventaire.setPannable(true);
        scrollInventaire.getStyleClass().add("scroll-inventaire");

        VBox.setVgrow(scrollInventaire, Priority.ALWAYS);

        // ----- Panel inventaire -----
        inventairePanel = new VBox(
                12,
                imageInventaire,
                inventaireTitre,
                scrollInventaire
        );
        inventairePanel.setMinWidth(LARGEUR_MIN_INVENTAIRE);
        inventairePanel.setPrefWidth(LARGEUR_PREF_INVENTAIRE);
        inventairePanel.setMaxWidth(LARGEUR_MAX_INVENTAIRE);
        inventaireTitre.setPadding(new Insets(16));
        inventairePanel.getStyleClass().add("inventaire-panel");
        inventairePanel.setAlignment(Pos.TOP_CENTER);

        // ----- Contenu Principal -----

        Region espaceVide = new Region();
        VBox.setVgrow(espaceVide, Priority.ALWAYS);


        VBox contenuPrincipal = new VBox(recherche, card, espaceVide, messageErreur);
        contenuPrincipal.setMinWidth(LARGEUR_MIN_CONTENU_PRINCIPAL);
        contenuPrincipal.setMaxWidth(Double.MAX_VALUE);
        contenuPrincipal.setPadding(new Insets(24, 24, 0, 24));
        contenuPrincipal.setMaxHeight(Double.MAX_VALUE);

        // ----- Layout Principal -----
        layoutPrincipal = new HBox(contenuPrincipal, inventairePanel);
        layoutPrincipal.setMaxWidth(Double.MAX_VALUE);
        layoutPrincipal.setMinHeight(HAUTEUR);
        layoutPrincipal.setMaxHeight(Double.MAX_VALUE);

        HBox.setHgrow(contenuPrincipal, Priority.ALWAYS);
        HBox.setHgrow(inventairePanel, Priority.NEVER);

        messageErreur.setMaxWidth(Double.MAX_VALUE);
        messageErreur.setAlignment(Pos.CENTER);


        // ----- Contenu Inventaire -----
        inventairePanel.setVisible(false);
        inventairePanel.setManaged(false);

        inventoryToggle.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                inventoryToggle.setText("Fermer l'inventaire");
                resizeWindowForInventory(true);

                Platform.runLater(() -> {
                    inventairePanel.setVisible(true);
                    inventairePanel.setManaged(true);
                });
            } else {
                inventoryToggle.setText("Montrer l'inventaire");
                inventairePanel.setVisible(false);
                inventairePanel.setManaged(false);
                resizeWindowForInventory(false);
            }
        });

        racine = layoutPrincipal;
        racine.getStyleClass().add("racine");
    }

    // ----- Liste inventaire -----
    public void setInventaireItems(
            List<Pokemon> pokemons,
            Consumer<Pokemon> onVoir,
            Consumer<Pokemon> onSupprimer
    ) {
        listePokemon.getChildren().clear();

        if (pokemons == null || pokemons.isEmpty()) {
            Label vide = new Label("Aucun Pokémon capturé");
            vide.getStyleClass().add("inventaire-vide");

            listePokemon.getChildren().add(vide);
            return;
        }

        for (Pokemon pokemon : pokemons) {
            // ----- Nom du Pokémon -----
            Label nom = new Label(
                    capitaliser(pokemon.name) + " #" + pokemon.id
            );
            nom.getStyleClass().add("pokemon-inventaire-nom");
            // ----- Clic sur le nom pour voir les détails -----
            nom.setOnMouseClicked(event -> onVoir.accept(pokemon));
            nom.setCursor(javafx.scene.Cursor.HAND);

            // ----- Icônes propres à chaque Pokémon -----
            ImageView iconeTypePrincipal = creerIconeType(
                    pokemon.primary_type_icon
            );
            ImageView iconeTypeSecondaire = creerIconeType(
                    pokemon.secondary_type_icon
            );

            HBox typesInventaire = new HBox(
                    2,
                    iconeTypePrincipal,
                    iconeTypeSecondaire
            );

            typesInventaire.setAlignment(Pos.CENTER_LEFT);
            typesInventaire.getStyleClass().add("types-inventaire");

            // ----- Icone poubelle - DELETE -----
            ImageView iconePoubelle = new ImageView(
                    Objects.requireNonNull(
                            getClass().getResource("/images/trash.png"),
                            "Image introuvable : /images/trash.png"
                    ).toExternalForm()
            );
            iconePoubelle.setFitWidth(18);
            iconePoubelle.setFitHeight(18);
            iconePoubelle.setPreserveRatio(true);

            // ----- Bouton supprimer transparent pour fond -----
            Button supprimer = new Button();
            supprimer.setMinSize(30, 30);
            supprimer.setPrefSize(30, 30);
            supprimer.setMaxSize(30, 30);
            supprimer.setGraphic(iconePoubelle);
            supprimer.getStyleClass().add("bouton-supprimer");
            supprimer.setOnAction(event -> {
                event.consume();
                onSupprimer.accept(pokemon);
            });

            // ----- Espacement dans la ligne -----
            Region espace = new Region();
            HBox.setHgrow(espace, Priority.ALWAYS);

            HBox ligne = new HBox(
                    8,
                    typesInventaire,
                    nom,
                    espace,
                    supprimer
            );

            ligne.setAlignment(Pos.CENTER_LEFT);
            ligne.setMaxWidth(Double.MAX_VALUE);
            ligne.getStyleClass().add("ligne-inventaire");

            listePokemon.getChildren().add(ligne);
        }
    }

    // ----- Icone type dans la liste -----
    private ImageView creerIconeType(String url) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(24);
        imageView.setFitHeight(24);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        if (url != null && !url.isBlank()) {
            imageView.setImage(new Image(url, true));
            imageView.setVisible(true);
            imageView.setManaged(true);
        }

        return imageView;
    }

    // ----- Racine de la vue -----
    public Parent getRoot() {
        return racine;
    }

    // ----- Méthode utilitaire pour capitaliser le nom du Pokémon -----
    private String capitaliser(String texte) {
        if (texte == null || texte.isBlank()) {
            return "";
        }

        return texte.substring(0, 1).toUpperCase() + texte.substring(1);
    }

    // ----- Redimensionner la fenêtre lors de l'affichage ou la fermeture de l'inventaire -----
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

    // ----- Animation durant la capture -----
    public void demarrerAnimationCapture() {
        gifCapture.setManaged(true);
        gifCapture.setVisible(true);
    }

    public void arreterAnimationCapture() {
        gifCapture.setVisible(false);
        gifCapture.setManaged(false);
    }

    public void masquerInformationsPokemon() {
        nomPokemon.setVisible(false);
        nomPokemon.setManaged(false);
        imagePokemon.setVisible(false);
        imagePokemon.setManaged(false);
        idPokemon.setVisible(false);
        idPokemon.setManaged(false);
        types.setVisible(false);
        types.setManaged(false);
        measurements.setVisible(false);
        measurements.setManaged(false);
        statistiquesTitre.setVisible(false);
        statistiquesTitre.setManaged(false);
        stats.setVisible(false);
        stats.setManaged(false);
    }

    public void afficherInformationsPokemon() {
        nomPokemon.setVisible(true);
        nomPokemon.setManaged(true);
        imagePokemon.setVisible(true);
        imagePokemon.setManaged(true);
        idPokemon.setVisible(true);
        idPokemon.setManaged(true);
        types.setVisible(true);
        types.setManaged(true);
        measurements.setVisible(true);
        measurements.setManaged(true);
        statistiquesTitre.setVisible(true);
        statistiquesTitre.setManaged(true);
        stats.setVisible(true);
        stats.setManaged(true);
    }
}

package pokehack.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import pokehack.modele.Pokemon;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import static pokehack.utils.TextUtils.capitaliser;

public class PokemonViewFx {

    private static final double LARGEUR_MIN_CONTENU_PRINCIPAL = 350;
    private static final double LARGEUR_MIN_INVENTAIRE = 300;
    private static final double LARGEUR_PREF_INVENTAIRE = 320;
    private static final double LARGEUR_MAX_INVENTAIRE = 340;
    private static final double HAUTEUR = 750;

    public final Label nomPokemon;
    public final Label idPokemon;
    public final Label typePokemon;
    public final Label typePokemon2;
    public final Label messageErreur;
    public final Label statistiquesTitre;
    public final Label weightValue;
    public final Label heightValue;

    public final Label hp;
    public final Label attack;
    public final Label defense;
    public final Label speed;

    public final Label hpNumber;
    public final Label atkNumber;
    public final Label defNumber;
    public final Label spdNumber;

    public final ProgressBar hpProgress;
    public final ProgressBar atkProgress;
    public final ProgressBar defProgress;
    public final ProgressBar spdProgress;

    public final TextField champNomPokemon;
    public final ImageView imagePokemon;
    public final ImageView gifCapture;
    public final Button catchButton;
    public final ToggleButton inventoryToggle;

    private final HBox racine;
    private final HBox measurements;
    private final VBox stats;
    private final HBox types;
    private final VBox inventairePanel;
    private final VBox listePokemon;

    public PokemonViewFx() {
        imagePokemon = creerImagePokemon();
        champNomPokemon = creerChampRecherche();
        catchButton = creerBoutonCapture();
        gifCapture = creerAnimationCapture();

        StackPane animationPokemon = new StackPane(imagePokemon, gifCapture);
        animationPokemon.setAlignment(Pos.CENTER);

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

        types = new HBox(10, typePokemon, typePokemon2);
        types.getStyleClass().add("types");
        types.setAlignment(Pos.CENTER);

        nomPokemon = new Label("Bulbizarre");
        nomPokemon.getStyleClass().add("nom-pokemon");

        idPokemon = new Label("#1");
        idPokemon.getStyleClass().add("id-pokemon");

        hp = new Label("HP");
        attack = new Label("ATK");
        defense = new Label("DEF");
        speed = new Label("SPD");

        hpProgress = creerProgressBar("hp-progress", 0.50);
        atkProgress = creerProgressBar("atk-progress", 0.40);
        defProgress = creerProgressBar("def-progress", 0.30);
        spdProgress = creerProgressBar("spd-progress", 0.60);

        hpNumber = new Label("50");
        atkNumber = new Label("40");
        defNumber = new Label("30");
        spdNumber = new Label("60");

        weightValue = new Label("6.5 kg");
        heightValue = new Label("50 cm");

        VBox weight = creerMesure(weightValue);
        VBox height = creerMesure(heightValue);

        measurements = new HBox(16, weight, height);
        measurements.setAlignment(Pos.CENTER);
        measurements.getStyleClass().add("measurements");

        HBox statsBarHP = creerLigneStatistique(hp, hpProgress, hpNumber);
        HBox statsBarATK = creerLigneStatistique(attack, atkProgress, atkNumber);
        HBox statsBarDEF = creerLigneStatistique(defense, defProgress, defNumber);
        HBox statsBarSPD = creerLigneStatistique(speed, spdProgress, spdNumber);

        statsBarHP.getStyleClass().add("stats-bar-hp");

        stats = new VBox(
                10,
                statsBarHP,
                statsBarATK,
                statsBarDEF,
                statsBarSPD
        );
        stats.setAlignment(Pos.CENTER);
        stats.setMaxWidth(Double.MAX_VALUE);

        HBox recherche = new HBox(16, champNomPokemon, catchButton);
        recherche.setMaxWidth(Double.MAX_VALUE);
        recherche.getStyleClass().add("recherche");

        VBox card = new VBox(
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

        listePokemon = new VBox(8);
        listePokemon.setAlignment(Pos.TOP_LEFT);

        ScrollPane scrollInventaire = new ScrollPane(listePokemon);
        scrollInventaire.setFitToWidth(true);
        scrollInventaire.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollInventaire.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollInventaire.setPannable(true);
        scrollInventaire.getStyleClass().add("scroll-inventaire");

        VBox.setVgrow(scrollInventaire, Priority.ALWAYS);

        ImageView imageInventaire = creerImageInventaire();

        Label inventaireTitre = new Label("Mes Pokémon");
        inventaireTitre.getStyleClass().add("inventaire-titre");
        inventaireTitre.setPadding(new Insets(16));

        inventairePanel = new VBox(
                12,
                imageInventaire,
                inventaireTitre,
                scrollInventaire
        );
        inventairePanel.setMinWidth(LARGEUR_MIN_INVENTAIRE);
        inventairePanel.setPrefWidth(LARGEUR_PREF_INVENTAIRE);
        inventairePanel.setMaxWidth(LARGEUR_MAX_INVENTAIRE);
        inventairePanel.getStyleClass().add("inventaire-panel");
        inventairePanel.setAlignment(Pos.TOP_CENTER);
        inventairePanel.setVisible(false);
        inventairePanel.setManaged(false);

        VBox contenuPrincipal = new VBox(recherche, card, messageErreur);
        contenuPrincipal.setMinWidth(LARGEUR_MIN_CONTENU_PRINCIPAL);
        contenuPrincipal.setMaxWidth(Double.MAX_VALUE);
        contenuPrincipal.setPadding(new Insets(24));

        HBox layoutPrincipal = new HBox(contenuPrincipal, inventairePanel);
        layoutPrincipal.setMaxWidth(Double.MAX_VALUE);
        layoutPrincipal.setMinHeight(HAUTEUR);
        layoutPrincipal.setMaxHeight(Double.MAX_VALUE);

        HBox.setHgrow(contenuPrincipal, Priority.ALWAYS);
        HBox.setHgrow(inventairePanel, Priority.NEVER);

        racine = layoutPrincipal;
        racine.getStyleClass().add("racine");
    }

    private ImageView creerImagePokemon() {
        ImageView imageView = new ImageView(
                new Image(
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
                        true
                )
        );

        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(false);

        return imageView;
    }

    private TextField creerChampRecherche() {
        TextField champ = new TextField();
        champ.setPromptText("Entrez le nom ou l'ID du Pokémon");
        champ.getStyleClass().add("champ-nom-pokemon");
        HBox.setHgrow(champ, Priority.ALWAYS);

        return champ;
    }

    private Button creerBoutonCapture() {
        Button bouton = new Button("Attraper");

        ImageView pokeballIcon = new ImageView(
                Objects.requireNonNull(
                        getClass().getResource("/images/pokeball.png"),
                        "Image introuvable : /images/pokeball.png"
                ).toExternalForm()
        );

        pokeballIcon.setFitWidth(24);
        pokeballIcon.setFitHeight(24);
        pokeballIcon.setPreserveRatio(true);

        bouton.setGraphic(pokeballIcon);
        bouton.setGraphicTextGap(8);
        bouton.getStyleClass().add("bouton-catch");

        return bouton;
    }

    private ImageView creerAnimationCapture() {
        Image gif = new Image(
                Objects.requireNonNull(
                        getClass().getResourceAsStream("/images/capture.gif"),
                        "GIF introuvable : /images/capture.gif"
                )
        );

        ImageView animation = new ImageView(gif);
        animation.setFitWidth(200);
        animation.setFitHeight(200);
        animation.setPreserveRatio(true);
        animation.setVisible(false);
        animation.setManaged(false);
        animation.setMouseTransparent(true);

        return animation;
    }

    private ProgressBar creerProgressBar(String classeCss, double progression) {
        ProgressBar progressBar = new ProgressBar(progression);
        progressBar.getStyleClass().add(classeCss);
        progressBar.setMaxWidth(Double.MAX_VALUE);

        return progressBar;
    }

    private VBox creerMesure(Label valeur) {
        valeur.getStyleClass().add(
                valeur == weightValue ? "weight-value" : "height-value"
        );

        VBox conteneur = new VBox(valeur);
        conteneur.setAlignment(Pos.CENTER);
        conteneur.getStyleClass().add("measurement-box");

        return conteneur;
    }

    private HBox creerLigneStatistique(
            Label titre,
            ProgressBar progressBar,
            Label valeur
    ) {
        HBox ligne = new HBox(10, titre, progressBar, valeur);
        ligne.setAlignment(Pos.CENTER);
        ligne.setMaxWidth(Double.MAX_VALUE);

        HBox.setHgrow(progressBar, Priority.ALWAYS);

        return ligne;
    }

    private ImageView creerImageInventaire() {
        ImageView imageView = new ImageView(
                Objects.requireNonNull(
                        getClass().getResource("/images/pokemon.png"),
                        "Image introuvable : /images/pokemon.png"
                ).toExternalForm()
        );

        imageView.setFitWidth(120);
        imageView.setFitHeight(120);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        return imageView;
    }

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
            listePokemon.getChildren().add(
                    creerLigneInventaire(pokemon, onVoir, onSupprimer)
            );
        }
    }

    private HBox creerLigneInventaire(
            Pokemon pokemon,
            Consumer<Pokemon> onVoir,
            Consumer<Pokemon> onSupprimer
    ) {
        Label nom = new Label(
                capitaliser(pokemon.name) + " #" + pokemon.id
        );
        nom.getStyleClass().add("pokemon-inventaire-nom");
        nom.setCursor(javafx.scene.Cursor.HAND);
        nom.setOnMouseClicked(event -> onVoir.accept(pokemon));

        ImageView iconeTypePrincipal =
                creerIconeType(pokemon.primary_type_icon);

        ImageView iconeTypeSecondaire =
                creerIconeType(pokemon.secondary_type_icon);

        HBox typesInventaire = new HBox(
                2,
                iconeTypePrincipal,
                iconeTypeSecondaire
        );
        typesInventaire.setAlignment(Pos.CENTER_LEFT);
        typesInventaire.getStyleClass().add("types-inventaire");

        Button supprimer = creerBoutonSupprimer(pokemon, onSupprimer);

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

        return ligne;
    }

    private Button creerBoutonSupprimer(
            Pokemon pokemon,
            Consumer<Pokemon> onSupprimer
    ) {
        ImageView iconePoubelle = new ImageView(
                Objects.requireNonNull(
                        getClass().getResource("/images/trash.png"),
                        "Image introuvable : /images/trash.png"
                ).toExternalForm()
        );

        iconePoubelle.setFitWidth(18);
        iconePoubelle.setFitHeight(18);
        iconePoubelle.setPreserveRatio(true);

        Button bouton = new Button();
        bouton.setMinSize(30, 30);
        bouton.setPrefSize(30, 30);
        bouton.setMaxSize(30, 30);
        bouton.setGraphic(iconePoubelle);
        bouton.getStyleClass().add("bouton-supprimer");

        bouton.setOnAction(event -> {
            event.consume();
            onSupprimer.accept(pokemon);
        });

        return bouton;
    }

    private ImageView creerIconeType(String url) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(24);
        imageView.setFitHeight(24);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        boolean urlValide = url != null && !url.isBlank();

        imageView.setVisible(urlValide);
        imageView.setManaged(urlValide);

        if (urlValide) {
            imageView.setImage(new Image(url, true));
        }

        return imageView;
    }

    public Parent getRoot() {
        return racine;
    }

    public void afficherInventaire(boolean visible) {
        inventairePanel.setVisible(visible);
        inventairePanel.setManaged(visible);
    }

    public void demarrerAnimationCapture() {
        gifCapture.setManaged(true);
        gifCapture.setVisible(true);
    }

    public void arreterAnimationCapture() {
        gifCapture.setVisible(false);
        gifCapture.setManaged(false);
    }

    public void masquerInformationsPokemon() {
        modifierVisibiliteInformations(false);
    }

    public void afficherInformationsPokemon() {
        modifierVisibiliteInformations(true);
    }

    private void modifierVisibiliteInformations(boolean visible) {
        nomPokemon.setVisible(visible);
        nomPokemon.setManaged(visible);

        imagePokemon.setVisible(visible);
        imagePokemon.setManaged(visible);

        idPokemon.setVisible(visible);
        idPokemon.setManaged(visible);

        types.setVisible(visible);
        types.setManaged(visible);

        measurements.setVisible(visible);
        measurements.setManaged(visible);

        statistiquesTitre.setVisible(visible);
        statistiquesTitre.setManaged(visible);

        stats.setVisible(visible);
        stats.setManaged(visible);
    }

}

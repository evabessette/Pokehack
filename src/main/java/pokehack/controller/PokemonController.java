package pokehack.controller;

import javafx.animation.PauseTransition;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;

import pokehack.modele.Pokemon;
import pokehack.modele.PokemonDAO;
import pokehack.service.PokemonApiService;
import pokehack.view.PokemonViewFx;
import static pokehack.utils.TextUtils.capitaliser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class PokemonController {

    private static final double LARGEUR_INVENTAIRE = 320;

    private final PokemonViewFx vue;
    private final PokemonApiService apiService;
    private final PokemonDAO pokemonDAO;

    private Pokemon pokemonActuel;

    public PokemonController(PokemonViewFx vue) {
        this.vue = vue;
        this.apiService = new PokemonApiService();
        this.pokemonDAO = new PokemonDAO();

        initialiserEvenements();
        chargerInventaire();
        chargerPokemonDepart();
    }

    private void initialiserEvenements() {
        vue.catchButton.setOnAction(event -> capturerPokemon());

        vue.inventoryToggle.selectedProperty().addListener(
                (observable, ancienEtat, inventaireVisible) ->
                        gererAffichageInventaire(inventaireVisible)
        );

        vue.imagePokemon.setOnMouseClicked(
                event -> jouerCriPokemon(pokemonActuel)
        );

        vue.imagePokemon.setCursor(Cursor.HAND);
    }

    private void capturerPokemon() {
        String recherche = vue.champNomPokemon.getText().trim();

        if (recherche.isEmpty()) {
            vue.messageErreur.setText(
                    "Entre un nom ou un ID de Pokémon."
            );
            return;
        }

        preparerCapture();

        Task<Pokemon> captureTask = creerTacheCapture(recherche);

        captureTask.setOnSucceeded(
                event -> gererCaptureReussie(captureTask.getValue())
        );

        captureTask.setOnFailed(
                event -> gererEchecCapture(captureTask.getException())
        );

        Thread thread = new Thread(
                captureTask,
                "capture-pokemon"
        );

        thread.setDaemon(true);
        thread.start();
    }

    private void preparerCapture() {
        vue.masquerInformationsPokemon();
        vue.demarrerAnimationCapture();
        vue.catchButton.setDisable(true);
        vue.messageErreur.setText("Capture en cours...");
    }

    private Task<Pokemon> creerTacheCapture(String recherche) {
        return new Task<>() {
            @Override
            protected Pokemon call() throws Exception {
                Pokemon pokemon =
                        apiService.rechercherPokemon(recherche);

                pokemonDAO.capturer(pokemon);

                return pokemon;
            }
        };
    }

    private void gererCaptureReussie(Pokemon pokemon) {
        vue.messageErreur.setText("Pokémon capturé...");
        vue.champNomPokemon.clear();

        PauseTransition delai =
                new PauseTransition(Duration.seconds(2));

        delai.setOnFinished(event -> {
            mettreAJourVue(pokemon);
            vue.arreterAnimationCapture();
            vue.afficherInformationsPokemon();
            chargerInventaire();

            vue.messageErreur.setText(
                    "Sauvegarde réussie : "
                            + capitaliser(pokemon.name)
            );

            vue.catchButton.setDisable(false);
        });

        delai.play();
    }

    private void gererEchecCapture(Throwable erreur) {
        vue.arreterAnimationCapture();
        vue.afficherInformationsPokemon();
        vue.catchButton.setDisable(false);

        String message =
                erreur != null && erreur.getMessage() != null
                        ? erreur.getMessage()
                        : "Erreur pendant la capture.";

        vue.messageErreur.setText(message);
    }

    private void gererAffichageInventaire(
            boolean inventaireVisible
    ) {
        vue.afficherInventaire(inventaireVisible);

        vue.inventoryToggle.setText(
                inventaireVisible
                        ? "Masquer l'inventaire"
                        : "Montrer l'inventaire"
        );

        redimensionnerFenetre(inventaireVisible);
    }

    private void redimensionnerFenetre(
            boolean inventaireVisible
    ) {
        if (vue.getRoot().getScene() == null) {
            return;
        }

        Stage stage = (Stage) vue.getRoot()
                .getScene()
                .getWindow();

        double changementLargeur =
                inventaireVisible
                        ? LARGEUR_INVENTAIRE
                        : -LARGEUR_INVENTAIRE;

        stage.setWidth(
                stage.getWidth() + changementLargeur
        );
    }

    private void chargerInventaire() {
        try {
            ArrayList<Pokemon> pokemons =
                    pokemonDAO.afficherTous();

            vue.setInventaireItems(
                    pokemons,
                    this::afficherPokemonDepuisInventaire,
                    this::supprimerPokemon
            );
        } catch (SQLException exception) {
            vue.messageErreur.setText(
                    "Erreur BD inventaire : "
                            + exception.getMessage()
            );
        }
    }

    private void afficherPokemonDepuisInventaire(
            Pokemon pokemonInventaire
    ) {
        try {
            Pokemon pokemonComplet =
                    apiService.rechercherPokemon(
                            pokemonInventaire.id
                    );

            mettreAJourVue(pokemonComplet);
            vue.messageErreur.setText("");
        } catch (Exception exception) {
            mettreAJourVue(pokemonInventaire);

            vue.messageErreur.setText(
                    "Affichage depuis la BD, "
                            + "icônes possiblement absentes."
            );
        }
    }

    private void supprimerPokemon(Pokemon pokemon) {
        ButtonType supprimer = new ButtonType(
                "Supprimer",
                ButtonBar.ButtonData.OK_DONE
        );

        ButtonType annuler = new ButtonType(
                "Annuler",
                ButtonBar.ButtonData.CANCEL_CLOSE
        );

        Alert confirmation = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Veux-tu vraiment supprimer "
                        + capitaliser(pokemon.name)
                        + " ?",
                supprimer,
                annuler
        );

        configurerAlerteSuppression(
                confirmation,
                pokemon
        );

        confirmation.showAndWait().ifPresent(reponse -> {
            if (reponse == supprimer) {
                supprimerPokemonConfirme(pokemon);
            }
        });
    }

    private void configurerAlerteSuppression(
            Alert confirmation,
            Pokemon pokemon
    ) {
        confirmation.setTitle(
                "Confirmer la suppression"
        );

        confirmation.setHeaderText(
                "Suppression de "
                        + capitaliser(pokemon.name)
        );

        ImageView image = new ImageView(
                new Image(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream(
                                        "/images/trash.png"
                                ),
                                "Image introuvable : /images/trash.png"
                        )
                )
        );

        image.setFitWidth(48);
        image.setFitHeight(48);
        image.setPreserveRatio(true);

        confirmation.setGraphic(image);

        Stage fenetreAlert =
                (Stage) confirmation
                        .getDialogPane()
                        .getScene()
                        .getWindow();

        fenetreAlert.getIcons().add(
                new Image(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream(
                                        "/images/pokeball.png"
                                ),
                                "Icône introuvable : /images/pokeball.png"
                        )
                )
        );

        confirmation.getDialogPane()
                .getStylesheets()
                .add(
                        Objects.requireNonNull(
                                getClass().getResource("/style.css"),
                                "Feuille de style introuvable : /style.css"
                        ).toExternalForm()
                );

        confirmation.getDialogPane()
                .getStyleClass()
                .add("confirmation-suppression");
    }

    private void supprimerPokemonConfirme(
            Pokemon pokemon
    ) {
        try {
            pokemonDAO.supprimerParId(pokemon.id);
            chargerInventaire();

            vue.messageErreur.setText(
                    "Pokémon supprimé : "
                            + capitaliser(pokemon.name)
            );
        } catch (SQLException exception) {
            vue.messageErreur.setText(
                    "Erreur suppression : "
                            + exception.getMessage()
            );
        }
    }

    private void mettreAJourVue(Pokemon pokemon) {
        pokemonActuel = pokemon;

        vue.nomPokemon.setText(
                capitaliser(pokemon.name)
        );

        vue.idPokemon.setText(
                "#" + pokemon.id
        );

        if (pokemon.image_url != null
                && !pokemon.image_url.isBlank()) {
            vue.imagePokemon.setImage(
                    new Image(pokemon.image_url, true)
            );
        }

        mettreAJourTypes(pokemon);
        mettreAJourMesures(pokemon);
        mettreAJourStatistiques(pokemon);
    }

    private void mettreAJourTypes(Pokemon pokemon) {
        vue.typePokemon.setText(
                capitaliser(pokemon.primary_type)
        );

        appliquerCouleurType(
                vue.typePokemon,
                pokemon.primary_type
        );

        boolean aTypeSecondaire =
                pokemon.secondary_type != null
                        && !pokemon.secondary_type.isBlank();

        vue.typePokemon2.setVisible(aTypeSecondaire);
        vue.typePokemon2.setManaged(aTypeSecondaire);

        if (aTypeSecondaire) {
            vue.typePokemon2.setText(
                    capitaliser(pokemon.secondary_type)
            );

            appliquerCouleurType(
                    vue.typePokemon2,
                    pokemon.secondary_type
            );
        } else {
            vue.typePokemon2.setText("");
        }
    }

    private void mettreAJourMesures(Pokemon pokemon) {
        vue.weightValue.setText(
                String.format(
                        "%.1f kg",
                        pokemon.weight / 10.0
                )
        );

        vue.heightValue.setText(
                String.format(
                        "%.0f cm",
                        pokemon.height * 10.0
                )
        );
    }

    private void mettreAJourStatistiques(
            Pokemon pokemon
    ) {
        mettreAJourStatistique(
                vue.hpNumber,
                vue.hpProgress,
                pokemon.hp
        );

        mettreAJourStatistique(
                vue.atkNumber,
                vue.atkProgress,
                pokemon.attack
        );

        mettreAJourStatistique(
                vue.defNumber,
                vue.defProgress,
                pokemon.defense
        );

        mettreAJourStatistique(
                vue.spdNumber,
                vue.spdProgress,
                pokemon.speed
        );
    }

    private void mettreAJourStatistique(
            Label valeurLabel,
            javafx.scene.control.ProgressBar progressBar,
            int valeur
    ) {
        valeurLabel.setText(String.valueOf(valeur));

        progressBar.setProgress(
                Math.min(1.0, valeur / 255.0)
        );
    }

    private void chargerPokemonDepart() {
        try {
            ArrayList<Pokemon> pokemons =
                    pokemonDAO.afficherTous();

            Pokemon pokemon;

            if (!pokemons.isEmpty()) {
                pokemon = apiService.rechercherPokemon(
                        pokemons.get(0).id
                );
            } else {
                pokemon = apiService.rechercherPokemon("1");
            }

            mettreAJourVue(pokemon);
            vue.messageErreur.setText("");
        } catch (Exception exception) {
            vue.messageErreur.setText(
                    "Impossible de charger le Pokémon de départ : "
                            + exception.getMessage()
            );
        }
    }

    private void appliquerCouleurType(
            Label label,
            String type
    ) {
        label.getStyleClass().removeAll(
                "type-normal",
                "type-feu",
                "type-eau",
                "type-electrik",
                "type-plante",
                "type-glace",
                "type-combat",
                "type-poison",
                "type-sol",
                "type-vol",
                "type-psy",
                "type-insecte",
                "type-roche",
                "type-spectre",
                "type-dragon",
                "type-tenebres",
                "type-acier",
                "type-fee"
        );

        if (!label.getStyleClass()
                .contains("type-pokemon")) {
            label.getStyleClass().add("type-pokemon");
        }

        if (type == null || type.isBlank()) {
            return;
        }

        String classeType = switch (
                type.toLowerCase()
        ) {
            case "normal" -> "type-normal";
            case "fire", "feu" -> "type-feu";
            case "water", "eau" -> "type-eau";
            case "electric", "electrik" -> "type-electrik";
            case "grass", "plante" -> "type-plante";
            case "ice", "glace" -> "type-glace";
            case "fighting", "combat" -> "type-combat";
            case "poison" -> "type-poison";
            case "ground", "sol" -> "type-sol";
            case "flying", "vol" -> "type-vol";
            case "psychic", "psy" -> "type-psy";
            case "bug", "insecte" -> "type-insecte";
            case "rock", "roche" -> "type-roche";
            case "ghost", "spectre" -> "type-spectre";
            case "dragon" -> "type-dragon";
            case "dark", "tenebres" -> "type-tenebres";
            case "steel", "acier" -> "type-acier";
            case "fairy", "fee" -> "type-fee";
            default -> "type-normal";
        };

        label.getStyleClass().add(classeType);
    }

    private void jouerCriPokemon(Pokemon pokemon) {
        if (pokemon == null
                || pokemon.cries == null
                || pokemon.cries.isBlank()) {
            return;
        }

        try {
            AudioClip cri =
                    new AudioClip(pokemon.cries);

            cri.setVolume(0.7);
            cri.play();
        } catch (Exception exception) {
            vue.messageErreur.setText(
                    "Impossible de jouer le cri : "
                            + exception.getMessage()
            );
        }
    }

}

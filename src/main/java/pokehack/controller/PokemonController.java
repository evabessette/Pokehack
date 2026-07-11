package pokehack.controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javafx.concurrent.Task;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.util.Objects;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;

import pokehack.modele.Pokemon;
import pokehack.modele.PokemonDAO;
import pokehack.service.PokemonApiService;
import pokehack.view.PokemonViewFx;

public class PokemonController {

    private final PokemonViewFx vue;
    private final PokemonApiService apiService;
    private final PokemonDAO pokemonDAO;
    private Pokemon pokemonActuel;

    // Controlleur pour gérer les interactions entre la vue et le modèle
    public PokemonController(PokemonViewFx vue) {
        this.vue = vue;
        this.apiService = new PokemonApiService();
        this.pokemonDAO = new PokemonDAO();

        vue.catchButton.setOnAction(e -> {
            String recherche = vue.champNomPokemon.getText().trim();
            if (recherche.isEmpty()) {
                vue.messageErreur.setText("Entre un nom ou un ID de Pokémon.");
                return;
            }
            vue.masquerInformationsPokemon();
            vue.demarrerAnimationCapture();
            vue.catchButton.setDisable(true);
            vue.messageErreur.setText("Capture en cours...");

            // Tache capture Pokémon
            Task<Pokemon> captureTask = new Task<>() {
                @Override
                protected Pokemon call() throws Exception {
                    Pokemon pokemon = apiService.rechercherPokemon(recherche);
                    pokemonDAO.capturer(pokemon);
                    return pokemon;
                }
            };

            // Tache réussi
            captureTask.setOnSucceeded(event -> {
                Pokemon pokemon = captureTask.getValue();
                vue.messageErreur.setText("Pokémon capturé...");
                PauseTransition delai = new PauseTransition(Duration.seconds(2));
                delai.setOnFinished(fin -> {
                    mettreAJourVue(pokemon);
                    vue.arreterAnimationCapture();
                    vue.afficherInformationsPokemon();
                    chargerInventaire();
                    vue.messageErreur.setText(
                            "Sauvegarde réussie : " + capitaliser(pokemon.name)
                    );
                    vue.catchButton.setDisable(false);
                });
                vue.champNomPokemon.clear();
                delai.play();
            });

            // Tache échoué
            captureTask.setOnFailed(event -> {
                Throwable erreur = captureTask.getException();
                vue.arreterAnimationCapture();
                vue.afficherInformationsPokemon();
                vue.messageErreur.setText(
                        erreur != null && erreur.getMessage() != null
                                ? erreur.getMessage()
                                : "Erreur pendant la capture."
                );
                vue.catchButton.setDisable(false);
            });

            Thread thread = new Thread(captureTask, "capture-pokemon");
            thread.setDaemon(true);
            thread.start();
        });

        // Logique pour bouton random
        vue.randomButton.setOnAction(e -> {
            int idAleatoire = new Random().nextInt(1000) + 1;
            String recherche = String.valueOf(idAleatoire);

            vue.champNomPokemon.setText(recherche);
            vue.messageErreur.setText("Chargement du Pokémon aléatoire...");

            try {
                Pokemon pokemon = apiService.rechercherPokemon(recherche);

                mettreAJourVue(pokemon);
                vue.messageErreur.setText("");
            } catch (Exception ex) {
                vue.messageErreur.setText("Erreur avec le Pokémon aléatoire : " + ex.getMessage());
            }
        });

        chargerInventaire();
        chargerPokemonDepart();
    }

    // Charger l'inventaire des Pokémon depuis la base de données
    private void chargerInventaire() {
        try {
            ArrayList<Pokemon> pokemons = pokemonDAO.afficherTous();
            vue.setInventaireItems(pokemons, this::afficherPokemonDepuisInventaire, this::supprimerPokemon);
        } catch (SQLException e) {
            vue.messageErreur.setText("Erreur BD inventaire : " + e.getMessage());
        }
    }

    // Afficher le Pokémon depuis l'inventaire
    private void afficherPokemonDepuisInventaire(Pokemon pokemonInventaire) {
        try {
            Pokemon pokemonComplet = apiService.rechercherPokemon(pokemonInventaire.id);
            mettreAJourVue(pokemonComplet);
            vue.messageErreur.setText("");
        } catch (Exception e) {
            mettreAJourVue(pokemonInventaire);
            vue.messageErreur.setText("Affichage depuis la BD, icônes possiblement absentes.");
        }
    }

    // Supprimer un pokemon
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
                        + capitaliser(pokemon.name) + " ?",
                supprimer,
                annuler
        );

        confirmation.setTitle("Confirmer la suppression");
        confirmation.setHeaderText(
                "Suppression de " + capitaliser(pokemon.name)
        );

        Stage fenetreAlert = (Stage) confirmation.getDialogPane()
                .getScene()
                .getWindow();

        ImageView image = new ImageView(
                new Image(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream("/images/trash.png")
                        )
                )
        );

        image.setFitWidth(48);
        image.setFitHeight(48);
        image.setPreserveRatio(true);

        confirmation.setGraphic(image);
        fenetreAlert.getIcons().add(
                new Image(
                        Objects.requireNonNull(
                                getClass().getResourceAsStream("/images/pokeball.png"),
                                "Icône introuvable"
                        )
                )
        );

        confirmation.getDialogPane().getStylesheets().add(
                Objects.requireNonNull(
                        getClass().getResource("/style.css")
                ).toExternalForm()
        );

        confirmation.getDialogPane()
                .getStyleClass()
                .add("confirmation-suppression");

        confirmation.showAndWait().ifPresent(reponse -> {
            if (reponse == supprimer) {
                supprimerPokemonConfirme(pokemon);
            }
        });
    }

    // Confirmer la supression
    private void supprimerPokemonConfirme(Pokemon pokemon) {
        try {
            pokemonDAO.supprimerParId(pokemon.id);

            chargerInventaire();

            vue.messageErreur.setText(
                    "Pokémon supprimé : " + capitaliser(pokemon.name)
            );
        } catch (SQLException e) {
            vue.messageErreur.setText(
                    "Erreur suppression : " + e.getMessage()
            );
        }
    }

    // Mettre à jour la vue avec les informations du Pokémon
    private void mettreAJourVue(Pokemon pokemon) {
        this.pokemonActuel = pokemon;
        vue.nomPokemon.setText(capitaliser(pokemon.name));
        vue.idPokemon.setText("#" + pokemon.id);

        if (pokemon.image_url != null && !pokemon.image_url.isBlank()) {
            vue.imagePokemon.setImage(new Image(pokemon.image_url, true));
        }
        // ----- Jouer le son en cliquant -----
        vue.imagePokemon.setOnMouseClicked(event -> {
            jouerCriPokemon(pokemonActuel);
        });

        vue.imagePokemon.setCursor(javafx.scene.Cursor.HAND);

        boolean aTypeSecondaire = pokemon.secondary_type != null && !pokemon.secondary_type.isBlank();

        vue.typePokemon2.setVisible(aTypeSecondaire);
        vue.typePokemon2.setManaged(aTypeSecondaire);
        vue.typePokemon.setText(capitaliser(pokemon.primary_type));
        appliquerCouleurType(vue.typePokemon, pokemon.primary_type);

        if (aTypeSecondaire) {
            vue.typePokemon2.setText(capitaliser(pokemon.secondary_type));
            appliquerCouleurType(vue.typePokemon2, pokemon.secondary_type);
        } else {
            vue.typePokemon2.setText("");
        }

        vue.weightValue.setText(String.format("%.1f kg", pokemon.weight / 10.0));
        vue.heightValue.setText(String.format("%.0f cm", pokemon.height * 10.0));

        vue.hpNumber.setText(String.valueOf(pokemon.hp));
        vue.hpProgress.setProgress(Math.min(1.0, pokemon.hp / 255.0));

        vue.atkNumber.setText(String.valueOf(pokemon.attack));
        vue.atkProgress.setProgress(Math.min(1.0, pokemon.attack / 255.0));

        vue.defNumber.setText(String.valueOf(pokemon.defense));
        vue.defProgress.setProgress(Math.min(1.0, pokemon.defense / 255.0));

        vue.spdNumber.setText(String.valueOf(pokemon.speed));
        vue.spdProgress.setProgress(Math.min(1.0, pokemon.speed / 255.0));
    }

    // Charger le pokemon de départ depuis la base de données
    private void chargerPokemonDepart() {
        try {
            ArrayList<Pokemon> pokemons = pokemonDAO.afficherTous();

            Pokemon pokemon;

            if (!pokemons.isEmpty()) {
                pokemon = apiService.rechercherPokemon(pokemons.get(0).id);
            } else {
                pokemon = apiService.rechercherPokemon("1");
            }

            mettreAJourVue(pokemon);
            vue.messageErreur.setText("");

        } catch (Exception ex) {
            vue.messageErreur.setText("Impossible de charger le Pokémon de départ : " + ex.getMessage());
        }
    }

    //  Capitaliser le texte
    private String capitaliser(String texte) {
        if (texte == null || texte.isBlank()) {
            return "";
        }

        return texte.substring(0, 1).toUpperCase() + texte.substring(1);
    }

    // Appliquer les couleurs aux types
    private void appliquerCouleurType(Label label, String type) {
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

        if (!label.getStyleClass().contains("type-pokemon")) {
            label.getStyleClass().add("type-pokemon");
        }

        if (type == null || type.isBlank()) {
            return;
        }

        String classeType = switch (type.toLowerCase()) {
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

    // Jouer le crie du Pokémon
    private void jouerCriPokemon(Pokemon pokemon) {
        if (pokemon == null || pokemon.cries == null || pokemon.cries.isBlank()) {
            return;
        }

        try {
            AudioClip cri = new AudioClip(pokemon.cries);
            cri.setVolume(0.7);
            cri.play();
        } catch (Exception e) {
            vue.messageErreur.setText(
                    "Impossible de jouer le cri : " + e.getMessage()
            );
        }
    }
}
package pokehack.controller;

import javafx.scene.image.Image;
import pokehack.modele.Pokemon;
import pokehack.modele.PokemonDAO;
import pokehack.service.PokemonApiService;
import pokehack.view.PokemonViewFx;

import java.sql.SQLException;
import java.util.ArrayList;

public class PokemonController {

    private final PokemonViewFx vue;
    private final PokemonApiService apiService;
    private final PokemonDAO pokemonDAO;

    public PokemonController(PokemonViewFx vue) {
        this.vue = vue;
        this.apiService = new PokemonApiService();
        this.pokemonDAO = new PokemonDAO();

        vue.catchButton.setOnAction(e -> {
            try {
                Pokemon pokemon = apiService.rechercherPokemon(vue.champNomPokemon.getText());

                pokemonDAO.capturer(pokemon);

                mettreAJourVue(pokemon);
                chargerInventaire();

                vue.messageErreur.setText("Sauvegarde réussie : " + capitaliser(pokemon.name));
            } catch (Exception ex) {
                vue.messageErreur.setText(ex.getMessage());
            }
        });

        chargerInventaire();
        chargerPokemonDepart();
    }

    private void chargerInventaire() {
        try {
            ArrayList<Pokemon> pokemons = pokemonDAO.afficherTous();
            vue.setInventaireItems(pokemons, this::afficherPokemonDepuisInventaire, this::supprimerPokemon);
        } catch (SQLException e) {
            vue.messageErreur.setText("Erreur BD inventaire : " + e.getMessage());
        }
    }

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

    private void supprimerPokemon(Pokemon pokemon) {
        try {
            pokemonDAO.supprimerParId(pokemon.id);
            chargerInventaire();
            vue.messageErreur.setText("Pokémon supprimé : " + capitaliser(pokemon.name));
        } catch (SQLException e) {
            vue.messageErreur.setText("Erreur suppression : " + e.getMessage());
        }
    }

    private void mettreAJourVue(Pokemon pokemon) {
        vue.nomPokemon.setText(capitaliser(pokemon.name));
        vue.idPokemon.setText("#" + pokemon.id);

        if (pokemon.image_url != null && !pokemon.image_url.isBlank()) {
            vue.imagePokemon.setImage(new Image(pokemon.image_url, true));
        }

        vue.typePokemon.setText(capitaliser(pokemon.primary_type));

        boolean aTypeSecondaire = pokemon.secondary_type != null && !pokemon.secondary_type.isBlank();

        vue.typePokemon2.setVisible(aTypeSecondaire);
        vue.typePokemon2.setManaged(aTypeSecondaire);

        if (aTypeSecondaire) {
            vue.typePokemon2.setText(capitaliser(pokemon.secondary_type));
        } else {
            vue.typePokemon2.setText("");
        }

        if (pokemon.primary_type_icon != null && !pokemon.primary_type_icon.isBlank()) {
            vue.primaryTypeIcon.setImage(new Image(pokemon.primary_type_icon, true));
            vue.primaryTypeIcon.setVisible(true);
            vue.primaryTypeIcon.setManaged(true);
        } else {
            vue.primaryTypeIcon.setImage(null);
            vue.primaryTypeIcon.setVisible(false);
            vue.primaryTypeIcon.setManaged(false);
        }

        if (pokemon.secondary_type_icon != null && !pokemon.secondary_type_icon.isBlank()) {
            vue.secondaryTypeIcon.setImage(new Image(pokemon.secondary_type_icon, true));
            vue.secondaryTypeIcon.setVisible(true);
            vue.secondaryTypeIcon.setManaged(true);
        } else {
            vue.secondaryTypeIcon.setImage(null);
            vue.secondaryTypeIcon.setVisible(false);
            vue.secondaryTypeIcon.setManaged(false);
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

    private String capitaliser(String texte) {
        if (texte == null || texte.isBlank()) {
            return "";
        }

        return texte.substring(0, 1).toUpperCase() + texte.substring(1);
    }
}

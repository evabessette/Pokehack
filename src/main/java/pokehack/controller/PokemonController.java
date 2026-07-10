package pokehack.controller;

import javafx.scene.image.Image;
import pokehack.modele.Pokemon;
import pokehack.view.PokemonViewFx;

public class PokemonController {

    private final PokemonViewFx vue;

    public PokemonController(PokemonViewFx vue) {
        this.vue = vue;
        vue.catchButton.setOnAction(e -> afficherPokemon(fairePikachu()));
    }

    private void afficherPokemon(Pokemon p) {
        vue.nomPokemon.setText(p.name);
        vue.imagePokemon.setImage(new Image(p.image_url, true));
        vue.idPokemon.setText("#" + p.id);

        vue.weightValue.setText(p.weight + " kg");
        vue.heightValue.setText(p.height + " m");

        vue.hpNumber.setText(String.valueOf(p.hp));
        vue.hpProgress.setProgress(p.hp / 255.0);

        vue.atkNumber.setText(String.valueOf(p.attack));
        vue.atkProgress.setProgress(p.attack / 255.0);

        vue.defNumber.setText(String.valueOf(p.defense));
        vue.defProgress.setProgress(p.defense / 255.0);

        vue.spdNumber.setText(String.valueOf(p.speed));
        vue.spdProgress.setProgress(p.speed / 255.0);

        afficherTypes(p);
    }

    private void afficherTypes(Pokemon p) {
        vue.typePokemon.setText(p.primary_type.toUpperCase());

        boolean deuxTypes = p.secondary_type != null && !p.secondary_type.isBlank();
        vue.typePokemon2.setVisible(deuxTypes);
        vue.typePokemon2.setManaged(deuxTypes);

        if (deuxTypes) {
            vue.typePokemon2.setText(p.secondary_type.toUpperCase());
        }
    }

    private Pokemon fairePikachu() {
        Pokemon p = new Pokemon();
        p.id = "25";
        p.name = "pikachu";
        p.primary_type = "electric";
        p.secondary_type = null;
        p.hp = 35;
        p.attack = 55;
        p.defense = 40;
        p.speed = 90;
        p.weight = 6.0;
        p.height = 0.4;
        p.image_url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png";
        return p;
    }
}

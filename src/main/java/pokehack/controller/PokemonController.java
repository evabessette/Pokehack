package pokehack.controller;

import pokehack.view.PokemonViewFx;

public class PokemonController {

    private final PokemonViewFx vue;

    public PokemonController(PokemonViewFx vue) {
        this.vue = vue;

        vue.catchButton.setOnAction(e -> {
            vue.nomPokemon.setText("Pikachu");
        });
    }
}
package pokehack.controller;

import javafx.scene.image.Image;
import pokehack.view.PokemonViewFx;

public class PokemonController {

    private final PokemonViewFx vue;

    public PokemonController(PokemonViewFx vue) {
        this.vue = vue;

        vue.catchButton.setOnAction(e -> {
            String saisie = vue.champNomPokemon.getText();
            vue.nomPokemon.setText(saisie);

            vue.imagePokemon.setImage(new Image("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png", true));

            vue.idPokemon.setText("#02");

            vue.typePokemon.setText("Electric");

            vue.weightValue.setText("5.5 kg");
            vue.heightValue.setText("100 cm");

            vue.hpNumber.setText("50");
            vue.hpProgress.setProgress(50 / 255.0);
            vue.atkNumber.setText("40");
            vue.atkProgress.setProgress(40 / 255.0);
            vue.defNumber.setText("30");
            vue.defProgress.setProgress(30 / 255.0);
            vue.spdNumber.setText("60");
            vue.spdProgress.setProgress(60 / 255.0);

            vue.typePokemon2.setVisible(false);
            vue.typePokemon2.setVisible(false);


        });

    }
}
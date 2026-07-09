package pokehack;

import pokehack.modele.Pokemon;
import pokehack.service.PokemonApiService;

public class TestApi {

    public static void main(String[] args) {
        PokemonApiService api = new PokemonApiService();

        try {
            Pokemon pokemon = api.rechercherPokemon("pikachu");
            System.out.println("ID: " + pokemon.id);
            System.out.println("Name: " + pokemon.name);
            System.out.println("Type: " + pokemon.primary_type);
            System.out.println("HP: " + pokemon.hp);
            System.out.println("Attack: " + pokemon.attack);
            System.out.println("Defense: " + pokemon.defense);
            System.out.println("Special attack: " + pokemon.special_attack);
            System.out.println("Special defense: " + pokemon.special_defense);
            System.out.println("Speed: " + pokemon.speed);
            System.out.println("Weight: " + pokemon.weight);
            System.out.println("Height: " + pokemon.height);
            System.out.println("Image: " + pokemon.image_url);

        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }
}
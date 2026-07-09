package pokehack.service;

import pokehack.modele.Pokemon;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class PokemonApiService {

    private static final String BASE_URL = "https://pokeapi.co/api/v2/pokemon/";

    private final HttpClient client;
    private final ObjectMapper mapper;

    public PokemonApiService() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        this.mapper = new ObjectMapper();
    }

    public Pokemon rechercherPokemon(String nomOuId) throws Exception {
        if (nomOuId == null || nomOuId.trim().isEmpty()) {
            throw new Exception("Veuillez entrer un nom ou un id de Pokémon.");
        }

        String recherche = nomOuId.trim().toLowerCase();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + recherche))
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        HttpResponse<String> response;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new Exception("Erreur réseau : impossible de contacter la PokéAPI.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new Exception("La recherche a été interrompue.");
        }

        if (response.statusCode() == 404) {
            throw new Exception("Aucun Pokémon trouvé pour : " + nomOuId);
        }

        if (response.statusCode() != 200) {
            throw new Exception("Erreur PokéAPI. Code HTTP : " + response.statusCode());
        }

        JsonNode root = mapper.readTree(response.body());

        Pokemon pokemon = new Pokemon();

        pokemon.id = root.path("id").asText();
        pokemon.name = root.path("name").asText();

        pokemon.image_url = root
                .path("sprites")
                .path("other")
                .path("official-artwork")
                .path("front_default")
                .asText();

        lireTypes(root, pokemon);
        lireStats(root, pokemon);

        pokemon.weight = root.path("weight").asDouble();
        pokemon.height = root.path("height").asDouble();

        return pokemon;
    }

    private void lireTypes(JsonNode root, Pokemon pokemon) {
        JsonNode types = root.path("types");

        pokemon.primary_type = null;
        pokemon.secondary_type = null;

        for (JsonNode typeNode : types) {
            int slot = typeNode.path("slot").asInt();
            String typeName = typeNode
                    .path("type")
                    .path("name")
                    .asText();

            if (slot == 1) {
                pokemon.primary_type = typeName;
            } else if (slot == 2) {
                pokemon.secondary_type = typeName;
            }
        }
    }

    private void lireStats(JsonNode root, Pokemon pokemon) {
        JsonNode stats = root.path("stats");

        for (JsonNode statNode : stats) {
            String statName = statNode
                    .path("stat")
                    .path("name")
                    .asText();

            int baseStat = statNode
                    .path("base_stat")
                    .asInt();

            switch (statName) {
                case "hp":
                    pokemon.hp = baseStat;
                    break;

                case "attack":
                    pokemon.attack = baseStat;
                    break;

                case "defense":
                    pokemon.defense = baseStat;
                    break;

                case "special-attack":
                    pokemon.special_attack = baseStat;
                    break;

                case "special-defense":
                    pokemon.special_defense = baseStat;
                    break;

                case "speed":
                    pokemon.speed = baseStat;
                    break;
            }
        }
    }
}
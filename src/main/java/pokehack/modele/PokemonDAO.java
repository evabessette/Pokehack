package pokehack.modele;

import pokehack.utils.Connexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PokemonDAO {

    public void capturer(Pokemon p) throws SQLException {
        String sql = """
                INSERT INTO pokemons (
                    id,
                    name,
                    image_url,
                    primary_type,
                    secondary_type,
                    primary_type_icon,
                    secondary_type_icon,
                    primary_type_name,
                    secondary_type_name,
                    cries,
                    hp,
                    attack,
                    defense,
                    special_attack,
                    special_defense,
                    speed,
                    weight,
                    height
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT (id) DO UPDATE SET
                    name = EXCLUDED.name,
                    image_url = EXCLUDED.image_url,
                    primary_type = EXCLUDED.primary_type,
                    secondary_type = EXCLUDED.secondary_type,
                    primary_type_icon = EXCLUDED.primary_type_icon,
                    secondary_type_icon = EXCLUDED.secondary_type_icon,
                    primary_type_name = EXCLUDED.primary_type_name,
                    secondary_type_name = EXCLUDED.secondary_type_name,
                    cries = EXCLUDED.cries,
                    hp = EXCLUDED.hp,
                    attack = EXCLUDED.attack,
                    defense = EXCLUDED.defense,
                    special_attack = EXCLUDED.special_attack,
                    special_defense = EXCLUDED.special_defense,
                    speed = EXCLUDED.speed,
                    weight = EXCLUDED.weight,
                    height = EXCLUDED.height
                """;

        try (
                Connection con = Connexion.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, p.id);
            ps.setString(2, p.name);
            ps.setString(3, p.image_url);
            ps.setString(4, p.primary_type);
            ps.setString(5, p.secondary_type);
            ps.setString(6, p.primary_type_icon);
            ps.setString(7, p.secondary_type_icon);
            ps.setString(8, p.primary_type_name);
            ps.setString(9, p.secondary_type_name);
            ps.setString(10, p.cries);
            ps.setInt(11, p.hp);
            ps.setInt(12, p.attack);
            ps.setInt(13, p.defense);
            ps.setInt(14, p.special_attack);
            ps.setInt(15, p.special_defense);
            ps.setInt(16, p.speed);
            ps.setDouble(17, p.weight);
            ps.setDouble(18, p.height);

            ps.executeUpdate();
        }
    }

    public ArrayList<Pokemon> afficherTous() throws SQLException {
        ArrayList<Pokemon> pokemons = new ArrayList<>();

        String sql = """
                SELECT
                    id,
                    name,
                    image_url,
                    primary_type,
                    secondary_type,
                    primary_type_icon,
                    secondary_type_icon,
                    primary_type_name,
                    secondary_type_name,
                    cries,
                    hp,
                    attack,
                    defense,
                    special_attack,
                    special_defense,
                    speed,
                    weight,
                    height,
                    captured_at
                FROM pokemons
                ORDER BY id
                """;

        try (
                Connection con = Connexion.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Pokemon pokemon = new Pokemon();

                pokemon.id = rs.getString("id");
                pokemon.name = rs.getString("name");
                pokemon.image_url = rs.getString("image_url");
                pokemon.primary_type = rs.getString("primary_type");
                pokemon.secondary_type = rs.getString("secondary_type");
                pokemon.primary_type_icon = rs.getString("primary_type_icon");
                pokemon.secondary_type_icon = rs.getString("secondary_type_icon");
                pokemon.primary_type_name = rs.getString("primary_type_name");
                pokemon.secondary_type_name = rs.getString("secondary_type_name");
                pokemon.cries = rs.getString("cries");

                pokemon.hp = rs.getInt("hp");
                pokemon.attack = rs.getInt("attack");
                pokemon.defense = rs.getInt("defense");
                pokemon.special_attack = rs.getInt("special_attack");
                pokemon.special_defense = rs.getInt("special_defense");
                pokemon.speed = rs.getInt("speed");

                pokemon.weight = rs.getDouble("weight");
                pokemon.height = rs.getDouble("height");
                pokemon.captured_at = rs.getString("captured_at");

                pokemons.add(pokemon);
            }
        }

        return pokemons;
    }

    public void supprimerParId(String id) throws SQLException {
        String sql = "DELETE FROM pokemons WHERE id = ?";

        try (
                Connection con = Connexion.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }
}
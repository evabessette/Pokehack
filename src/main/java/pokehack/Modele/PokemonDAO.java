package pokehack.Modele;

import pokehack.Utils.Connexion;

import java.sql.Connection;
import java.sql.SQLException;

public class PokemonDAO {

    public void capture(Pokemon p) throws SQLException {
        String sql = "INSERT INTO pokemons"
        + "(id,name,primary_type,secondary_type,hp,attack,defense,speed)"
        + "values (?,?,?,?,?,?,?,?)"
        + "ON CONFLICT (id) DO UPDATE SET "
        + "name = EXCLUDED.name";

        try (Connection con = Connexion.getConnection();
        java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, p.id);
        ps.setString(2, p.name);
        ps.setString(3, p.primary_type);
        ps.setString(4, p.secondary_type);
        ps.setInt(5, p.hp);
        ps.setInt(6, p.attack);
        ps.setInt(7, p.defense);
        ps.setInt(8, p.speed);
        ps.executeUpdate();
        }
    }
}

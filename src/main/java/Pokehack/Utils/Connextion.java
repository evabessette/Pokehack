package Pokehack.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connextion {
    private static final String URL = "jdbc:postgresql://localhost:5432/pokehack";
    private static final String USER = "postgres";
    private static final String PASSWORD = "ton_mot_de_passe"; // Remplacez par votre mot de passe PostgreSQL

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

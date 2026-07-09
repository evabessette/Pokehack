package pokehack.Utils;

import java.sql.Connection;
import java.sql.SQLException;

public class Connexion {

    private static final String URL = "jdbc:postgresql://localhost:5432/pokehack";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";

    private Connexion() {
    }

    public static Connection getConnection() throws SQLException {
        return java.sql.DriverManager.getConnection(URL, USER, PASSWORD);
    }

}

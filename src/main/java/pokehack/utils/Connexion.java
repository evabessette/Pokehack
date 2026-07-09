package pokehack.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {

    public static Connection getConnection() throws SQLException {
        String url = Config.get("db.url");
        String user = Config.get("db.user");
        String password = Config.get("db.password");

        return DriverManager.getConnection(url, user, password);
    }
}

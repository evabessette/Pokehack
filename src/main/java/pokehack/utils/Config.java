package pokehack.utils;

import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Fichier config.properties introuvable dans src/main/resources");
            }

            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chargement de config.properties", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
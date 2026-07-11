package pokehack.utils;

public final class TextUtils {
    private TextUtils() {}

    public static String capitaliser(String texte) {
        if (texte == null || texte.isBlank()) {
            return "";
        }

        return texte.substring(0, 1).toUpperCase()
                + texte.substring(1);
    }
}

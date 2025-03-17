package fr.pixelmonworld.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.pixelmonworld.domain.News;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

/**
 * Utilitaire concernant le site.
 */
public class SiteUtils {

    // URL du site
    private static String url = "https://nacou.pixelmonworld.fr/launcher/";
    // JSON récupéré depuis le site
    private static JsonObject json;
    // Formateur de date pour les news
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d'T'H:mm:ss'Z'", Locale.FRENCH);

    /**
     * Permet de récupérer un asset depuis le site.
     * @param fichier Le fichier à récupérer.
     * @return L'image récupérée.
     */
    public static BufferedImage getAssetFromSite(String fichier) {
        if (fichier.equals("connexion_panel")) {
            return ResourcesUtils.getBufferedImage("connexion_panel/background.png");
        }
        if (fichier.equals("icon")) {
            return ResourcesUtils.getBufferedImage("other/icon.png");
        }
        return null;
//        JsonObject jsonObject = getJsonFromSite();
//        String imageUrl = jsonObject.get("assets").getAsJsonObject().get(fichier).getAsString();
//        try {
//            return ImageIO.read(new URL(imageUrl));
//        } catch (IOException e) {
//            Launcher.erreurInterne(e);
//        }
//        return null;
    }

    /**
     * Permet de récupérer les rendus ingame stockés sur le site.
     * @return Les rendus récupérés.
     */
    public static ArrayList<BufferedImage> getRendersFromSite() {
        ArrayList<BufferedImage> renders = new ArrayList<>();
        renders.add(ResourcesUtils.getBufferedImage("renders/render_1.png"));
//        JsonObject jsonObject = getJsonFromSite();
//        jsonObject.get("slides").getAsJsonArray().forEach(jsonElement -> {
//            try {
//                renders.add(ImageIO.read(new URL(jsonElement.getAsString())));
//            } catch (IOException e) {
//                Launcher.erreurInterne(e);
//            }
//        });
        return renders;
    }

    /**
     * Permet de récupérer les news stockées sur le site.
     * @return Les news récupérées.
     */
    public static Collection<News> getNewsFromSite() {
        Collection<News> news = new ArrayList<>();
        JsonObject json = getJsonFromSite();
        json.get("news").getAsJsonArray().forEach(jsonElement -> {
            try {
                JsonObject newsObject = jsonElement.getAsJsonObject();
                URL url = new URL(newsObject.get("url").getAsString());
                LocalDateTime date = LocalDateTime.parse(newsObject.get("date_posted").getAsString(), formatter);
                news.add(new News(newsObject.get("title").getAsString(), url, date));
            } catch (MalformedURLException e) {
                Launcher.erreurInterne(e);
            }
        });
        return news;
    }

    /**
     * Permet de récupérer le JSON du site.
     * @return Le JSON récupéré.
     */
    private static JsonObject getJsonFromSite() {
        if (json == null) {
            try (InputStream is = new URL(url + "assets").openStream()) {
                json = JsonParser.parseReader(new InputStreamReader(is)).getAsJsonObject();
                return json;
            } catch (IOException e) {
                Launcher.erreurInterne(e);
            }
        }
        return json;
    }

    /**
     * Permet de récupérer un fichier depuis le site sous le format d'un JsonObject.
     * @param fichier Le nom du fichier à récupérer.
     * @return Le fichier récupéré.
     */
    public static JsonObject getFileFromSiteAsJsonObject(String fichier) {
        JsonObject json = getJsonFromSite();
        JsonArray jsonArray = json.get("files").getAsJsonArray();
        for (JsonElement jsonObject : jsonArray) {
            if (jsonObject.getAsJsonObject().get("name").getAsString().startsWith(fichier)) {
                return jsonObject.getAsJsonObject();
            }
        }
        return null;
    }
}

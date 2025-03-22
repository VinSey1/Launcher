package fr.pixelmonworld.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.pixelmonworld.Prelauncher;
import fr.pixelmonworld.domain.News;

import javax.imageio.ImageIO;
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
     * Permet de récupérer un fichier depuis le site sous le format d'un JsonObject.
     * @param fichier Le nom du fichier à récupérer.
     * @return Le fichier récupéré.
     */
    public static JsonObject getFileFromSiteAsJsonObject(String fichier) throws IOException {
        JsonObject json = getJsonFromSite();
        JsonArray jsonArray = json.get("files").getAsJsonArray();
        for (JsonElement jsonObject : jsonArray) {
            if (jsonObject.getAsJsonObject().get("name").getAsString().startsWith(fichier)) {
                return jsonObject.getAsJsonObject();
            }
        }
        throw new NullPointerException("Impossible de récupérer " + fichier + ".");
    }

    /**
     * Permet de récupérer un asset depuis le site. Si jamais on ne peut pas, le récupère de manière statique dans les ressources.
     * @param fichier Le fichier à récupérer.
     * @return L'image récupérée.
     */
    public static BufferedImage getAssetFromSite(String fichier) {
        try {
            JsonObject jsonObject = getJsonFromSite();
            String imageUrl = jsonObject.get("assets").getAsJsonObject().get(fichier).getAsString();
            return ImageIO.read(new URL(imageUrl));
        } catch (IOException | NullPointerException e) {
            if (fichier.equals("connexion_panel")) {
                return ResourcesUtils.getBufferedImage("default/default_background.png");
            }
            if (fichier.equals("icon")) {
                return ResourcesUtils.getBufferedImage("default/default_icon.png");
            }
        }
        throw new NullPointerException("Impossible de récupérer " + fichier + ".");
    }

    /**
     * Permet de récupérer les rendus ingame stockés sur le site. Si jamais on ne peut pas, les récupères de manière statique dans les ressources.
     * @return Les rendus récupérés.
     */
    public static ArrayList<BufferedImage> getRendersFromSite() {
        ArrayList<BufferedImage> renders = new ArrayList<>();
        try {
            JsonObject jsonObject = getJsonFromSite();
            for (JsonElement jsonElement : jsonObject.get("slides").getAsJsonArray()) {
                renders.add(ImageIO.read(new URL(jsonElement.getAsString())));
            }
        } catch (IOException | NullPointerException e) {
            renders.add(ResourcesUtils.getBufferedImage("default/default_render.png"));
        }
        return renders;
    }

    /**
     * Permet de récupérer les news stockées sur le site.
     * @return Les news récupérées.
     */
    public static Collection<News> getNewsFromSite() {
        Collection<News> news = new ArrayList<>();
        try {
            JsonObject json = getJsonFromSite();
            json.get("news").getAsJsonArray().forEach(jsonElement -> {
                try {
                    JsonObject newsObject = jsonElement.getAsJsonObject();
                    URL url = new URL(newsObject.get("url").getAsString());
                    LocalDateTime date = LocalDateTime.parse(newsObject.get("date_posted").getAsString(), formatter);
                    news.add(new News(newsObject.get("title").getAsString(), url, date));
                } catch (MalformedURLException e) {
                    Prelauncher.erreurInterne(e);
                }
            });
        } catch (IOException e) {
            return new ArrayList<>();
        }
        return news;
    }

    /**
     * Permet de récupérer le JSON du site.
     * @return Le JSON récupéré.
     */
    private static JsonObject getJsonFromSite() throws IOException {
        if (json == null) {
            InputStream is = new URL(url + "assets").openStream();
            json = JsonParser.parseReader(new InputStreamReader(is)).getAsJsonObject();
            return json;
        }
        return json;
    }
}

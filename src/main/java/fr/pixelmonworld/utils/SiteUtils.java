package fr.pixelmonworld.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.pixelmonworld.Launcher;
import fr.pixelmonworld.domain.News;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public class SiteUtils {

    private static String url = "https://nacou.pixelmonworld.fr/launcher/";
    private static JsonObject json;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d'T'H:mm:ss'Z'", Locale.FRENCH);


    // TODO A faire
    public static File getFileFromSite(File fichier) {
//        switch (fichier.getName()) {
//            case "servers.dat":
//                return new File("https://pixelmonworld.fr/launcher/servers.dat");
//        }
//        String fichierName = fichier.getName();
        return fichier;
    }

    public static BufferedImage getAssetFromSite(String fichier) {
        JsonObject jsonObject = getJsonFromSite();
        String imageUrl = jsonObject.get("assets").getAsJsonObject().get(fichier).getAsString();
        try {
            return ImageIO.read(new File(new URL(imageUrl).toURI()));
        } catch (IOException | URISyntaxException e) {
            Launcher.erreurInterne(e);
        }
        return null;
    }

    public static Collection<BufferedImage> getRendersFromSite() {
        Collection<BufferedImage> renders = new ArrayList<>();
        JsonObject jsonObject = getJsonFromSite();
        jsonObject.get("slides").getAsJsonArray().forEach(jsonElement -> {
            try {
                renders.add(ImageIO.read(new URL(jsonElement.getAsString())));
            } catch (IOException e) {
                Launcher.erreurInterne(e);
            }
        });
        return renders;
    }

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

    public static JsonObject getFileFromSiteAsJsonObject(String s) {
        JsonObject json = getJsonFromSite();
        JsonArray jsonArray = json.get("files").getAsJsonArray();
        for (JsonElement jsonObject : jsonArray) {
            if (jsonObject.getAsJsonObject().get("name").getAsString().startsWith(s)) {
                return jsonObject.getAsJsonObject();
            }
        }
        return null;
    }
}

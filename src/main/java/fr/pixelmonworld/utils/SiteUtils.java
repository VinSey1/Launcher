package fr.pixelmonworld.utils;

import fr.pixelmonworld.domain.News;

import java.io.File;
import java.util.Optional;

public class SiteUtils {

    // TODO A faire
    public static File getFileFromSite(File fichier) {
//        switch (fichier.getName()) {
//            case "servers.dat":
//                return new File("https://pixelmonworld.fr/launcher/servers.dat");
//        }
//        String fichierName = fichier.getName();
        return fichier;
    }

    public static Optional<News> getNewsFromSite() {
        Optional<News> news = Optional.of(new News("Titre de la news", "Contenu"));
        return news;
    }
}

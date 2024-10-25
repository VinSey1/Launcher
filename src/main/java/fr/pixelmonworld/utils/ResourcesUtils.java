package fr.pixelmonworld.utils;

import fr.pixelmonworld.Launcher;
import fr.pixelmonworld.MainFrame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Random;

/**
 * Utilitaire concernant les ressources.
 */
public class ResourcesUtils {

    private static int lastRandomImage = Integer.MAX_VALUE;

    /**
     * Permet de récupérer une image depuis le dossier de ressources.
     * @param fichier Le fichier à récupérer.
     * @return Image récupérée depuis les ressources.
     */
    public static Image getImage(String fichier) {
        try {
            return ImageIO.read(getResourceAsStream(fichier));
        } catch (IOException e) {
            Launcher.erreurInterne(e);
            return null;
        }
    }

    /**
     * Permet de récupérer une image depuis le dossier de ressources.
     * @param fichier Le fichier à récupérer.
     * @return BufferedImage récupérée depuis les ressources.
     */
    public static BufferedImage getBufferedImage(String fichier) {
        return (BufferedImage) getImage(fichier);
    }

    /**
     * Permet de récupérer une image aléatoire ingame dans le dossier de ressources.
     * @return BufferedImage récupérée depuis les ressources.
     */
    public static BufferedImage getRandomRenderImage() {
        Random random = new Random();
        int number;
        do {
            number = random.nextInt(14 - 1 + 1) + 1;
        } while (number == lastRandomImage);
        lastRandomImage = number;
//        return getBufferedImage("renders/render_" + lastRandomImage + ".jpg");
        return getBufferedImage("renders/render_test.png");
    }

    /**
     * Permet de récupérer un fichier dans le dossier de ressources.
     * @return Fichier récupéré depuis les ressources.
     */
    public static URL getResource(String file) {
        return MainFrame.getInstance().getClass().getClassLoader().getResource(file);
    }

    /**
     * Permet de récupérer un fichier dans le dossier de ressources.
     * @return Fichier récupéré depuis les ressources.
     */
    public static InputStream getResourceAsStream(String file) {
        try {
            return getResource(file).openStream();
        } catch (IOException e) {
            Launcher.erreurInterne(e);
            return null;
        }
    }
}

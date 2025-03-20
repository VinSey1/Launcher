package fr.pixelmonworld.utils;

import fr.pixelmonworld.MainFrame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
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
        ArrayList<BufferedImage> renders = Launcher.getRenders();
        Random random = new Random();
        int number;
        do {
            number = random.nextInt(renders.size());
        } while (number == lastRandomImage);
        lastRandomImage = number;
        return renders.get(number);
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
        return MainFrame.getInstance().getClass().getClassLoader().getResourceAsStream(file);
    }
}

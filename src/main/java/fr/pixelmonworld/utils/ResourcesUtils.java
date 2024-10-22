package fr.pixelmonworld.utils;

import fr.pixelmonworld.MainFrame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
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
     * @throws IOException Problème lors de la récupération du fichier.
     */
    public static Image getImage(String fichier) throws IOException {
        InputStream inputStream = MainFrame.getInstance().getClass().getClassLoader().getResourceAsStream(fichier);
        return inputStream != null ? ImageIO.read(inputStream ) : null;
    }

    /**
     * Permet de récupérer une image depuis le dossier de ressources.
     * @param fichier Le fichier à récupérer.
     * @return BufferedImage récupérée depuis les ressources.
     * @throws IOException Problème lors de la récupération du fichier.
     */
    public static BufferedImage getBufferedImage(String fichier) throws IOException {
        InputStream inputStream = MainFrame.getInstance().getClass().getClassLoader().getResourceAsStream(fichier);
        return inputStream != null ? ImageIO.read(inputStream ) : null;
    }

    /**
     * Permet de récupérer une image aléatoire ingame dans le dossier de ressources.
     * @return BufferedImage récupérée depuis les ressources.
     * @throws IOException Problème lors de la récupération du fichier.
     */
    public static BufferedImage getRandomRenderImage() throws IOException {
        Random random = new Random();
        int number;
        do {
            number = random.nextInt(14 - 1 + 1) + 1;
        } while (number == lastRandomImage);
        lastRandomImage = number;
        return getBufferedImage("render_" + lastRandomImage + ".jpg");
    }

    /**
     * Permet de récupérer un fichier dans le dossier de ressources.
     * @return Fichier récupéré depuis les ressources.
     * @throws IOException Problème lors de la récupération du fichier.
     */
    public static File getFile(String file) throws URISyntaxException {
        return new File(MainFrame.getInstance().getClass().getClassLoader().getResource(file).toURI());
    }
}

package fr.pixelmonworld.utils;

import fr.pixelmonworld.MainFrame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class ImagesSelector {
    public static Image getImage(String fichier) throws IOException {
        InputStream inputStream = MainFrame.getInstance().getClass().getClassLoader().getResourceAsStream(fichier);
        return inputStream != null ? ImageIO.read(inputStream ) : null;
    }

    public static BufferedImage getBufferedImage(String fichier) throws IOException {
        InputStream inputStream = MainFrame.getInstance().getClass().getClassLoader().getResourceAsStream(fichier);
        return inputStream != null ? ImageIO.read(inputStream ) : null;
    }

    public static BufferedImage getRandomRenderImage() throws IOException {
        Random random = new Random();
        int number = random.nextInt(14 - 1 + 1) + 1;
        return getBufferedImage("render_" + number + ".jpg");
    }
}

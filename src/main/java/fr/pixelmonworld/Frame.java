package fr.pixelmonworld;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.util.WindowMover;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Frame extends JFrame {

    private static Frame instance;
    private static File ramFile = new File(String.valueOf(Launcher.getPath()), "ram.txt");
    private static File saverFile = new File(String.valueOf(Launcher.getPath()), "user.stock");
    private static Saver saver = new Saver(saverFile);

    private Panel panel;

    public Frame() throws IOException {
        instance = this;

        this.setTitle("Launcher PixelmonWorld");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1920, 1290);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setIconImage(getImage("icon.png"));

        this.setContentPane(panel = new Panel());

        WindowMover mover = new WindowMover(this);
        this.addMouseListener(mover);
        this.addMouseMotionListener(mover);

        this.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        Launcher.getCrashFile().mkdirs();

        if (!ramFile.exists()) {
            ramFile.createNewFile();
        }

        if (!saverFile.exists()) {
            saverFile.createNewFile();
        }

        instance = new Frame();
    }

    public static Image getImage(String fichier) throws IOException {
        InputStream inputStream = Frame.getInstance().getClass().getClassLoader().getResourceAsStream(fichier);
        return inputStream != null ? ImageIO.read(inputStream ) : null;
    }

    public static BufferedImage getBufferedImage(String fichier) throws IOException {
        InputStream inputStream = Frame.getInstance().getClass().getClassLoader().getResourceAsStream(fichier);
        return inputStream != null ? ImageIO.read(inputStream ) : null;
    }

    public static Frame getInstance() {
        return instance;
    }

    public Panel getPanel() {
        return panel;
    }

    public static File getRamFile() {
        return ramFile;
    }

    public static Saver getSaver() {
        return saver;
    }
}

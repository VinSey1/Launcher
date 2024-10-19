package fr.pixelmonworld;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.util.WindowMover;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static fr.pixelmonworld.utils.ImagesSelector.getImage;

public class MainFrame extends JFrame {

    private static MainFrame instance;
    private static File ramFile = new File(String.valueOf(Launcher.getPath()), "ram.txt");
    private static File saverFile = new File(String.valueOf(Launcher.getPath()), "user.stock");
    private static File serversFile =  new File(String.valueOf(Launcher.getPath()), "servers.dat");
    private static Saver saver = new Saver(saverFile);

    private LauncherPanel launcherPanel;

    public MainFrame() throws IOException {
        instance = this;

        this.setTitle("Launcher PixelmonWorld");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1920, 1290);
        this.setUndecorated(true);
        this.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
        this.setLocationRelativeTo(null);
        this.setIconImage(getImage("icon.png"));

        this.setContentPane(launcherPanel = new LauncherPanel());

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

        instance = new MainFrame();
    }

    public static MainFrame getInstance() {
        return instance;
    }

    public static File getRamFile() {
        return ramFile;
    }

    public static Saver getSaver() {
        return saver;
    }
}

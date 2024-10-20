package fr.pixelmonworld;

import fr.pixelmonworld.panels.main.MainPanel;
import fr.pixelmonworld.utils.FileUtils;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.util.WindowMover;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static fr.pixelmonworld.utils.ImagesSelector.getImage;

public class MainFrame extends JFrame {

    private static MainFrame instance;
    private static File ramFile = new File(String.valueOf(Launcher.getPath()), "ram.txt");
    private static File saverFile = new File(String.valueOf(Launcher.getPath()), "user.stock");
    private static File serversFile =  new File(String.valueOf(Launcher.getPath()), "servers.dat");
    private static File resourcepackFile =  new File(String.valueOf(Launcher.getPath()), "\\resourcepacks\\PixelmonWorld.zip");
    private static Saver saver = new Saver(saverFile);

    public MainFrame() throws IOException, URISyntaxException {
        instance = this;

        File serversFileFromResource = new File(getInstance().getClass().getClassLoader().getResource("servers.dat").toURI());
        FileUtils.copyFile(serversFileFromResource, serversFile);

        if (!resourcepackFile.exists()) {
            File resourcePackFromResource = new File(getInstance().getClass().getClassLoader().getResource("resourcepack.zip").toURI());
            FileUtils.copyFile(resourcePackFromResource, resourcepackFile);
        }

        this.setTitle("Launcher PixelmonWorld");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1920, 1290);
        this.setUndecorated(true);
        this.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
        this.setLocationRelativeTo(null);
        this.setIconImage(getImage("icon.png"));

        this.setContentPane(new MainPanel(this.getWidth(), this.getHeight()));

        WindowMover mover = new WindowMover(this);
        this.addMouseListener(mover);
        this.addMouseMotionListener(mover);

        this.setVisible(true);
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
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

    public static Saver getSaver() {
        return saver;
    }
}

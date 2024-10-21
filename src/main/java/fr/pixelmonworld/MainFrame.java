package fr.pixelmonworld;

import fr.pixelmonworld.panels.main.MainPanel;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.util.WindowMover;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static fr.pixelmonworld.utils.ResourcesUtils.getImage;

/**
 * Coeur de l'application graphique.
 */
public class MainFrame extends JFrame {

    // Instance de l'application (singleton)
    private static MainFrame instance;
    // Fichier de sauvegarde des options (ram + token d'authentification Microsoft)
    private static File saverFile = new File(String.valueOf(Launcher.getPath()), "user.stock");
    // Objet permettant de sauvegarder les options dans le fichier
    private static Saver saver = new Saver(saverFile);

    /**
     * Constructeur par défaut.
     * @throws IOException Problème lors d'une mise à jour graphique.
     */
    public MainFrame() throws IOException {
        instance = this;

        // Définit les éléments basique de l'application (taille, titre, icône...)
        this.setTitle("Launcher PixelmonWorld");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1920, 1290);
        this.setUndecorated(true);
        this.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
        this.setLocationRelativeTo(null);
        this.setIconImage(getImage("icon.png"));

        // Permet d'ajouter le panneau principal à l'application
        this.setContentPane(new MainPanel(this.getWidth(), this.getHeight()));

        // Permet à l'utilisateur de pouvoir bouger la fenêtre
        WindowMover mover = new WindowMover(this);
        this.addMouseListener(mover);
        this.addMouseMotionListener(mover);

        // Affiche l'application
        this.setVisible(true);
    }

    /**
     * Classe de lancement de l'application.
     * @param args Arguments de l'application.
     * @throws IOException Problème lors d'une mise à jour graphique.
     * @throws URISyntaxException Problème lors de la récupération d'un fichier.
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        // Permet de créer le dossier %APPDATA%/.PixelmonWorld/ s'il n'existe pas
        Launcher.getCrashFile().mkdirs();
        Launcher.initDiscord();

        // Permet de créer le fichier de sauvegarde s'il n'existe pas
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

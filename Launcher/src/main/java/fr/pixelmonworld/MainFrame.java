package fr.pixelmonworld;

import fr.pixelmonworld.launcher.LauncherPanel;
import fr.pixelmonworld.utils.Launcher;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.util.WindowMover;
import org.pushingpixels.radiance.animation.api.Timeline;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Coeur de l'application graphique.
 */
public class MainFrame extends JFrame {

    // Instance de l'application (singleton)
    private static MainFrame instance;

    // Fichier de sauvegarde des options (ram + token d'authentification Microsoft)
    private static File saverFile = new File(String.valueOf(Launcher.getPath()), "user.stock");

    // Objet permettant de sauvegarder les options dans le fichier comme les news ou le token d'authentification Microsoft
    private static Saver saver = new Saver(saverFile);

    /**
     * Classe de lancement de l'application.
     * @param args Arguments de l'application.
     */
    public static void main(String[] args) {
        try {
            Launcher.init();
            instance = new MainFrame();
        } catch (Exception e) {
            Launcher.erreurInterne(e);
        }
    }

    /**
     * Constructeur par défaut.
     */
    public MainFrame() {
        instance = this;

        // Définit les éléments basique de l'application (taille, titre, icône...)
        this.setTitle("Launcher PixelmonWorld");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1280, 720);
        this.setUndecorated(true);
        this.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
        this.setLocationRelativeTo(null);

        this.setIconImage(Launcher.getIcon());

        // Permet d'ajouter le panneau principal à l'application
        this.setContentPane(new LauncherPanel(this));

        // Permet à l'utilisateur de pouvoir bouger la fenêtre
        WindowMover mover = new WindowMover(this);
        this.addMouseListener(mover);
        this.addMouseMotionListener(mover);

        // Permet de définir l'opacité de l'application
        this.setOpacity(0);

        // Affiche l'application
        this.setVisible(true);

        // Permet de faire apparaître l'application avec un fadeIn
        Timeline.builder(this)
                .addPropertyToInterpolate("opacity", 0.0f, 1.0f)
                .setDuration(70)
                .play();
    }

    /**
     * Permet de récupérer l'instance de l'application.
     * @return L'instance de l'application.
     */
    public static MainFrame getInstance() {
        return instance;
    }

    /**
     * Permet de récupérer l'objet de sauvegarde des options.
     * @return L'objet de sauvegarde des options.
     */
    public static Saver getSaver() {
        return saver;
    }
}

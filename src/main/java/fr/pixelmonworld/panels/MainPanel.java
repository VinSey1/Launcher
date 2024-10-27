package fr.pixelmonworld.panels;

import fr.pixelmonworld.domain.DefaultLauncherPanel;
import fr.pixelmonworld.panels.launcher.LauncherPanel;
import fr.pixelmonworld.panels.prelauncher.PreLauncherPanel;

/**
 * Panneau principal de l'application servant de parent pour le reste des éléments spécifiques.
 */
public class MainPanel extends DefaultLauncherPanel {

    /**
     * Initialise le panneau de pré-lancement.
     */
    public void initPreLauncher() {
        this.add(new PreLauncherPanel(this, 250, 300));
    }

    /**
     * Initialise le panneau de lancement.
     */
    public void initLauncher() {
        this.add(new LauncherPanel(this));
    }

    /**
     * Constructeur par défaut.
     * @param width  La largeur du panneau.
     * @param height La hauteur du panneau.
     */
    public MainPanel(int width, int height) {
        super(width, height);
        initPreLauncher();
        initLauncher();
    }
}
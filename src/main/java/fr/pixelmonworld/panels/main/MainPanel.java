package fr.pixelmonworld.panels.main;

import fr.pixelmonworld.domain.DefaultLauncherPanel;

/**
 * Panneau principal de l'application servant de parent pour le reste des éléments spécifiques.
 */
public class MainPanel extends DefaultLauncherPanel {
    public void initPreLauncher() {
        this.add(new PreLauncherPanel(this, 250, 300));
    }

    public void initLauncher() {
        this.add(new LauncherPanel(this));
    }

    /**
     * Constructeur par défaut.
     *
     * @param width  La largeur du panneau.
     * @param height La hauteur du panneau.
     */
    public MainPanel(int width, int height) {
        super(width, height);
        initPreLauncher();
        initLauncher();
    }
}
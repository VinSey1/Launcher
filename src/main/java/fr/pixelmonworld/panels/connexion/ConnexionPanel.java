package fr.pixelmonworld.panels.connexion;

import fr.pixelmonworld.domain.DefaultLauncherPanel;

import java.awt.*;
import java.io.IOException;

/**
 * Panneau permettant d'afficher le bouton de connexion.
 */
public class ConnexionPanel extends DefaultLauncherPanel {

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param width La largeur du panneau.
     * @param height La hauteur du panneau.
     * @param x Les coordonnées X du panneau.
     * @param y Les coordonnées Y du panneau.
     * @throws IOException Problème lors d'une mise à jour graphique.
     */
    public ConnexionPanel(Component parent, int width, int height, int x, int y) throws IOException {
        super(parent, width, height, x, y);

        // Ajout du bouton de connexion
        this.add(new ConnexionButton(this, this.getHeight() / 2));
    }
}

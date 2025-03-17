package fr.pixelmonworld.panels.connexion;

import fr.pixelmonworld.Launcher;
import fr.pixelmonworld.domain.DefaultLauncherPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Panneau permettant d'afficher le bouton de connexion.
 */
public class ConnexionPanel extends DefaultLauncherPanel {

    // Structure de la fenêtre
    private ImageIcon backgroundIcon = new ImageIcon(Launcher.getConnexionPanel());

    // Est-ce que l'utilisateur est connecté à Microsoft ?
    private boolean microsoftAuth = false;

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param width La largeur du panneau.
     * @param height La hauteur du panneau.
     * @param x Les coordonnées X du panneau.
     * @param y Les coordonnées Y du panneau.
     */
    public ConnexionPanel(Component parent, int width, int height, int x, int y) {
        super(parent, width, height, x, y);

//        // Ajout du bouton de connexion
        this.add(new ConnexionButton(this, this.getHeight() / 2));

//        this.setBackground(Color.RED);

        // Ajout du background du panel
        JLabel background = new JLabel(backgroundIcon);
        background.setBounds(0, 0, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
        this.add(background);
    }

    public boolean isMicrosoftAuth() {
        return microsoftAuth;
    }

    public void setMicrosoftAuth(boolean microsoftAuth) {
        this.microsoftAuth = microsoftAuth;
    }
}

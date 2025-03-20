package fr.pixelmonworld.launcher.connexion_panel;

import fr.pixelmonworld.domain.DefaultLauncherPanel;
import fr.pixelmonworld.launcher.connexion_panel.buttons.*;
import fr.pixelmonworld.utils.Launcher;

import javax.swing.*;
import java.awt.*;

/**
 * Panneau de connexion de l'application contenant également les boutons d'aide, de tutoriel et d'accès à Java.
 */
public class ConnexionPanel extends DefaultLauncherPanel {

    // Background du panneau
    private ImageIcon backgroundIcon = new ImageIcon(Launcher.getConnexionPanel());

    // Est-ce que l'utilisateur est connecté à Microsoft ?
    private boolean microsoftAuth = false;

    // Bouton de connexion
    private ConnexionButton connexionButton;

    // Bouton de tutoriel
    private TutoButton tutoButton;

    // Bouton d'aide
    private HelpButton helpButton;

    // Bouton de déconnexion
    private DisconnectButton disconnectButton;

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

        this.add(connexionButton = new ConnexionButton(this, 13, 261));
        this.add(tutoButton = new TutoButton(this, 25, 359));
        this.add(helpButton = new HelpButton(this, 137, 359));
        this.add(disconnectButton = new DisconnectButton(this, 13, 359));
        this.add(new JavaButton(this, 253, 356));

        // Ajout du background du panel
        JLabel background = new JLabel(backgroundIcon);
        background.setBounds(0, 0, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
        this.add(background);

        // Si l'utilisateur est déjà connecté, on affiche le bouton de connexion à Minecraft et on enlève le bouton de tuto et d'aide
        this.setTexture();
    }

    /**
     * Permet de savoir si l'utilisateur est connecté à Microsoft.
     * @return Vrai si l'utilisateur est connecté à Microsoft, faux sinon.
     */
    public boolean isMicrosoftAuth() {
        return microsoftAuth;
    }

    /**
     * Permet de définir si l'utilisateur est connecté à Microsoft.
     * @param microsoftAuth Vrai si l'utilisateur est connecté à Microsoft, faux sinon.
     */
    public void setMicrosoftAuth(boolean microsoftAuth) {
        this.microsoftAuth = microsoftAuth;
    }

    /**
     * Permet de définir la texture des boutons en fonction de l'authentification de l'utilisateur.
     */
    public void setTexture() {
        tutoButton.setVisible(!microsoftAuth);
        helpButton.setVisible(!microsoftAuth);
        disconnectButton.setVisible(microsoftAuth);

        connexionButton.setTexture();
    }
}

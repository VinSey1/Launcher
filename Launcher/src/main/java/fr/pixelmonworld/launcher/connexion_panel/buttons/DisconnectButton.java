package fr.pixelmonworld.launcher.connexion_panel.buttons;

import fr.pixelmonworld.domain.DefaultLauncherButton;
import fr.pixelmonworld.launcher.connexion_panel.ConnexionPanel;
import fr.pixelmonworld.utils.Launcher;
import fr.theshark34.swinger.event.SwingerEvent;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;

/**
 * Bouton de déconnexion de Microsoft.
 */
public class DisconnectButton extends DefaultLauncherButton {

    // Parent du composant, nécessaire pour prendre en compte les mises à jour graphiques en cascade
    ConnexionPanel parent;

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param y Les coordonnées Y du bouton.
     */
    public DisconnectButton(ConnexionPanel parent, int x, int y) {
        super(parent, x, y, getBufferedImage("launcher/connexion_panel/disconnect_button.png"));
        this.parent = parent;
    }

    /**
     * Permet de supprimer l'authentification Microsoft si le bouton est appuyé.
     * @param swingerEvent L'événement à traiter.
     */
    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        Launcher.disconnect();
        parent.setMicrosoftAuth(false);
        parent.setTexture();
    }
}

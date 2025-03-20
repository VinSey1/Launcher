package fr.pixelmonworld.launcher.top_panel.buttons;

import fr.pixelmonworld.domain.DefaultLauncherButton;
import fr.theshark34.swinger.event.SwingerEvent;

import java.awt.*;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;

/**
 * Bouton de fermeture de l'application.
 */
public class CloseButton extends DefaultLauncherButton {

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param y Les coordonnées Y du bouton.
     */
    public CloseButton(Component parent, int x, int y) {
        super(parent, x, y, getBufferedImage("top_panel/close_button.png"));
    }

    /**
     * Permet de fermer l'application quand le bouton est appuyé.
     * @param swingerEvent L'événement à traiter.
     */
    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        System.exit(0);
    }
}

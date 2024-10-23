package fr.pixelmonworld.panels.main;

import fr.pixelmonworld.domain.DefaultLauncherButton;
import fr.theshark34.swinger.event.SwingerEvent;

import java.awt.*;
import java.io.IOException;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;

/**
 * Bouton de fermeture de l'application.
 */
public class CloseButton extends DefaultLauncherButton {

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param y Les coordonnées Y du bouton.
     * @throws IOException Problème lors d'une mise à jour graphique.
     */
    public CloseButton(Component parent, int y) throws IOException {
        super(parent, y, getBufferedImage("buttons/close_button.png"));
        this.setBounds(parent.getWidth() - this.getWidth() - 10, y);
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

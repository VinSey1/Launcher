package fr.pixelmonworld.launcher.news_panel;

import fr.pixelmonworld.domain.DefaultLauncherButton;
import fr.theshark34.swinger.event.SwingerEvent;

import java.awt.*;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;

/**
 * Bouton de fermeture du panel de news.
 */
public class CloseNewsButton extends DefaultLauncherButton {

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param y Les coordonnées Y du bouton.
     */
    public CloseNewsButton(Component parent, int x, int y) {
        super(parent, x, y, getBufferedImage("launcher/news_panel/close_button.png"));
    }

    /**
     * Permet de fermer l'application quand le bouton est appuyé.
     * @param swingerEvent L'événement à traiter.
     */
    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        this.getParent().setVisible(false);
    }
}

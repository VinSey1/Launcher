package fr.pixelmonworld.panels.buttons;

import fr.pixelmonworld.Launcher;
import fr.pixelmonworld.domain.DefaultLauncherButton;
import fr.theshark34.swinger.event.SwingerEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;

/**
 * Bouton pour lancer l'application Twitter.
 */
public class TwitterButton extends DefaultLauncherButton {

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param y Les coordonnées Y du bouton.
     */
    public TwitterButton(Component parent, int y) {
        super(parent, y, getBufferedImage("buttons/medias/twitter_button.png"));
        this.setBounds(parent.getWidth() - this.getWidth() - 20, this.getY(), this.getWidth(), this.getHeight());
    }

    /**
     * Permet d'accéder à Twitter quand le bouton est appuyé.
     * @param swingerEvent L'événement à traiter.
     */
    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        try {
            Desktop.getDesktop().browse(URI.create("https://twitter.com/PixelmonWorldFR"));
        } catch (IOException e) {
            Launcher.erreurInterne(e);
        }
    }
}
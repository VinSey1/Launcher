package fr.pixelmonworld.panels.buttons;

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
     * @throws IOException Problème lors d'une mise à jour graphique.
     */
    public TwitterButton(Component parent, int y) throws IOException {
        super(parent, y, getBufferedImage("twitter_button.png"));
        this.setBounds(this.getX() + this.getX() / 2, this.getY(), this.getWidth(), this.getHeight());
    }

    /**
     * Permet d'accéder à Twitter quand le bouton est appuyé.
     * @param swingerEvent L'événement à traiter.
     */
    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        try {
            Desktop.getDesktop().browse(URI.create("https://twitter.com/PixelmonWorldFR"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
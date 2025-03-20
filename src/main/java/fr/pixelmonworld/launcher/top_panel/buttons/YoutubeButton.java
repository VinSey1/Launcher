package fr.pixelmonworld.launcher.top_panel.buttons;

import fr.pixelmonworld.utils.Launcher;
import fr.pixelmonworld.domain.DefaultLauncherButton;
import fr.theshark34.swinger.event.SwingerEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;

/**
 * Bouton pour lancer l'application Youtube.
 */
public class YoutubeButton extends DefaultLauncherButton {

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param y Les coordonnées Y du bouton.
     */
    public YoutubeButton(Component parent, int x, int y) {
        super(parent, x, y, getBufferedImage("top_panel/youtube_button.png"));
    }

    /**
     * Permet d'accéder à Youtube quand le bouton est appuyé.
     * @param swingerEvent L'événement à traiter.
     */
    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        try {
            Desktop.getDesktop().browse(URI.create("https://www.youtube.com/c/PixelmonWorldFR"));
        } catch (IOException e) {
            Launcher.erreurInterne(e);
        }
    }
}

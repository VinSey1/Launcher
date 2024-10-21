package fr.pixelmonworld.panels.buttons;

import fr.pixelmonworld.Launcher;
import fr.pixelmonworld.domain.DefaultLauncherButton;
import fr.theshark34.swinger.event.SwingerEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;

/**
 * Bouton pour lancer l'application TikTok.
 */
public class TikTokButton extends DefaultLauncherButton {

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param y Les coordonnées Y du bouton.
     * @throws IOException Problème lors d'une mise à jour graphique.
     */
    public TikTokButton(Component parent, int y) throws IOException {
        super(parent, y, getBufferedImage("tiktok_button.png"));
        this.setBounds(this.getX() / 2, this.getY(), this.getWidth(), this.getHeight());
    }

    /**
     * Permet d'accéder à TikTok quand le bouton est appuyé.
     * @param swingerEvent L'événement à traiter.
     */
    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        try {
            Desktop.getDesktop().browse(URI.create("https://www.java.com/fr/download/manual.jsp"));
        } catch (IOException e) {
            Launcher.erreurInterne(e);
        }
    }
}

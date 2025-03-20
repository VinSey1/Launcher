package fr.pixelmonworld.launcher.connexion_panel.buttons;

import fr.pixelmonworld.domain.DefaultLauncherButton;
import fr.pixelmonworld.utils.Launcher;
import fr.theshark34.swinger.event.SwingerEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;

public class TutoButton extends DefaultLauncherButton {

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param y Les coordonnées Y du bouton.
     */
    public TutoButton(Component parent, int x, int y) {
        super(parent, x, y, getBufferedImage("launcher/connexion_panel/tuto_button.png"));
    }

    /**
     * Permet d'accéder au tuto sur le site quand le bouton est appuyé.
     * @param swingerEvent L'événement à traiter.
     */
    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        try {
            Desktop.getDesktop().browse(URI.create("https://pixelmonworld.fr/join"));
        } catch (IOException e) {
            Launcher.erreurInterne(e);
        }
    }
}

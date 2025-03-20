package fr.pixelmonworld.launcher.connexion_panel.buttons;

import fr.pixelmonworld.domain.DefaultLauncherButton;
import fr.pixelmonworld.utils.Launcher;
import fr.theshark34.swinger.event.SwingerEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;

public class HelpButton extends DefaultLauncherButton {

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param y Les coordonnées Y du bouton.
     */
    public HelpButton(Component parent, int x, int y) {
        super(parent, x, y, getBufferedImage("launcher/connexion_panel/help_button.png"));
    }

    /**
     * Permet d'accéder au salon d'aide sur Discord quand le bouton est appuyé.
     * @param swingerEvent L'événement à traiter.
     */
    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        try {
            Desktop.getDesktop().browse(URI.create("https://discord.gg/F9vMAwNCWk"));
        } catch (IOException e) {
            Launcher.erreurInterne(e);
        }
    }
}

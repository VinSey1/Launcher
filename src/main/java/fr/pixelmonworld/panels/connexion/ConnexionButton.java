package fr.pixelmonworld.panels.connexion;

import fr.pixelmonworld.Launcher;
import fr.pixelmonworld.domain.DefaultLauncherButton;
import fr.pixelmonworld.panels.connexion.threads.MicrosoftThread;
import fr.pixelmonworld.panels.connexion.threads.MinecraftThread;
import fr.theshark34.swinger.event.SwingerEvent;

import java.awt.*;
import java.io.IOException;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;

/**
 * Bouton de connexion à Microsoft ou à Minecraft.
 */
public class ConnexionButton extends DefaultLauncherButton {

    // Est-ce que l'utilisateur est connecté à Microsoft ?
    boolean microsoftAuth;

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param y Les coordonnées Y du panneau.
     * @throws IOException Problème lors d'une mise à jour graphique.
     */
    public ConnexionButton(Component parent, int y) throws IOException {
        super(parent, y, getBufferedImage("microsoft_button.png"));
        // Vérification de la connexion par défaut
        microsoftAuth = Launcher.defaultAuth();
        // Si l'utilisateur est déjà connecté, on affiche le bouton de connexion à Minecraft
        if (microsoftAuth) {
            this.setTexture(getBufferedImage("minecraft_button.png"));
        }
    }

    /**
     * Permet de mettre à jour le bouton en lui signalant que l'utilisateur est désormais connecté à Microsoft.
     * @throws IOException Problème lors d'une mise à jour graphique.
     */
    public void updateAuthStatus() throws IOException {
        this.microsoftAuth = true;
        this.setTexture(getBufferedImage("minecraft_button.png"));
    }

    /**
     * Permet de lancer l'authentification Microsoft ou mettre à jour et lancer Minecraft si le bouton est appuyé.
     * @param swingerEvent L'événement à traiter.
     */
    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        Thread t;
        if (!microsoftAuth) {
            t = new Thread(new MicrosoftThread(this));
        } else {
            t = new Thread(new MinecraftThread());
        }
        t.start();
    }
}

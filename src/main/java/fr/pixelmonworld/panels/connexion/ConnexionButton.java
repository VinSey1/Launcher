package fr.pixelmonworld.panels.connexion;

import fr.pixelmonworld.Launcher;
import fr.pixelmonworld.domain.DefaultLauncherButton;
import fr.pixelmonworld.panels.connexion.threads.MicrosoftThread;
import fr.pixelmonworld.panels.connexion.threads.MinecraftThread;
import fr.theshark34.swinger.event.SwingerEvent;

import java.awt.*;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;

/**
 * Bouton de connexion à Microsoft ou à Minecraft.
 */
public class ConnexionButton extends DefaultLauncherButton {

    ConnexionPanel parent;

    // Est-ce que l'event du bouton est déjà en cours d'exécution ?
    boolean isClicked;

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param y Les coordonnées Y du panneau.
     */
    public ConnexionButton(ConnexionPanel parent, int y) {
        super(parent, y, getBufferedImage("connexion_panel/connect_button.png"));
        this.parent = parent;
        // Vérification de la connexion par défaut
        parent.setMicrosoftAuth(Launcher.defaultAuth());
        // Si l'utilisateur est déjà connecté, on affiche le bouton de connexion à Minecraft
        this.setTexture();
    }

    /**
     * Permet de mettre à jour le bouton en lui signalant que l'utilisateur est désormais connecté à Microsoft.
     */
    public void updateAuthStatus() {
        parent.setMicrosoftAuth(true);
        this.setTexture();
    }

    /**
     * Permet de lancer l'authentification Microsoft ou mettre à jour et lancer Minecraft si le bouton est appuyé.
     * @param swingerEvent L'événement à traiter.
     */
    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        if (!isClicked) {
            setClicked(true);
            Thread t;
            if (!parent.isMicrosoftAuth()) {
                t = new Thread(new MicrosoftThread(this));
            } else {
                t = new Thread(new MinecraftThread(this));
            }
            t.start();
        }
    }

    /**
     * Permet de définir si le bouton a été cliqué ou non.
     * @param clicked Oui si le bouton a été cliqué.
     */
    public void setClicked(boolean clicked) {
        isClicked = clicked;
        if (isClicked) {
            if (parent.isMicrosoftAuth()) {
                this.setTexture(getBufferedImage("connexion_panel/disabled_connect_button.png"));
            } else {
                this.setTexture(getBufferedImage("connexion_panel/disabled_play_button.png"));
            }
            this.setCursor(Cursor.getDefaultCursor());
        } else {
            this.setTexture();
        }
    }

    /**
     * Permet de mettre à jour l'image en fonction de si c'est une connexion Microsoft ou une connexion Minecraft.
     */
    private void setTexture() {
        if (parent.isMicrosoftAuth()) {
            this.setTexture(getBufferedImage("connexion_panel/play_button.png"));
        } else {
            this.setTexture(getBufferedImage("connexion_panel/connect_button.png"));
        }
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}

package fr.pixelmonworld.panels.buttons;

import fr.pixelmonworld.domain.DefaultLauncherPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;

/**
 * Panneau permettant d'afficher l'ensemble des boutons d'accès aux médias.
 */
public class ButtonsPanel extends DefaultLauncherPanel {

    // Logo d'erreur
    private BufferedImage errorLogoImage = getBufferedImage("buttons/error/error_logo.png");
    // Image contenant le texte "Erreur de lancement ?"
    private BufferedImage errorTextImage = getBufferedImage("buttons/error/error_text.png");
    // Logo de discord
    private BufferedImage discordLogoImage = getBufferedImage("buttons/discord/discord_logo.png");
    // Image contenant le texte "Rejoins-nous !"
    private BufferedImage discordTextImage = getBufferedImage("buttons/discord/discord_text.png");

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param width La largeur du panneau.
     * @param height La hauteur du panneau.
     * @param x Les coordonnées X du panneau.
     * @param y Les coordonnées Y du panneau.
     * @throws IOException Problème lors d'une mise à jour graphique.
     */
    public ButtonsPanel(Component parent, int width, int height, int x, int y) throws IOException {
        super(parent, width, height, x, y);

        int currentY = 60;

        // Ajout du logo d'erreur
        this.add(genererImage(currentY, new ImageIcon(errorLogoImage)));

        currentY += 80;
        // Ajout du texte d'erreur
        this.add(genererImage(currentY, new ImageIcon(errorTextImage)));

        currentY += 60;
        // Ajout du bouton Update Java
        this.add(new JavaButton(this, currentY));

        currentY += 80;
        // Ajout du logo discord
        this.add(genererImage(currentY, new ImageIcon(discordLogoImage)));
        currentY += 80;
        // Ajout du texte "Rejoins-nous !"
        this.add(genererImage(currentY, new ImageIcon(discordTextImage)));
        currentY += 60;
        // Ajout du bouton Discord
        this.add(new DiscordButton(this, currentY));

        currentY += 80;
        // Ajout des boutons Média
        this.add(new TikTokButton(this, currentY));
        this.add(new YoutubeButton(this, currentY));
        this.add(new TwitterButton(this, currentY));
    }
}

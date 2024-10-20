package fr.pixelmonworld.panels.buttons;

import fr.pixelmonworld.domain.DefaultLauncherPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static fr.pixelmonworld.utils.ImagesSelector.getBufferedImage;

public class ButtonsPanel extends DefaultLauncherPanel {

    private BufferedImage errorTextImage = getBufferedImage("error_text.png");
    private BufferedImage errorLogoImage = getBufferedImage("error_logo.png");

    private BufferedImage discordTextImage = getBufferedImage("discord_text.png");
    private BufferedImage discordLogoImage = getBufferedImage("discord_logo.png");

    public ButtonsPanel(Component parent, int width, int height, int x, int y) throws IOException {
        super(parent, width, height, x, y);

        // Ajout du logo d'erreur
        this.add(genererImage(60, new ImageIcon(errorLogoImage)));

        // Ajout du texte d'erreur
        this.add(genererImage(160, new ImageIcon(errorTextImage)));

        // Ajout du bouton Update Java
        this.add(new JavaButton(this, 235));

        // Ajout du logo discord
        this.add(genererImage(360, new ImageIcon(discordLogoImage)));

        // Ajout du texte "Rejoins-nous !"
        this.add(genererImage(460, new ImageIcon(discordTextImage)));

        // Ajout du bouton Discord
        this.add(new DiscordButton(this, 535));

        this.add(new TikTokButton(this, 635));
        this.add(new YoutubeButton(this, 635));
        this.add(new TwitterButton(this, 635));
    }

}

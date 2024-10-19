package fr.pixelmonworld.panels.buttons;

import fr.pixelmonworld.domain.DefaultLauncherPanel;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static fr.pixelmonworld.utils.ImagesSelector.getBufferedImage;

public class ButtonsPanel extends DefaultLauncherPanel {

    private BufferedImage errorTextImage = getBufferedImage("error_text.png");
    private BufferedImage errorLogoImage = getBufferedImage("error_logo.png");

    private BufferedImage discordTextImage = getBufferedImage("discord_text.png");
    private BufferedImage discordLogoImage = getBufferedImage("discord_logo.png");

    public ButtonsPanel(int width, int height, int x, int y) throws IOException {
        super(width, height, x, y);

        // Ajout du logo d'erreur
        this.add(genererImage(60, 172, 166, new ImageIcon(errorLogoImage)));

        // Ajout du texte d'erreur
        this.add(genererImage(160, 447, 102, new ImageIcon(errorTextImage)));

        // Ajout du bouton Update Java
        this.add(new JavaButton(this.getWidth(), 235));

        // Ajout du logo discord
        this.add(genererImage(360, 172, 166, new ImageIcon(discordLogoImage)));

        // Ajout du texte "Rejoins-nous !"
        this.add(genererImage(460, 447, 102, new ImageIcon(discordTextImage)));

        // Ajout du bouton Discord
        this.add(new DiscordButton(this.getWidth(), 535));

        this.add(new TikTokButton(this.getWidth(), 635));
        this.add(new YoutubeButton(this.getWidth(), 635));
        this.add(new TwitterButton(this.getWidth(), 635));
    }

}

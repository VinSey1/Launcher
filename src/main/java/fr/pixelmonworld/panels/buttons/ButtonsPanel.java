package fr.pixelmonworld.panels.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static fr.pixelmonworld.utils.ImagesSelector.getBufferedImage;

public class ButtonsPanel extends JPanel {

    private BufferedImage errorTextImage = getBufferedImage("error_text.png");
    private BufferedImage errorLogoImage = getBufferedImage("error_logo.png");

    private BufferedImage discordTextImage = getBufferedImage("discord_text.png");
    private BufferedImage discordLogoImage = getBufferedImage("discord_logo.png");

    public ButtonsPanel(int width, int height, int x, int y) throws IOException {
        this.setLayout(null);
        this.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
        this.setBounds(x, y, width, height);

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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(null, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    private JLabel genererImage(int y, int width, int height, ImageIcon image) {
        JLabel result = new JLabel(image);
        result.setBounds(this.getWidth() / 2 - width / 2, y - height / 2, width, height);
        return result;
    }
}

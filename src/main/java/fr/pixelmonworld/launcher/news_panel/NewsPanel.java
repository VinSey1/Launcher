package fr.pixelmonworld.launcher.news_panel;

import fr.pixelmonworld.domain.DefaultLauncherPanel;
import fr.pixelmonworld.domain.News;
import fr.pixelmonworld.utils.Launcher;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;

/**
 * Panneau des actualités.
 */
public class NewsPanel extends DefaultLauncherPanel {

    // Background du panneau
    private ImageIcon backgroundIcon = new ImageIcon(Objects.requireNonNull(getBufferedImage("launcher/news_panel/background.png")));

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param news Les actualités à afficher.
     */
    public NewsPanel(Component parent, int x, int y, Collection<News> news) {
        super(parent, 0, 0, x, y);
        this.setSize(backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());

        Launcher.setNewsPanel(this);

        try {
            for (int i = 0; i < news.size(); i++) {
                this.add(new NewPanel(this, (this.getHeight() / 4) * i + 60 + (i * 6), (News) news.toArray()[i]));
            }
        } catch (FontFormatException | IOException e) {
            Launcher.erreurInterne(e);
        }

        this.add(new CloseNewsButton(parent, this.getWidth() - 35, 15));

        JLabel background = new JLabel(backgroundIcon);
        background.setBounds(0, 0, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
        this.add(background);
    }
}

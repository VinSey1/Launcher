package fr.pixelmonworld.panels.launcher.top;

import fr.pixelmonworld.Launcher;
import fr.pixelmonworld.MainFrame;
import fr.pixelmonworld.domain.DefaultLauncherPanel;
import fr.pixelmonworld.panels.news.NewsAlert;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;

public class TopPanel extends DefaultLauncherPanel {

    // Alerte de news
    private NewsAlert newsAlert;

    // Structure de la fenêtre
    private ImageIcon backgroundIcon = new ImageIcon(Objects.requireNonNull(getBufferedImage("top_bar/background.png")));

    // Structure de la fenêtre
    private ImageIcon serverIconIcon = new ImageIcon(Objects.requireNonNull(getBufferedImage("top_bar/server_icon.png")));

    public TopPanel(Component parent) {
        super(parent, parent.getWidth(), parent.getHeight());

        Launcher.setTopPanel(this);

        // Ajout de l'alerte de news
        newsAlert = new NewsAlert(this, 1186, 12);
        String isNews = MainFrame.getSaver().get("news");
        newsAlert.setVisible(isNews != null && isNews.equals("true"));
        this.add(newsAlert);

        JLabel serverIcon = new JLabel(serverIconIcon);
        serverIcon.setBounds(8, 8, serverIconIcon.getIconWidth(), serverIconIcon.getIconHeight());
        this.add(serverIcon);

        // Ajout des boutons Média
        this.add(new DiscordButton(this, 75, 8));
        this.add(new YoutubeButton(this, 130, 8));
        this.add(new TwitterButton(this, 185, 8));
        this.add(new TikTokButton(this, 240, 8));

        this.add(new NewsButton(this, 1140, 8));

        // Ajout du bouton de fermeture
        this.add(new CloseButton(this,1220, 8));

        JLabel background = new JLabel(backgroundIcon);
        background.setBounds(this.getWidth() - backgroundIcon.getIconWidth(), 0, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
        this.add(background);
    }

    /**
     * Permet de récupérer l'alerte de news.
     * @return L'alerte de news.
     */
    public NewsAlert getNewsAlert() {
        return newsAlert;
    }
}

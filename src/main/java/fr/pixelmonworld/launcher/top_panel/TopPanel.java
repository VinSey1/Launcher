package fr.pixelmonworld.launcher.top_panel;

import fr.pixelmonworld.MainFrame;
import fr.pixelmonworld.domain.DefaultLauncherPanel;
import fr.pixelmonworld.launcher.top_panel.buttons.*;
import fr.pixelmonworld.launcher.top_panel.buttons.news.NewsAlert;
import fr.pixelmonworld.launcher.top_panel.buttons.news.NewsButton;
import fr.pixelmonworld.utils.Launcher;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;

public class TopPanel extends DefaultLauncherPanel {

    // Alerte de news
    private NewsAlert newsAlert;

    // Background du panneau
    private ImageIcon backgroundIcon = new ImageIcon(Objects.requireNonNull(getBufferedImage("launcher/top_panel/background.png")));

    // L'icône du serveur à afficher en haut à gauche
    private ImageIcon serverIconIcon = new ImageIcon(Objects.requireNonNull(getBufferedImage("launcher/top_panel/server_icon.png")));

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     */
    public TopPanel(Component parent) {
        super(parent);
        this.setSize(backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());

        Launcher.setTopPanel(this);

        // Ajout de l'icône du serveur
        JLabel serverIcon = new JLabel(serverIconIcon);
        serverIcon.setBounds(8, 8, serverIconIcon.getIconWidth(), serverIconIcon.getIconHeight());
        this.add(serverIcon);

        // Ajout des boutons Média
        this.add(new DiscordButton(this, 75, 8));
        this.add(new YoutubeButton(this, 130, 8));
        this.add(new TwitterButton(this, 185, 8));
        this.add(new TikTokButton(this, 240, 8));

        // Ajout de l'alerte et du bouton de news
        newsAlert = new NewsAlert(this, 1172, 14);
        String isNews = MainFrame.getSaver().get("news");
        newsAlert.setVisible(isNews != null && isNews.equals("true"));
        this.add(newsAlert);

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

package fr.pixelmonworld.panels.news;

import fr.pixelmonworld.domain.DefaultLauncherPanel;
import fr.pixelmonworld.domain.News;
import fr.pixelmonworld.utils.SiteUtils;

import java.awt.*;
import java.io.IOException;
import java.util.Optional;

public class NewsPanel extends DefaultLauncherPanel {

    private News news;

    private NewsButton newsButton;

    /**
     * Constructeur par défaut.
     * @param width La largeur du panneau.
     * @param height La hauteur du panneau.
     * @throws IOException Problème lors d'une mise à jour graphique.
     */
    public NewsPanel(Component parent, int width, int height) throws IOException {
        super(parent, width, height);

        Optional<News> news = SiteUtils.getNewsFromSite();

        if (news.isPresent()) {
            this.news = news.get();
            // Ajout du bouton de news
            this.add(newsButton = new NewsButton(this, 70, 285));
        }
    }

    /**
     * Permet d'afficher les news.
     */
    // TODO A faire
    public void showNews() {
        System.out.println("News : " + news);
    }
}

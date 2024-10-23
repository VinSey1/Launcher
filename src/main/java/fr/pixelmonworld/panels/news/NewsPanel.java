package fr.pixelmonworld.panels.news;

import fr.pixelmonworld.Launcher;
import fr.pixelmonworld.domain.DefaultLauncherPanel;
import fr.pixelmonworld.domain.News;
import fr.pixelmonworld.utils.SiteUtils;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Optional;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;

public class NewsPanel extends DefaultLauncherPanel implements SwingerEventListener {

    private News news;
    private STexturedButton newsButton;
    private boolean clicked;

    /**
     * Constructeur par défaut.
     * @param width La largeur du panneau.
     * @param height La hauteur du panneau.
     * @throws IOException Problème lors d'une mise à jour graphique.
     */
    public NewsPanel(Component parent, int width, int height, int x, int y) throws IOException {
        super(parent, width, height);

        Optional<News> news = SiteUtils.getNewsFromSite();

        if (news.isPresent()) {
            this.news = news.get();

            BufferedImage newsButtonImage = getBufferedImage("news_button.png");
            newsButton = new STexturedButton(newsButtonImage, newsButtonImage);
            newsButton.setBounds(x, y, newsButtonImage.getWidth(), newsButtonImage.getHeight());
            newsButton.addEventListener(this);
            this.add(newsButton);
        }
    }

    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        if (!clicked) {
            this.clicked = true;
            Launcher.removeNewsAlert();
        }
        System.out.println("News : " + news);
    }
}

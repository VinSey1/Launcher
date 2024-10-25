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
import java.util.Collection;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;

public class NewsPanel extends DefaultLauncherPanel implements SwingerEventListener {

    private News news;
    private STexturedButton newsButton;
    private boolean clicked;

    /**
     * Constructeur par défaut.
     * @param width La largeur du panneau.
     * @param height La hauteur du panneau.
     */
    public NewsPanel(Component parent, int width, int height, int x, int y) {
        super(parent, width, height);

        Collection<News> news = SiteUtils.getNewsFromSite();

        if (news.size() > 0) {
//            this.news = news.get();

            BufferedImage newsButtonImage = getBufferedImage("news/news_button.png");
            newsButton = new STexturedButton(newsButtonImage, newsButtonImage);
            newsButton.setBounds(x, y, newsButtonImage.getWidth(), newsButtonImage.getHeight());
            newsButton.addEventListener(this);
            newsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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

package fr.pixelmonworld.panels.news;

import fr.pixelmonworld.Launcher;
import fr.pixelmonworld.MainFrame;
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

    private Collection<News> news;
    private STexturedButton newsButton;
    private boolean clicked;
    private ShowNewsPanel showNewsPanel;

    /**
     * Constructeur par défaut.
     */
    public NewsPanel(Component parent) {
        super(parent, parent.getWidth(), parent.getHeight());

        news = SiteUtils.getNewsFromSite();

        if (news.isEmpty()) {
            MainFrame.getSaver().set("news", "false");
        }

        BufferedImage newsButtonImage = getBufferedImage("news/news_button.png");
        newsButton = new STexturedButton(newsButtonImage, newsButtonImage);
        newsButton.setBounds(40, 220, newsButtonImage.getWidth(), newsButtonImage.getHeight());
        newsButton.addEventListener(this);
        newsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.add(newsButton);

        this.add(showNewsPanel = new ShowNewsPanel(parent, 500, 200, news));
        showNewsPanel.setVisible(false);
    }

    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        if (!clicked) {
            this.clicked = true;
            Launcher.removeNewsAlert();
        }
        showNewsPanel.setVisible(!showNewsPanel.isVisible());
    }
}

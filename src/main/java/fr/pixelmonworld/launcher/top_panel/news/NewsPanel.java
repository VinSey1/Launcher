package fr.pixelmonworld.launcher.top_panel.news;

import fr.pixelmonworld.utils.Launcher;
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

/**
 * Panneau permettant d'afficher le bouton des actualités et permettre de les afficher.
 */
public class NewsPanel extends DefaultLauncherPanel implements SwingerEventListener {

    // Les actualités à afficher
    private Collection<News> news;
    // Bouton permettant d'afficher les actualités
    private STexturedButton newsButton;
    // Permet de savoir si le bouton a été cliqué²
    private boolean clicked;
    // Panneau permettant d'afficher les actualités
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

        BufferedImage newsButtonImage = getBufferedImage("top_bar/news_button.png");
        newsButton = new STexturedButton(newsButtonImage, newsButtonImage);
        newsButton.setBounds(1186, 12, newsButtonImage.getWidth(), newsButtonImage.getHeight());
        newsButton.addEventListener(this);
        newsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.add(newsButton);

        this.add(showNewsPanel = new ShowNewsPanel(parent, 500, 200, news));
        showNewsPanel.setVisible(false);
    }

    /**
     * Permet de gérer l'événement du bouton et d'afficher ou non les news.
     * @param swingerEvent L'événement du bouton.
     */
    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        if (!clicked) {
            this.clicked = true;
            Launcher.removeNewsAlert();
        }
        showNewsPanel.setVisible(!showNewsPanel.isVisible());
    }
}

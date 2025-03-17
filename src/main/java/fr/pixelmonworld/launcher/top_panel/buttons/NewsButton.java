package fr.pixelmonworld.launcher.top_panel.buttons;

import fr.pixelmonworld.utils.Launcher;
import fr.pixelmonworld.MainFrame;
import fr.pixelmonworld.domain.DefaultLauncherButton;
import fr.pixelmonworld.domain.News;
import fr.pixelmonworld.launcher.top_panel.news.ShowNewsPanel;
import fr.pixelmonworld.utils.SiteUtils;
import fr.theshark34.swinger.event.SwingerEvent;

import java.awt.*;
import java.util.Collection;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;

public class NewsButton extends DefaultLauncherButton {

    // Les actualités à afficher
    private Collection<News> news;
    // Permet de savoir si le bouton a été cliqué²
    private boolean clicked;
    // Panneau permettant d'afficher les actualités
    private ShowNewsPanel showNewsPanel;

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param y Les coordonnées Y du bouton.
     */
    public NewsButton(Component parent, int x, int y) {
        super(parent, x, y, getBufferedImage("top_bar/news_button.png"));

        news = SiteUtils.getNewsFromSite();

        if (news.isEmpty()) {
            MainFrame.getSaver().set("news", "false");
        }

        this.add(showNewsPanel = new ShowNewsPanel(parent, 500, 200, news));
    }

    /**
     * Permet d'accéder au site de Java quand le bouton est appuyé.
     * @param swingerEvent L'événement à traiter.
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

package fr.pixelmonworld.launcher;

import fr.pixelmonworld.MainFrame;
import fr.pixelmonworld.domain.DefaultLauncherPanel;
import fr.pixelmonworld.domain.News;
import fr.pixelmonworld.domain.OpacityJLabel;
import fr.pixelmonworld.launcher.connexion_panel.ConnexionPanel;
import fr.pixelmonworld.launcher.news_panel.NewsPanel;
import fr.pixelmonworld.launcher.popup_panel.PopupPanel;
import fr.pixelmonworld.launcher.ram_panel.RamPanel;
import fr.pixelmonworld.launcher.top_panel.TopPanel;
import fr.pixelmonworld.utils.Launcher;
import org.pushingpixels.radiance.animation.api.Timeline;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Objects;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;
import static fr.pixelmonworld.utils.ResourcesUtils.getRandomRenderImage;

/**
 * Panneau principal du launcher.
 */
public class LauncherPanel extends DefaultLauncherPanel {

    // Les actualités à afficher
    private Collection<News> news;

    // Panneau des actualités
    private NewsPanel newsPanel;

    // Image transparente
    private ImageIcon fill = new ImageIcon(Objects.requireNonNull(getBufferedImage("utils/fill.png")));

    // JLabel contenant le premier render ingame du serveur
    private OpacityJLabel render;

    // JLabel contenant le second render ingame du serveur (permet de faire un effet de fade)
    private OpacityJLabel render2;

    // Panneau de popup
    private PopupPanel popupPanel;

    // Permet de savoir si le launcher est en train de charger et de désactiver la mise à jour du background
    private boolean isLoading;

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     */
    public LauncherPanel(Component parent) {
        super(parent, parent.getWidth(), parent.getHeight());

        Launcher.setLauncherPanel(this);

        this.add(popupPanel = new PopupPanel(this, 358, 226));
        popupPanel.setVisible(false);

        this.add(new TopPanel(this));

        this.add(new ConnexionPanel(this, 359, 430, 25, 93));

        this.add(new RamPanel(this, 683, 106, 25, 584));

        // Récupération des news depuis le site
        news = Launcher.getNews();

        if (news.isEmpty()) {
            MainFrame.getSaver().set("news", "false");
        }

        this.add(newsPanel = new NewsPanel(parent, 830, 70, news));
        newsPanel.setVisible(false);

        ImageIcon renderIcon = new ImageIcon(getRandomRenderImage());
        render = new OpacityJLabel(renderIcon);

        // Ajout du screen ingame
        render.setBounds(0, this.getHeight() - render.getIcon().getIconHeight(), render.getIcon().getIconWidth(), render.getIcon().getIconHeight());
        render.setVisible(true);
        this.add(render);

        // Ajout du screen ingame 2
        render2 = new OpacityJLabel(fill);
        render2.setBounds(0, this.getHeight() - render.getIcon().getIconHeight(), render.getIcon().getIconWidth(), render.getIcon().getIconHeight());
        render2.setVisible(false);
        this.add(render2);

        // Permet de changer le render toutes les 10 secondes
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(10000);
                    if (!isLoading) updateRender();
                } catch (Exception e) {
                    Launcher.erreurInterne(e);
                }
            }
        }).start();
    }

    /**
     * Permet de mettre à jour le screen ingame avec un effet de fade.
     *
     */
    public void updateRender() {
        // Récupère une nouvelle image aléatoire (différente de la précédente)
        ImageIcon newRenderIcon = new ImageIcon(getRandomRenderImage());
        // Définit quel render afficher et lequel cacher
        OpacityJLabel renderToShow;
        OpacityJLabel renderToHide;
        if (render.isVisible()) {
            renderToShow = render2;
            renderToHide = render;
        } else {
            renderToShow = render;
            renderToHide = render2;
        }
        // Animation de fade
        renderToShow.setOpacity(0f);
        renderToShow.setIcon(newRenderIcon);
        renderToShow.setVisible(true);
        Timeline.builder(renderToShow)
                .addPropertyToInterpolate("opacity", 0.0f, 1.0f)
                .setDuration(2000)
                .play();
        renderToHide.setVisible(false);
        renderToHide.setIcon(fill);
    }

    /**
     * Permet de mettre à jour le texte affiché par le popup et de faire avancer la barre de progression.
     * @param newText Le nouveau texte à afficher.
     */
    public void updateLog(String newText) {
        popupPanel.updateTextAndValue(newText);
    }

    /**
     * Permet de mettre à jour la valeur maximale de la barre de progression du popup.
     */
    public void updateMaxLogs() {
        popupPanel.setMaxLogs();
    }

    /**
     * Permet de savoir si le launcher est en train de charger et de désactiver la mise à jour du background.
     * @param isLoading Le nouveau statut de chargement.
     */
    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
        this.popupPanel.setVisible(isLoading);
    }
}

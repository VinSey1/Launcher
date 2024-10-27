package fr.pixelmonworld.panels.launcher;

import fr.pixelmonworld.Launcher;
import fr.pixelmonworld.MainFrame;
import fr.pixelmonworld.domain.DefaultLauncherPanel;
import fr.pixelmonworld.domain.RenderJLabel;
import fr.pixelmonworld.panels.buttons.ButtonsPanel;
import fr.pixelmonworld.panels.connexion.ConnexionPanel;
import fr.pixelmonworld.panels.news.NewsAlert;
import fr.pixelmonworld.panels.news.NewsPanel;
import fr.pixelmonworld.panels.ram.RamPanel;
import org.pushingpixels.radiance.animation.api.Timeline;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;
import static fr.pixelmonworld.utils.ResourcesUtils.getRandomRenderImage;

/**
 * Panneau principal du launcher.
 */
public class LauncherPanel extends DefaultLauncherPanel {

    // Structure de la fenêtre
    private ImageIcon backgroundIcon = new ImageIcon(Objects.requireNonNull(getBufferedImage("other/background.png")));
    // Icône du launcher
    private ImageIcon logoIcon = new ImageIcon(Launcher.getLogo());
    // Image ingame du serveur
    private ImageIcon fill = new ImageIcon(Objects.requireNonNull(getBufferedImage("other/fill.png")));
    // JLabel contenant le premier render ingame du serveur
    private RenderJLabel render;
    // JLabel contenant le second render ingame du serveur (permet de faire un effet de fade)
    private RenderJLabel render2;
    // Alerte de news
    private NewsAlert newsAlert;
    // Panel de mise à jour
    private UpdatePanel updatePanel;
    // Permet de savoir si le launcher est en train de charger et de désactiver la mise à jour du background
    private boolean isLoading;

    /**
     * Permet de savoir si le launcher est en train de charger et de désactiver la mise à jour du background.
     * @param isLoading Le nouveau statut de chargement.
     */
    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
        this.updatePanel.setVisible(isLoading);
    }

    /**
     * Permet de récupérer l'alerte de news.
     * @return L'alerte de news.
     */
    public NewsAlert getNewsAlert() {
        return newsAlert;
    }

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     */
    public LauncherPanel(Component parent) {
        super(parent, parent.getWidth(), parent.getHeight());

        Launcher.setLauncherPanel(this);

        // Ajout du logo
        JLabel logo = new JLabel(logoIcon);
        logo.setBounds((this.getWidth() - logoIcon.getIconWidth()) / 2, 0, logoIcon.getIconWidth(), logoIcon.getIconHeight());
        this.add(logo);

        ImageIcon renderIcon = new ImageIcon(getRandomRenderImage());

        // Ajout de l'alerte de news
        newsAlert = new NewsAlert(this, 40, 220);
        String isNews = MainFrame.getSaver().get("news");
        newsAlert.setVisible(isNews != null && isNews.equals("true"));
        this.add(newsAlert);
        this.add(new NewsPanel(this));

        this.add(updatePanel = new UpdatePanel(this, 300, 200));
        updatePanel.setVisible(false);

        // Ajout du panel avec l'ensemble des boutons
        this.add(new ButtonsPanel(this, 402, 585, 1174, 206));

        // Ajout du panel de connexion
        this.add(new ConnexionPanel(this, 402, 226, 1174, 813));

        // Utilisé pour centrer des éléments par rapport à la fenêtre de visualisation des renders
        Rectangle rendersWindow = new Rectangle(27, 209, 1125, 831);

        // Ajout du panel de ram
        this.add(new RamPanel(this, rendersWindow.width, 150, (int) rendersWindow.getX(), (int) (rendersWindow.getHeight() + rendersWindow.getY() - 150)));

        // Ajout du bouton de fermeture
        this.add(new CloseButton(this,this.getHeight() - backgroundIcon.getIconHeight()));

        render = new RenderJLabel(renderIcon);

        // Ajout de la structure de la fenêtre
        JLabel background = new JLabel(backgroundIcon);
        background.setBounds(this.getWidth() - backgroundIcon.getIconWidth(), this.getHeight() - backgroundIcon.getIconHeight(), backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
        this.add(background);

        // Ajout du screen ingame
        render.setBounds(0, 185, render.getIcon().getIconWidth(), render.getIcon().getIconHeight());
        render.setVisible(true);
        this.add(render);

        // Ajout du screen ingame
        render2 = new RenderJLabel(fill);
        render2.setBounds(0, 185, render.getIcon().getIconWidth(), render.getIcon().getIconHeight());
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
        RenderJLabel renderToShow;
        RenderJLabel renderToHide;
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
     * Permet de mettre à jour le texte affiché par le JLabel visible.
     * @param newText Le nouveau texte à afficher.
     */
    public void updateLog(String newText, int i) {
        updatePanel.updateTextAndValue(newText, i);
    }
}

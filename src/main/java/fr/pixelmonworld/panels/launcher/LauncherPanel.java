package fr.pixelmonworld.panels.launcher;

import fr.pixelmonworld.Launcher;
import fr.pixelmonworld.domain.DefaultLauncherPanel;
import fr.pixelmonworld.domain.RenderJLabel;
import fr.pixelmonworld.panels.connexion.ConnexionPanel;
import fr.pixelmonworld.panels.launcher.top.TopPanel;
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

    // Image transparente
    private ImageIcon fill = new ImageIcon(Objects.requireNonNull(getBufferedImage("other/fill.png")));
    // JLabel contenant le premier render ingame du serveur
    private RenderJLabel render;
    // JLabel contenant le second render ingame du serveur (permet de faire un effet de fade)
    private RenderJLabel render2;
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
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     */
    public LauncherPanel(Component parent) {
        super(parent, parent.getWidth(), parent.getHeight());

        Launcher.setLauncherPanel(this);

        this.add(updatePanel = new UpdatePanel(this, 300, 200));
        updatePanel.setVisible(false);

        this.add(new TopPanel(this));

        // Ajout du panel de connexion
        this.add(new ConnexionPanel(this, 359, 430, 25, 93));

//        // Ajout du panel de ram
//        this.add(new RamPanel(this, rendersWindow.width, 150, (int) rendersWindow.getX(), (int) (rendersWindow.getHeight() + rendersWindow.getY() - 150)));

        ImageIcon renderIcon = new ImageIcon(getRandomRenderImage());
        render = new RenderJLabel(renderIcon);

        // Ajout du screen ingame
        render.setBounds(0, this.getHeight() - render.getIcon().getIconHeight(), render.getIcon().getIconWidth(), render.getIcon().getIconHeight());
        render.setVisible(true);
        this.add(render);

        // Ajout du screen ingame 2
        render2 = new RenderJLabel(fill);
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

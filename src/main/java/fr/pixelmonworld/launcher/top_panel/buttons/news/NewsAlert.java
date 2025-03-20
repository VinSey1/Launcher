package fr.pixelmonworld.launcher.top_panel.buttons.news;

import fr.pixelmonworld.domain.OpacityJLabel;
import org.pushingpixels.radiance.animation.api.Timeline;

import javax.swing.*;
import java.awt.*;

import static fr.pixelmonworld.utils.ResourcesUtils.getResource;

/**
 * Permet d'afficher une alerte de news.
 */
public class NewsAlert extends OpacityJLabel {

    // Le parent à appeler pour repaint lors d'une mise à jour graphique.
    Component parent;

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param x Les coordonnées X du panneau.
     * @param y Les coordonnées Y du panneau.
     */
    public NewsAlert(Component parent, int x, int y) {
        super(new ImageIcon(getResource("launcher/top_panel/notification.png")));
        this.parent = parent;
        this.setBounds(x, y, this.getIcon().getIconWidth(), this.getIcon().getIconHeight());
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Permet de changer le render toutes les 10 secondes
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Timeline.builder(this)
                            .addPropertyToInterpolate("opacity", 0.0f, 1.0f)
                            .setDuration(500)
                            .play();
                    Thread.sleep(500);
                    Timeline.builder(this)
                            .addPropertyToInterpolate("opacity", 1.0f, 0.0f)
                            .setDuration(500)
                            .play();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Permet de redessiner le composant ainsi que son parent.
     */
    @Override
    public void repaint() {
        super.repaint();
        if (this.parent != null) {
            this.parent.repaint();
        }
    }
}

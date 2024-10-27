package fr.pixelmonworld.panels.news;

import javax.swing.*;
import java.awt.*;

import static fr.pixelmonworld.utils.ResourcesUtils.getResource;

/**
 * Permet d'afficher une alerte de news.
 */
public class NewsAlert extends JLabel {

    // Le parent à appeler pour repaint lors d'une mise à jour graphique.
    Component parent;

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param x Les coordonnées X du panneau.
     * @param y Les coordonnées Y du panneau.
     */
    public NewsAlert(Component parent, int x, int y) {
        super(new ImageIcon(getResource("news/news_alert.gif")));
        this.parent = parent;
        this.setBounds(x, y, this.getIcon().getIconWidth(), this.getIcon().getIconHeight());
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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

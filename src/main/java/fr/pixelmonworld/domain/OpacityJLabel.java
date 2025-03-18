package fr.pixelmonworld.domain;

import javax.swing.*;
import java.awt.*;

/**
 * JLabel spécifique permettant de gérer l'opacité de l'image affichée.
 */
public class OpacityJLabel extends JLabel {
    private float opacity = 1.0f;

    /**
     * Constructeur prenant une ImageIcon.
     * @param imageIcon L'image à afficher dans le JLabel.
     */
    public OpacityJLabel(ImageIcon imageIcon) {
        super(imageIcon);
    }

    /**
     * Permet de repeindre le composant graphique en prenant en compte l'opacité.
     * @param g L'objet graphique à repeindre.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        super.paintComponent(g2d);
        g2d.dispose();
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
        repaint();
    }
}
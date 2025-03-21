package fr.pixelmonworld.domain;

import javax.swing.*;
import java.awt.*;

/**
 * JLabel spécifique permettant de gérer l'opacité de l'image affichée.
 */
public class OpacityJLabel extends JLabel {

    // L'opacité de l'image par défaut à visible
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

    /**
     * Permet de définir l'opacité de l'image.
     * @param opacity L'opacité de l'image.
     */
    public void setOpacity(float opacity) {
        this.opacity = opacity;
        repaint();
    }

    /**
     * Permet de savoir si l'image est affichée.
     * @return Vrai si l'image est affichée, faux sinon.
     */
    public boolean isShowed() {
        return opacity == 1.0f;
    }
}
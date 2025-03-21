package fr.pixelmonworld.domain;

import javax.swing.*;
import java.awt.*;

/**
 * Classe parente de l'ensemble des panneaux de l'application.
 */
public class DefaultLauncherPanel extends JPanel {

    // Parent du composant, nécessaire pour prendre en compte les mises à jour graphiques en cascade
    Component parent;

    /**
     * Constructeur par défaut permettant de définir la taille et la position du panneau.
     * @param width La largeur du panneau.
     * @param height La hauteur du panneau.
     * @param x Les coordonnées X du panneau.
     * @param y Les coordonnées Y du panneau.
     */
    public DefaultLauncherPanel(int width, int height, int x, int y) {
        // Permet de pouvoir placer les éléments en X, Y
        this.setLayout(null);
        this.setDoubleBuffered(true);
        this.setOpaque(true);
        // Permet d'avoir un fond transparent
        this.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
        // Permet de définir la taille de l'application et son emplacement en X, Y
        this.setBounds(x, y, width, height);
    }

    /**
     * Constructeur par défaut permettant de définir la taille, la position et le parent du panneau.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param width La largeur du panneau.
     * @param height La hauteur du panneau.
     * @param x Les coordonnées X du panneau.
     * @param y Les coordonnées Y du panneau.
     */
    public DefaultLauncherPanel(Component parent, int width, int height, int x, int y) {
        this(width, height, x, y);
        this.parent = parent;
    }

    /**
     * Constructeur par défaut permettant de définir le parent du panneau.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     */
    public DefaultLauncherPanel(Component parent) {
        this(parent, 0, 0);
    }

    /**
     * Constructeur par défaut permettant de définir la taille du panneau.
     * @param width La largeur du panneau.
     * @param height La hauteur du panneau.
     */
    public DefaultLauncherPanel(int width, int height) {
        this(width, height, 0, 0);
    }

    /**
     * Constructeur par défaut permettant de définir la taille du panneau ainsi que son parent.
     * @param width La largeur du panneau.
     * @param height La hauteur du panneau.
     */
    public DefaultLauncherPanel(Component parent, int width, int height) {
        this(parent, width, height, 0, 0);
    }

    /**
     * Permet de générer un texte centré horizontalement de manière dynamique.
     * @param y L'axe Y sur lequel afficher le texte.
     * @param message Le texte à afficher.
     * @return Le JLabel contenant le texte à afficher.
     */
    protected JLabel genererTexte(int y, String message, int fontSize) {
        JLabel result = new JLabel(message);
        result.setBounds((this.getWidth() - (this.getWidth() - 20)) / 2, y - 50 / 2, this.getWidth() - 20, fontSize + 5);
        result.setHorizontalAlignment(SwingConstants.CENTER);
        result.setVerticalAlignment(SwingConstants.CENTER);
        result.setForeground(Color.WHITE);
        return result;
    }

    /**
     * Permet d'afficher le composant avec JavaFX.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(null, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    /**
     * Permet de mettre à jour le composant graphique ainsi que son parent s'il existe (important pour éviter d'avoir
     * des éléments qui ne se mettent pas à jour).
     */
    @Override
    public void repaint() {
        super.repaint();
        if (this.parent != null) this.parent.repaint();
    }
}

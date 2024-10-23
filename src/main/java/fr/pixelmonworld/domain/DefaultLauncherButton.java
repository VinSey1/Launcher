package fr.pixelmonworld.domain;

import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Classe parente de l'ensemble des boutons de l'application.
 */
public class DefaultLauncherButton extends STexturedButton implements SwingerEventListener {

    // Parent du composant, nécessaire pour prendre en compte les mises à jour graphiques en cascade
    Component parent;

    /**
     * Constructeur par défaut permettant de créer un bouton centré horizontalement de manière dynamique.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param y L'axe Y sur lequel afficher l'image.
     * @param image L'image à afficher.
     */
    public DefaultLauncherButton(Component parent, int y, BufferedImage image) {
        this(parent, parent.getWidth() / 2, y, image);
    }

    /**
     * Constructeur par défaut permettant de créer un bouton.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param x L'axe X sur lequel afficher l'image.
     * @param y L'axe Y sur lequel afficher l'image.
     * @param image L'image à afficher.
     */
    public DefaultLauncherButton(Component parent, int x, int y, BufferedImage image) {
        super(image, image);
        this.parent = parent;
        this.setBounds(x - image.getWidth() / 2, y - image.getHeight() / 2, image.getWidth(), image.getHeight());
        this.addEventListener(this);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    /**
     * Permet de redéfinir la texture du bouton.
     * @param texture La nouvelle texture à afficher.
     */
    @Override
    public void setTexture(Image texture) {
        super.setTexture(texture);
        super.setTextureHover(texture);
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

    /**
     * Permet de gérer les événements lors de l'appel du bouton.
     * @param swingerEvent L'événement à traiter.
     */
    @Override
    public void onEvent(SwingerEvent swingerEvent) {}
}

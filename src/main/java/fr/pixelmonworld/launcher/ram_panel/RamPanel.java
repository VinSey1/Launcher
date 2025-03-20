package fr.pixelmonworld.launcher.ram_panel;

import fr.pixelmonworld.utils.Launcher;
import fr.pixelmonworld.domain.DefaultLauncherPanel;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;

/**
 * Panneau permettant d'afficher le sélecteur de ram.
 */
public class RamPanel extends DefaultLauncherPanel implements SwingerEventListener {

    // Le bouton pour baisser la ram
    private STexturedButton moins;

    // Le bouton pour augmenter la ram
    private STexturedButton plus;

    // Background du panneau (la barre de ram)
    private JLabel background;

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param width La largeur du panneau.
     * @param height La hauteur du panneau.
     * @param x Les coordonnées X du panneau.
     * @param y Les coordonnées Y du panneau.
     */
    public RamPanel(Component parent, int width, int height, int x, int y) {
        super(parent, width, height, x, y);

        // Bouton permettant de baisser la ram
        moins = new STexturedButton(getBufferedImage("utils/fill.png"), getBufferedImage("utils/fill.png"));
        moins.setBounds(this.getWidth() / 2 - 52, 8, 20, 20);
        moins.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        moins.addEventListener(this);
        this.setOpaque(true);
        this.add(moins);

        // Bouton permettant d'augmenter la ram
        plus = new STexturedButton(getBufferedImage("utils/fill.png"), getBufferedImage("utils/fill.png"));
        plus.setBounds(this.getWidth() / 2 + 37, 8, 20, 20);
        plus.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        plus.addEventListener(this);
        this.setOpaque(true);
        this.add(plus);

        // Permet de définir l'affichage de ram par défaut
        this.genererBackground();
    }

    /**
     * Permet d'actualiser l'affichage pour que la flèche suive la ram définie par l'utilisateur.
     */
    private void genererBackground() {
        // Permet de récupérer le background associé à l'endroit de la flèche attendu
        ImageIcon backgroundIcon = new ImageIcon(Objects.requireNonNull(getBufferedImage("launcher/ram_panel/ram_" + Launcher.getRam() + ".png")));
        background = new JLabel(backgroundIcon);
        background.setBounds((this.getWidth() - backgroundIcon.getIconWidth()) / 2, (this.getHeight() - backgroundIcon.getIconHeight()) / 2, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
        this.add(background);
        this.repaint();
    }

    /**
     * Permet de gérer les événements d'ajout et de suppression de ram pour mettre à jour les paramètres du launcher.
     * @param swingerEvent L'événement à traiter.
     */
    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        if (swingerEvent.getSource() == plus) {
            Launcher.addRam();
        }
        if (swingerEvent.getSource() == moins) {
            Launcher.removeRam();
        }
        this.remove(background);
        genererBackground();
    }
}

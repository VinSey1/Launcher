package fr.pixelmonworld.panels.ram;

import fr.pixelmonworld.Launcher;
import fr.pixelmonworld.domain.DefaultLauncherPanel;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
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
    // Le background (la barre de ram) à afficher
    private JLabel background;

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param width La largeur du panneau.
     * @param height La hauteur du panneau.
     * @param x Les coordonnées X du panneau.
     * @param y Les coordonnées Y du panneau.
     * @throws IOException Problème lors d'une mise à jour graphique.
     */
    public RamPanel(Component parent, int width, int height, int x, int y) throws IOException {
        super(parent, width, height, x, y);

        // Bouton permettant de baisser la ram
        moins = new STexturedButton(getBufferedImage("fill.png"), getBufferedImage("fill.png"));
        moins.setBounds(400, 20, 40, 40);
        moins.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        moins.addEventListener(this);
        this.add(moins);

        // Bouton permettant d'augmenter la ram
        plus = new STexturedButton(getBufferedImage("fill.png"), getBufferedImage("fill.png"));
        plus.setBounds(500, 20, 40, 40);
        plus.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        plus.addEventListener(this);
        this.add(plus);

        // Permet de définir l'affichage de ram par défaut
        this.genererBackground();
    }

    /**
     * Permet d'actualiser l'affichage pour que la flèche suive la ram définie par l'utilisateur.
     * @throws IOException Problème lors d'une mise à jour graphique.
     */
    private void genererBackground() throws IOException {
        // Permet de récupérer le background associé à l'endroit de la flèche attendu
        ImageIcon backgroundIcon = new ImageIcon(Objects.requireNonNull(getBufferedImage("ram_" + Launcher.getRam() + ".png")));
        background = new JLabel(backgroundIcon);
        background.setBounds(0, 0, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
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
        try {
            this.remove(background);
            genererBackground();
        } catch (IOException e) {
            Launcher.erreurInterne(e);
        }
    }
}

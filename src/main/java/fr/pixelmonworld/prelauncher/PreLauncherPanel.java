package fr.pixelmonworld.prelauncher;

import fr.pixelmonworld.domain.DefaultLauncherPanel;
import fr.pixelmonworld.domain.OpacityJLabel;
import fr.pixelmonworld.utils.Launcher;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static fr.pixelmonworld.utils.ResourcesUtils.getResource;
import static fr.pixelmonworld.utils.ResourcesUtils.getResourceAsStream;

/**
 * Panneau permettant d'afficher le pré-lancement du launcher.
 */
public class PreLauncherPanel extends DefaultLauncherPanel {

    // JLabel contenant le texte à afficher
    private JLabel text;

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param width La largeur du panneau.
     * @param height La hauteur du panneau.
     */
    public PreLauncherPanel(Component parent, int width, int height) {
        super(parent, width, height, (parent.getWidth() - width) / 2, (parent.getHeight() - height) / 2);

        Launcher.setPreLauncherPanel(this);

        Font robotoBold = null;
        try {
            robotoBold = Font.createFont(Font.TRUETYPE_FONT, getResourceAsStream("fonts/Roboto-Bold.ttf")).deriveFont(20f);
        } catch (FontFormatException | IOException e) {
            Launcher.erreurInterne(e);
        }

        // Ajout d'un JLabel visible
        text = genererTexte(this.getHeight() - 25, "", 25);
        text.setFont(robotoBold);
        text.setBackground(new Color(24, 71, 8));
        text.setForeground(new Color(186, 255, 143));
        text.setOpaque(true);
        this.add(text);

        ImageIcon serverIconIcon = new ImageIcon(getResource("prelauncher_panel/server_logo.png"));
        OpacityJLabel loadingLabel = new OpacityJLabel(serverIconIcon);
        loadingLabel.setBounds((this.getWidth() / 2) - (serverIconIcon.getIconWidth() / 2), (this.getHeight() / 2) - (serverIconIcon.getIconHeight() / 2) - 20, serverIconIcon.getIconWidth(), serverIconIcon.getIconHeight());
        this.add(loadingLabel, 0);

        this.setOpaque(true);
    }

    /**
     * Met à jour le texte du panneau.
     * @param message Le texte à afficher.
     */
    public void updateText(String message) {
        text.setText(message);
    }
}

package fr.pixelmonworld.prelauncher;

import fr.pixelmonworld.Prelauncher;
import fr.pixelmonworld.domain.DefaultLauncherPanel;
import fr.pixelmonworld.domain.JLabel;
import fr.pixelmonworld.utils.ResourcesUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * Panneau permettant d'afficher le pré-lancement du launcher.
 */
public class PrelauncherPanel extends DefaultLauncherPanel {

    // JLabel contenant le texte à afficher
    private javax.swing.JLabel text;

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param width La largeur du panneau.
     * @param height La hauteur du panneau.
     */
    public PrelauncherPanel(Component parent, int width, int height) {
        super(parent, width, height, (parent.getWidth() - width) / 2, (parent.getHeight() - height) / 2);

        Font robotoBold = null;
        try {
            robotoBold = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(ResourcesUtils.getResourceAsStream("utils/fonts/Roboto-Bold.ttf"))).deriveFont(20f);
        } catch (FontFormatException | IOException e) {
            Prelauncher.erreurInterne(e);
        }

        // Ajout d'un JLabel visible
        text = genererTexte(this.getHeight() - 25, "", 25);
        text.setFont(robotoBold);
        text.setBackground(new Color(24, 71, 8));
        text.setForeground(new Color(186, 255, 143));
        text.setOpaque(true);
        this.add(text);

        ImageIcon serverLogoIcon = new ImageIcon(Objects.requireNonNull(ResourcesUtils.getResource("prelauncher/logo.png")));
        JLabel serverLogo = new JLabel(serverLogoIcon);
        serverLogo.setBounds((this.getWidth() / 2) - (serverLogoIcon.getIconWidth() / 2), (this.getHeight() / 2) - (serverLogoIcon.getIconHeight() / 2) - 20, serverLogoIcon.getIconWidth(), serverLogoIcon.getIconHeight());
        this.add(serverLogo, 0);

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

package fr.pixelmonworld.prelauncher;

import fr.pixelmonworld.Prelauncher;
import fr.pixelmonworld.domain.DefaultLauncherPanel;
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
     */
    public PrelauncherPanel(Component parent, int width, int height) {
        super(parent, width, height, (parent.getWidth() - width) / 2, (parent.getHeight() - height) / 2);
        this.setOpaque(false);

        Font robotoBold = null;
        try {
            robotoBold = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(ResourcesUtils.getResourceAsStream("utils/fonts/Roboto-Bold.ttf"))).deriveFont(25f);
        } catch (FontFormatException | IOException e) {
            Prelauncher.erreurInterne(e);
        }

        ImageIcon serverLogoIcon = new ImageIcon(Objects.requireNonNull(ResourcesUtils.getResource("prelauncher/logo.png")));
        JLabel serverLogo = new JLabel(serverLogoIcon);
        serverLogo.setHorizontalAlignment(SwingConstants.CENTER);
        serverLogo.setVerticalAlignment(SwingConstants.CENTER);
        serverLogo.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        this.add(serverLogo);

        // Création d'un JLabel personnalisé avec contour
        text = new JLabel("") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Configuration du texte
                String txt = getText();
                Font font = getFont();
                g2.setFont(font);
                FontMetrics metrics = g2.getFontMetrics();

                // Calcul des coordonnées pour le centrage
                int x = (getWidth() - metrics.stringWidth(txt)) / 2; // Centrage horizontal
                int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent(); // Centrage vertical

                // Dessin du contour
                g2.setColor(new Color(24, 71, 8)); // Couleur du contour
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (dx != 0 || dy != 0) {
                            g2.drawString(txt, x + dx, y + dy);
                        }
                    }
                }

                // Dessin du texte principal (couleur originale)
                g2.setColor(getForeground());
                g2.drawString(txt, x, y);

                g2.dispose();
            }
        };

        // Configuration du JLabel (inchangée)
        text.setBounds(0, (parent.getHeight() / 2) + (serverLogoIcon.getIconHeight() / 2), parent.getWidth(), 30);
        text.setFont(robotoBold);
        text.setForeground(new Color(186, 255, 143)); // Couleur d'origine préservée
        text.setOpaque(false);
        this.add(text);
    }

    /**
     * Met à jour le texte du panneau.
     * @param message Le texte à afficher.
     */
    public void updateText(String message) {
        text.setText(message);
    }
}

package fr.pixelmonworld.panels.main;

import fr.pixelmonworld.domain.DefaultLauncherPanel;
import fr.pixelmonworld.domain.TypeMessage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;

/**
 * Panneau permettant d'afficher des informations dans un pop-up.
 */
public class InfosPanel extends DefaultLauncherPanel {

    private final ImageIcon backgroundIcon = new ImageIcon(Objects.requireNonNull(getBufferedImage("other/infos_panel_background.png")));
    private JLabel logsLabel;

    public InfosPanel(Component parent, int width, int height, int x, int y, TypeMessage message) throws IOException {
        super(parent, width, height, x - width / 2, y - height / 2);
        this.setLayout(null);

        // Ajout de l'image
        ImageIcon image = new ImageIcon(getBufferedImage(message.getNomFichier()));
        JLabel imageLabel = genererImage(y / 3 - image.getIconHeight(), image);
        this.add(imageLabel);

        // Ajout d'un JLabel visible
        logsLabel = genererTexte(y / 2, "", 20);
        this.add(logsLabel);

        // Ajout du background
        JLabel background = new JLabel(backgroundIcon);
        background.setBounds(0, 0, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
        this.add(background);
    }

    /**
     * Met à jour le texte affiché par le JLabel visible.
     * @param newText Le nouveau texte à afficher.
     */
    public void updateText(String newText) {
        logsLabel.setText(newText);
    }
}

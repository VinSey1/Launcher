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

    private final ImageIcon backgroundIcon = new ImageIcon(Objects.requireNonNull(getBufferedImage("infos_panel_background.png")));

    // TODO A mettre à jour de manière propre avec la MAJ des assets
    public InfosPanel(Component parent, int width, int height, int x, int y, TypeMessage message) throws IOException {
        super(parent, width, height, x - width / 2, y - height / 2);
        this.setLayout(new BorderLayout());

        ImageIcon image = new ImageIcon(getBufferedImage(message.getNomFichier()));
        this.add(genererImage(y / 3 - image.getIconHeight(), image));

        // Ajout du background
        JLabel background = new JLabel(backgroundIcon);
        background.setBounds(0, 0, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
        this.add(background);
    }
}

package fr.pixelmonworld.panels.main;

import fr.pixelmonworld.domain.DefaultLauncherPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;
import static fr.pixelmonworld.utils.ResourcesUtils.getResource;

public class LoadingScreen extends DefaultLauncherPanel {

    private final ImageIcon backgroundIcon = new ImageIcon(Objects.requireNonNull(getBufferedImage("loading_screen.png")));
    private JLabel logsLabel;

    /**
     * Constructeur par défaut.
     * @param parent Le composant parent.
     * @throws IOException Si une erreur survient lors de la lecture de l'image.
     */
    public LoadingScreen(Component parent) throws IOException {
        super(parent.getWidth(), parent.getHeight());

        // Ajout d'un JLabel visible
        logsLabel = genererTexte(parent.getHeight() / 2, "", 20);
        this.add(logsLabel);

        // Ajout d'une image
        ImageIcon image = new ImageIcon(getResource("loading.gif"));
        JLabel imageLabel = genererImage(parent.getHeight() / 2 - image.getIconHeight() - logsLabel.getFont().getSize(), image);
        this.setDoubleBuffered(true);
        this.add(imageLabel);

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

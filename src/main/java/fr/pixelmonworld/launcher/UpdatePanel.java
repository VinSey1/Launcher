package fr.pixelmonworld.launcher;

import fr.pixelmonworld.utils.Launcher;
import fr.pixelmonworld.domain.DefaultLauncherPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static fr.pixelmonworld.utils.ResourcesUtils.getResource;
import static fr.pixelmonworld.utils.ResourcesUtils.getResourceAsStream;

/**
 * Panneau permettant d'afficher des informations dans un pop-up.
 */
public class UpdatePanel extends DefaultLauncherPanel {

    // JLabel contenant le texte à afficher
    private JLabel text;
    // Barre de progression de la mise à jour
    private JProgressBar progressBar;
    // Valeur maximale de la barre de progression lorsqu'il y a des mods à installer
    private int maximumValueWithMods = 2658;
    // Valeur maximale de la barre de progression lorsqu'il n'y a pas de mods à installer
    private int maximumValueWithoutMods = 10;

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param width La largeur du panneau.
     * @param height La hauteur du panneau.
     */
    public UpdatePanel(Component parent, int width, int height) {
        super(parent, width, height, (parent.getWidth() - width) / 2, (parent.getHeight() - height) / 2);

        Font robotoBlack = null;
        Font robotoBold = null;
        try {
            robotoBlack = Font.createFont(Font.TRUETYPE_FONT, getResourceAsStream("fonts/Roboto-Black.ttf")).deriveFont(20f);
            robotoBold = Font.createFont(Font.TRUETYPE_FONT, getResourceAsStream("fonts/Roboto-Bold.ttf")).deriveFont(12f);
        } catch (FontFormatException | IOException e) {
            Launcher.erreurInterne(e);
        }

        // Ajout d'un Titre
        JLabel titre = genererTexte(this.getHeight() / 6, "Mise à jour de Minecraft", 20);
        titre.setFont(robotoBlack);
        this.add(titre);

        // Ajout d'un JLabel visible
        text = genererTexte((this.getHeight() - this.getHeight() / 8) - 10, "", 12);
        text.setFont(robotoBold);
        this.add(text);

        // Ajout de la barre de progression
        progressBar = new JProgressBar();
        progressBar.setBounds((this.getWidth() - (this.getWidth() - 50)) / 2, this.getHeight() - 35, this.getWidth() - 50, 20);
        progressBar.setMaximum(maximumValueWithoutMods);
        progressBar.setMinimum(0);
        progressBar.setBorderPainted(false);
        progressBar.setBackground(new Color(78, 76, 78));
        progressBar.setForeground(new Color(237, 236, 237));

        this.add(progressBar);

        ImageIcon loadingGifIcon = new ImageIcon(getResource("other/update_loading.gif"));
        JLabel loadingGif = genererImage(this.getHeight() / 2, loadingGifIcon);
        loadingGif.setDoubleBuffered(true);
        this.add(loadingGif, 0);

        this.setOpaque(true);
        this.setBackground(new Color(23, 22, 23));
    }

    /**
     * Met à jour le texte du panneau.
     * @param text Le texte à afficher.
     */
    public void updateTextAndValue(String text, int value) {
        if (value == 5 && !text.contains("Checking assets..."))  {
            progressBar.setMaximum(maximumValueWithMods);
        }
        this.text.setText(text);
        this.progressBar.setValue(value);
    }

    /**
     * Met à jour le titre du panneau.
     * @param title Le titre à afficher.
     */
    public void updateTitle(String title) {
        this.text.setText(title);
    }
}

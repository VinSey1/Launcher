package fr.pixelmonworld.launcher.popup_panel;

import fr.pixelmonworld.domain.DefaultLauncherPanel;
import fr.pixelmonworld.domain.OpacityJLabel;
import fr.pixelmonworld.utils.Launcher;
import org.pushingpixels.radiance.animation.api.Timeline;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static fr.pixelmonworld.utils.ResourcesUtils.*;

/**
 * Panneau permettant d'afficher des informations dans un pop-up.
 */
public class PopupPanel extends DefaultLauncherPanel {

    // Structure de la fenêtre
    private ImageIcon backgroundIcon = new ImageIcon(Objects.requireNonNull(getBufferedImage("popup_panel/background.png")));

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
    public PopupPanel(Component parent, int width, int height) {
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
        titre.setForeground(new Color(186, 255, 143));
        this.add(titre);

        // Ajout d'un JLabel visible
        text = genererTexte((this.getHeight() - this.getHeight() / 8) - 10, "", 12);
        text.setFont(robotoBold);
        text.setForeground(new Color(186, 255, 143));
        this.add(text);

        // Ajout de la barre de progression
        progressBar = new JProgressBar();
        progressBar.setBounds((this.getWidth() - (this.getWidth() - 50)) / 2, this.getHeight() - 35, this.getWidth() - 50, 20);
        progressBar.setMaximum(maximumValueWithoutMods);
        progressBar.setMinimum(0);
        progressBar.setBorderPainted(false);
        progressBar.setBackground(new Color(24, 71, 8));
        progressBar.setForeground(new Color(186, 255, 143));
        this.add(progressBar);

        ImageIcon loadingIcon = new ImageIcon(getResource("popup_panel/icon.png"));
        OpacityJLabel loadingLabel = new OpacityJLabel(loadingIcon);
        loadingLabel.setBounds((this.getWidth() / 2) - (loadingIcon.getIconWidth() / 2), (this.getHeight() / 2) - (loadingIcon.getIconHeight() / 2) - 20, loadingIcon.getIconWidth(), loadingIcon.getIconHeight());
        this.add(loadingLabel, 0);

        // Permet de changer le render toutes les 10 secondes
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Timeline.builder(loadingLabel)
                            .addPropertyToInterpolate("opacity", 0.0f, 1.0f)
                            .setDuration(1000)
                            .play();
                    Thread.sleep(1000);
                    Timeline.builder(loadingLabel)
                            .addPropertyToInterpolate("opacity", 1.0f, 0.0f)
                            .setDuration(1000)
                            .play();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        JLabel background = new JLabel(backgroundIcon);
        background.setBounds(0, 0, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
        this.add(background);
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

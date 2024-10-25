package fr.pixelmonworld.panels.main;

import fr.pixelmonworld.Launcher;
import fr.pixelmonworld.domain.DefaultLauncherPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static fr.pixelmonworld.utils.ResourcesUtils.getResource;
import static fr.pixelmonworld.utils.ResourcesUtils.getResourceAsStream;

/**
 * Panneau permettant d'afficher des informations dans un pop-up.
 */
public class PreLauncherPanel extends DefaultLauncherPanel {

//    private final ImageIcon backgroundIcon = new ImageIcon(Objects.requireNonNull(getBufferedImage("other/infos_panel_background.png")));
    private JLabel text;
    private JProgressBar progressBar;

    public PreLauncherPanel(Component parent, int width, int height) {
        super(parent, width, height, (parent.getWidth() - width) / 2, (parent.getHeight() - height) / 2);

        Launcher.setPreLauncherPanel(this);

        Font robotoBlack = null;
        Font robotoBold = null;
        try {
            robotoBlack = Font.createFont(Font.TRUETYPE_FONT, getResourceAsStream("fonts/Roboto-black.ttf"));
            robotoBold = Font.createFont(Font.TRUETYPE_FONT, getResourceAsStream("fonts/Roboto-Bold.ttf"));
        } catch (FontFormatException | IOException e) {
            Launcher.erreurInterne(e);
        }

        // Ajout d'un Titre
        JLabel titre = genererTexte(this.getHeight() / 5, "PW", 50);
        titre.setFont(robotoBlack.deriveFont(50f));
        this.add(titre);


        // Ajout d'un JLabel visible
        text = genererTexte(this.getHeight() - this.getHeight() / 10, "", 10);
        text.setFont(robotoBold.deriveFont(12f));
        this.add(text);

        // Ajout de la barre de progression
        progressBar = new JProgressBar();
        progressBar.setBounds((this.getWidth() - (this.getWidth() - 50)) / 2, this.getHeight() - 35, this.getWidth() - 50, 20);
        progressBar.setMaximum(100);
        progressBar.setMinimum(0);
        progressBar.setBorderPainted(false);
        progressBar.setBackground(new Color(78, 76, 78));
        progressBar.setForeground(new Color(237, 236, 237));

        this.add(progressBar);

        ImageIcon preLauncherGifIcon = new ImageIcon(getResource("other/prelauncher_loading2.gif"));
        JLabel preLauncherGif = genererImage(this.getHeight() - 120, preLauncherGifIcon);
        preLauncherGif.setDoubleBuffered(true);
        this.add(preLauncherGif, 0);

//        // Ajout du background
//        JLabel background = new JLabel(backgroundIcon);
//        background.setBounds(0, 0, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
//        this.add(background);
        this.setOpaque(true);
        this.setBackground(new Color(23, 22, 23));

        Launcher.init();
    }

    /**
     * Met à jour le texte du panneau.
     * @param text Le texte à afficher.
     */
    public void updateTextAndValue(String text, int value) {
        this.text.setText(text);
        this.progressBar.setValue(value);
    }
}

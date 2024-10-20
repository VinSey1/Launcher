package fr.pixelmonworld.panels.main;

import fr.pixelmonworld.Launcher;
import fr.pixelmonworld.domain.DefaultLauncherPanel;
import fr.pixelmonworld.panels.buttons.ButtonsPanel;
import fr.pixelmonworld.panels.connexion.ConnexionPanel;
import fr.pixelmonworld.panels.ram.RamPanel;

import javax.swing.*;
import java.io.IOException;
import java.util.Objects;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;
import static fr.pixelmonworld.utils.ResourcesUtils.getRandomRenderImage;

/**
 * Panneau principal de l'application servant de parent pour le reste des éléments spécifiques.
 */
public class MainPanel extends DefaultLauncherPanel {

    // Structure de la fenêtre
    private ImageIcon backgroundIcon = new ImageIcon(Objects.requireNonNull(getBufferedImage("background.png")));
    // Icône du launcher
    private ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(getBufferedImage("server_logo.png")));
    // Image ingame du serveur
    private ImageIcon renderIcon = new ImageIcon(getRandomRenderImage());

    /**
     * Constructeur par défaut.
     * @param width La largeur du panneau.
     * @param height La hauteur du panneau.
     * @throws IOException Problème lors d'une mise à jour graphique.
     */
    public MainPanel(int width, int height) throws IOException {
        super(width, height);

        // Permet l'affichage de la fenêtre d'information
        Launcher.setLauncherPanel(this);

        // Ajout du logo
        JLabel logo = new JLabel(logoIcon);
        logo.setBounds(width / 2 - logoIcon.getIconWidth() / 2, 0, logoIcon.getIconWidth(), logoIcon.getIconHeight());
        this.add(logo);

        // Ajout du panel avec l'ensemble des boutons
        this.add(new ButtonsPanel(this, 483, 702, 1408, 249));

        // Ajout du panel de connexion
        this.add(new ConnexionPanel(this, 483, 271, 1408, 976));

        // Ajout du panel de ram
        this.add(new RamPanel(this, 954, 165, 257, 1100));

        // Ajout du bouton de fermeture
        this.add(new CloseButton(this, 220));

        // Ajout de la structure de la fenêtre
        JLabel background = new JLabel(backgroundIcon);
        background.setBounds(0, 0, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
        this.add(background);

        // Ajout du screen ingame
        JLabel render = new JLabel(renderIcon);
        render.setBounds(-81, 215, renderIcon.getIconWidth(), renderIcon.getIconHeight());
        this.add(render);
    }
}

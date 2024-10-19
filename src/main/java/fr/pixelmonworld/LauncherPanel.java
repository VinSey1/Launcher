package fr.pixelmonworld;

import fr.pixelmonworld.panels.buttons.ButtonsPanel;
import fr.pixelmonworld.panels.connexion.ConnexionPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static fr.pixelmonworld.utils.ImagesSelector.getBufferedImage;
import static fr.pixelmonworld.utils.ImagesSelector.getRandomRenderImage;

public class LauncherPanel extends JPanel {

    private BufferedImage backgroundImage = getBufferedImage("background.png");
    private BufferedImage logoImage = getBufferedImage("server_logo.png");
    private BufferedImage renderImage = getRandomRenderImage();

    public LauncherPanel() throws IOException {
        this.setLayout(null);
        this.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));

        // Ajout du logo
        JLabel logo = new JLabel(new ImageIcon(logoImage));
        logo.setBounds(560, 0, 831, 477);
        this.add(logo);

        JPanel buttonsPanel = new ButtonsPanel(483, 702, 1408, 249);
        this.add(buttonsPanel);

        JPanel connexionPanel = new ConnexionPanel(483, 271, 1408, 976, this);
        this.add(connexionPanel);


        // Ajout du background
        JLabel background = new JLabel(new ImageIcon(backgroundImage));
        background.setBounds(0, 0, 1920, 1290);
        this.add(background);

        JLabel render = new JLabel(new ImageIcon(renderImage));
        render.setBounds(-81, 215, 1783, 1080);
        this.add(render);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(null, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}

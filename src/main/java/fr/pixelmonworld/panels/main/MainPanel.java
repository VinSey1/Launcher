package fr.pixelmonworld.panels.main;

import fr.pixelmonworld.Launcher;
import fr.pixelmonworld.panels.buttons.ButtonsPanel;
import fr.pixelmonworld.panels.connexion.ConnexionPanel;
import fr.pixelmonworld.panels.ram.RamPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static fr.pixelmonworld.utils.ImagesSelector.getBufferedImage;
import static fr.pixelmonworld.utils.ImagesSelector.getRandomRenderImage;

public class MainPanel extends JPanel {

    private ImageIcon backgroundIcon = new ImageIcon(Objects.requireNonNull(getBufferedImage("background.png")));
    private ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(getBufferedImage("server_logo.png")));
    private ImageIcon renderIcon = new ImageIcon(getRandomRenderImage());
    private ImageIcon closeButtonIcon = new ImageIcon(Objects.requireNonNull(getBufferedImage("close_button.png")));


    public MainPanel(int width, int height) throws IOException {
        this.setLayout(null);
        this.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
        this.setSize(width, height);

        Launcher.setLauncherPanel(this);

        // Ajout du logo
        JLabel logo = new JLabel(logoIcon);
        logo.setBounds(width / 2 - logoIcon.getIconWidth() / 2, 0, logoIcon.getIconWidth(), logoIcon.getIconHeight());
        this.add(logo);

        this.add(new ButtonsPanel(this, 483, 702, 1408, 249));

        this.add(new CloseButton(this, 220));

        this.add(new ConnexionPanel(this, 483, 271, 1408, 976));

        this.add(new RamPanel(this, 954, 165, 257, 1100));

        // Ajout du background
        JLabel background = new JLabel(backgroundIcon);
        background.setBounds(0, 0, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
        this.add(background);

        JLabel render = new JLabel(renderIcon);
        render.setBounds(-81, 215, renderIcon.getIconWidth(), renderIcon.getIconHeight());
        this.add(render);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(null, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}

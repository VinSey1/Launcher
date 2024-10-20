package fr.pixelmonworld.panels.main;

import fr.pixelmonworld.domain.DefaultLauncherPanel;
import fr.pixelmonworld.domain.TypeMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static fr.pixelmonworld.utils.ImagesSelector.getBufferedImage;

public class InfosPanel extends DefaultLauncherPanel {

    private BufferedImage errorTextImage = getBufferedImage("error_text.png");
    private BufferedImage defaultTextImage = getBufferedImage("discord_logo.png");
    private final ImageIcon backgroundIcon = new ImageIcon(Objects.requireNonNull(getBufferedImage("infos_panel_background.png")));

    public InfosPanel(Component parent, int width, int height, int x, int y, TypeMessage message) throws IOException {
        super(parent, width, height, x - width / 2, y - height / 2);
        this.setLayout(new BorderLayout());

//
//        ImageIcon image;
//        switch (message) {
//            case UPDATE_MINECRAFT -> image = new ImageIcon(errorTextImage);
//            default -> image = new ImageIcon(defaultTextImage);
//        }
//        this.add(genererImage(y / 2 - image.getIconHeight(), image));

        JLabel test = new JLabel("tezotze", JLabel.CENTER);
        test.setBackground(Color.GREEN);
        this.add(test, BorderLayout.CENTER, 0);

        // Ajout du background
        JLabel background = new JLabel(backgroundIcon);
        background.setBounds(0, 0, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
        this.add(background);
    }
}

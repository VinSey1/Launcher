package fr.pixelmonworld.domain;

import javax.swing.*;
import java.awt.*;

public class DefaultLauncherPanel extends JPanel {
    public DefaultLauncherPanel(int width, int height, int x, int y) {
        this.setLayout(null);
        this.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
        this.setBounds(x, y, width, height);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(null, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    protected JLabel genererImage(int y, int width, int height, ImageIcon image) {
        JLabel result = new JLabel(image);
        result.setBounds(this.getWidth() / 2 - width / 2, y - height / 2, width, height);
        return result;
    }
}

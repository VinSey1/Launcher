package fr.pixelmonworld.panels.news;

import javax.swing.*;
import java.awt.*;

import static fr.pixelmonworld.utils.ResourcesUtils.getResource;

public class NewsAlert extends JLabel {

    Component parent;

    public NewsAlert(Component parent, int x, int y) {
        super(new ImageIcon(getResource("news_alert.gif")));
        this.parent = parent;
        this.setBounds(x, y, this.getIcon().getIconWidth(), this.getIcon().getIconHeight());
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void repaint() {
        super.repaint();
        if (this.parent != null) {
            this.parent.repaint();
        }
    }
}

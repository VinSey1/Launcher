package fr.pixelmonworld.panels.news;

import fr.pixelmonworld.Launcher;
import fr.pixelmonworld.domain.News;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;

import static fr.pixelmonworld.utils.ResourcesUtils.getResourceAsStream;

public class LabelNews extends JLabel {
    public LabelNews(Component parent, int y, int fontSize, News news) throws IOException, FontFormatException {
        super(news.toMessage());
        this.setBounds((parent.getWidth() - (parent.getWidth() - 20)) / 2, y - 50 / 2, parent.getWidth() - 20, fontSize + 2);
        this.setHorizontalAlignment(SwingConstants.LEFT);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setForeground(Color.WHITE);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.setFont(Font.createFont(Font.TRUETYPE_FONT, getResourceAsStream("fonts/Roboto-Bold.ttf")).deriveFont(Float.parseFloat(fontSize + "f")));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                try {
                    Desktop.getDesktop().browse(news.getUrl().toURI());
                } catch (IOException | URISyntaxException e) {
                    Launcher.erreurInterne(e);
                }
            }
        });
    }
}

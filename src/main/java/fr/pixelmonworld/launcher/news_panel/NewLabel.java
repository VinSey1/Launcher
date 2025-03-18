package fr.pixelmonworld.launcher.news_panel;

import fr.pixelmonworld.domain.News;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

import static fr.pixelmonworld.utils.ResourcesUtils.getResourceAsStream;

public class NewLabel extends JLabel {

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param news L'actualité à afficher.
     */
    public NewLabel(Component parent, News news) throws IOException, FontFormatException {
        super(news.toMessage());
        this.setBounds(0, 0, parent.getWidth(), parent.getHeight());

        Border border = this.getBorder();
        Border margin = new EmptyBorder(2,20,10,10);
        this.setBorder(new CompoundBorder(border, margin));

        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.CENTER);
        this.setFont(Font.createFont(Font.TRUETYPE_FONT, getResourceAsStream("fonts/AkzidenzGroteskBQ-MedExt.ttf")).deriveFont(18f));
        this.setForeground(new Color(24, 71, 8));
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}

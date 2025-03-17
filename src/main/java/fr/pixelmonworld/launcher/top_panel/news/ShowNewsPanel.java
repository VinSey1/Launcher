package fr.pixelmonworld.launcher.top_panel.news;

import fr.pixelmonworld.utils.Launcher;
import fr.pixelmonworld.domain.DefaultLauncherPanel;
import fr.pixelmonworld.domain.News;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Collection;

import static fr.pixelmonworld.utils.ResourcesUtils.getResourceAsStream;

/**
 * Panneau permettant d'afficher des informations dans un pop-up.
 */
public class ShowNewsPanel extends DefaultLauncherPanel {

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param width La largeur du panneau.
     * @param height La hauteur du panneau.
     * @param news Les actualités à afficher.
     */
    public ShowNewsPanel(Component parent, int width, int height, Collection<News> news) {
        super(parent, width, height, (parent.getWidth() - width) / 2, (parent.getHeight() - height) / 2);

        Font robotoBlack = null;
        try {
            robotoBlack = Font.createFont(Font.TRUETYPE_FONT, getResourceAsStream("fonts/Roboto-Black.ttf")).deriveFont(20f);
            for (int i = 0; i < news.size(); i++) {
                News n = (News) news.toArray()[i];
                LabelNews newsJLabel = new LabelNews(this, this.getHeight() / 6 + 50 + (i * 50), 15, n);
                this.add(newsJLabel);
            }
        } catch (FontFormatException | IOException e) {
            Launcher.erreurInterne(e);
        }

        // Ajout d'un Titre
        JLabel titre = genererTexte(this.getHeight() / 6 + 5, "Actualités", 20);
        titre.setFont(robotoBlack);
        this.add(titre);

        this.setOpaque(true);
        this.setBackground(new Color(23, 22, 23));
    }
}

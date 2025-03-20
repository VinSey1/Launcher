package fr.pixelmonworld.launcher.news_panel;

import fr.pixelmonworld.domain.DefaultLauncherButton;
import fr.pixelmonworld.domain.News;
import fr.pixelmonworld.utils.Launcher;
import fr.theshark34.swinger.event.SwingerEvent;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;

/**
 * Permet d'afficher une actualité.
 */
public class NewPanel extends DefaultLauncherButton {

    // Structure de la fenêtre
    private ImageIcon backgroundIcon = new ImageIcon(Objects.requireNonNull(getBufferedImage("news_panel/new_background.png")));

    private News news;

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param y Les coordonnées Y du panneau.
     * @param news L'actualité à afficher.
     */
    public NewPanel(Component parent, int y, News news) throws IOException, FontFormatException {
        super(parent, 0, getBufferedImage("news_panel/new_background.png"));
        this.setBounds((parent.getWidth() / 2) - (backgroundIcon.getIconWidth() / 2) - 5, y);

        this.news = news;

        this.add(new NewLabel(this, news));
    }

    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        try {
            Desktop.getDesktop().browse(news.getUrl().toURI());
        } catch (IOException | URISyntaxException err) {
            Launcher.erreurInterne(err);
        }
    }
}

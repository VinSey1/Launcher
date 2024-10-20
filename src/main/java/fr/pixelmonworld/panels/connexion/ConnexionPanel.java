package fr.pixelmonworld.panels.connexion;

import fr.pixelmonworld.LauncherPanel;
import fr.pixelmonworld.domain.DefaultLauncherPanel;

import java.io.IOException;

public class ConnexionPanel extends DefaultLauncherPanel {

    ConnexionButton connexionButton;
    LauncherPanel parent;

    public ConnexionPanel(int width, int height, int x, int y, LauncherPanel parent) throws IOException {
        super(width, height, x, y);

        this.parent = parent;

        // Ajout du bouton de connexion
        this.add(connexionButton = new ConnexionButton(this.getWidth(), this.getHeight() / 2, this));
    }

    @Override
    public void repaint() {
        super.repaint();
        if (this.parent != null) this.parent.repaint();
    }
}

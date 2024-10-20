package fr.pixelmonworld.panels.connexion;

import fr.pixelmonworld.domain.DefaultLauncherPanel;

import java.awt.*;
import java.io.IOException;

public class ConnexionPanel extends DefaultLauncherPanel {

    ConnexionButton connexionButton;

    public ConnexionPanel(Component parent, int width, int height, int x, int y) throws IOException {
        super(parent, width, height, x, y);

        // Ajout du bouton de connexion
        this.add(connexionButton = new ConnexionButton(this, this.getHeight() / 2));
    }
}

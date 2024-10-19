package fr.pixelmonworld.utils;

import fr.pixelmonworld.Launcher;
import fr.pixelmonworld.panels.connexion.ConnexionButton;

import java.io.IOException;

public class MicrosoftThread implements Runnable {

    ConnexionButton parent;

    public MicrosoftThread(ConnexionButton parent) {
        this.parent = parent;
    }

    @Override
    public void run() {
        Launcher.auth();
        try {
            parent.update();
        } catch (IOException e) {
            Launcher.erreurInterne(e);
        }
    }
}

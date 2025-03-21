package fr.pixelmonworld.launcher.connexion_panel.threads;

import fr.pixelmonworld.launcher.connexion_panel.buttons.ConnexionButton;
import fr.pixelmonworld.utils.Launcher;

/**
 * Thread permettant de se connecter à Microsoft.
 */
public class MicrosoftThread implements Runnable {

    // Le parent à appeler pour signifier que l'utilisateur est connecté
    ConnexionButton parent;

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler lorsque l'utilisateur est connecté.
     */
    public MicrosoftThread(ConnexionButton parent) {
        this.parent = parent;
    }

    /**
     * Permet de lancer la connexion à Microsoft.
     */
    @Override
    public void run() {
        Launcher.auth();
        parent.updateAuthStatus();
        parent.setClicked(false);
    }
}

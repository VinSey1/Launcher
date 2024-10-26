package fr.pixelmonworld.panels.connexion.threads;

import fr.pixelmonworld.Launcher;
import fr.pixelmonworld.panels.connexion.ConnexionButton;

/**
 * Thread permettant de mettre à jour et de lancer Minecraft.
 */
public class MinecraftThread implements Runnable {

    // Le parent à appeler pour signifier que le bouton est redisponible ou non
    ConnexionButton parent;

    /**
     * Constructeur par défaut.
     * @param parent Le parent à appeler lorsque le bouton est redisponible.
     */
    public MinecraftThread(ConnexionButton parent) {
        this.parent = parent;
    }

    /**
     * Permet de lancer la mise à jour de Minecraft et de le lancer ensuite.
     */
    @Override
    public void run() {
        Launcher.update();
//        Launcher.launch();
        parent.setClicked(false);
    }
}

package fr.pixelmonworld.panels.connexion.threads;

import fr.pixelmonworld.Launcher;

/**
 * Thread permettant de mettre à jour et de lancer Minecraft.
 */
public class MinecraftThread implements Runnable {

    /**
     * Permet de lancer la mise à jour de Minecraft et de le lancer ensuite.
     */
    @Override
    public void run() {
        Launcher.update();
        Launcher.launch();
    }
}

package fr.pixelmonworld.utils;

import fr.pixelmonworld.Launcher;

public class MinecraftThread implements Runnable {

    @Override
    public void run() {
        Launcher.update();
        Launcher.launch();
    }
}

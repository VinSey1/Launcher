package fr.pixelmonworld.utils;

import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.pixelmonworld.Launcher;

public class MicrosoftThread implements Runnable {
    @Override
    public void run() {
        try {
            Launcher.auth();
        } catch (MicrosoftAuthenticationException e) {
            Launcher.getReporter().catchError(e, "Impossible de se connecter.");
        }
    }
}

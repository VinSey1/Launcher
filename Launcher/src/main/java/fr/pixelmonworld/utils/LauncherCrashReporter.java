package fr.pixelmonworld.utils;

import fr.theshark34.openlauncherlib.util.CrashReporter;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;

/**
 * Permet de gérer les crashs du launcher. Etend la classe "CrashReporter" de OpenLauncherLib pour pouvoir gérer le tout
 * de manière spécifique.
 */
public class LauncherCrashReporter extends CrashReporter {

    /**
     * Constructeur par défaut.
     * @param name Le nom du fichier de log.
     * @param dir Le dossier où écrire le fichier de log.
     */
    public LauncherCrashReporter(String name, Path dir) {
        super(name, dir);
    }

    /**
     * Permet de gérer une erreur et de quitter le programme.
     * @param e L'exception à gérer.
     * @param message Le message à afficher.
     */
    @Override
    public void catchError(Exception e, String message) {
        String msg;
        try {
            msg = "Log du crash : " + this.writeError(e).toString();
        } catch (IOException err) {
            msg = "Impossible de créer le log de crash : " + err;
        }
        JOptionPane.showMessageDialog(null, e + "\n" + msg, "Erreur interne :", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Objects.requireNonNull(getBufferedImage("launcher/popup_panel/icon.png"))));
        System.exit(1);
    }
}

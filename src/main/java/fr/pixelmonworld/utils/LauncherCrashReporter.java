package fr.pixelmonworld.utils;

import fr.theshark34.openlauncherlib.util.CrashReporter;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;

public class LauncherCrashReporter extends CrashReporter {
    public LauncherCrashReporter(String name, Path dir) {
        super(name, dir);
    }

    @Override
    public void catchError(Exception e, String message) {
        String msg;
        try {
            msg = "Log du crash : " + this.writeError(e).toString();
        } catch (IOException var5) {
            msg = "Impossible de créer le log de crash : " + var5;
        }
        JOptionPane.showMessageDialog(null, message + "\n\n" + e + "\n\n" + msg, "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}

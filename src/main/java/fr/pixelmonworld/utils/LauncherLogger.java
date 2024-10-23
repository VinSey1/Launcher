package fr.pixelmonworld.utils;

import fr.flowarg.flowlogger.Logger;
import fr.pixelmonworld.panels.main.MainPanel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Classe qui étend la classe "Logger" pour gérer les logs du lanceur.
 * Cette classe permet de mettre à jour dynamiquement le panneau d'information avec les messages de log de l'updater.
 */
public class LauncherLogger extends Logger {
    private final MainPanel mainPanel;

    /**
     * Constructeur par défaut.
     * @param mainPanel Le panneau d'informations à mettre à jour avec les messages de log.
     */
    public LauncherLogger(MainPanel mainPanel) {
        super("", null);
        this.mainPanel = mainPanel;
    }

    /**
     * Écrit un message dans le fichier de log.
     * @param toLog Le message à écrire dans le fichier de log.
     */
    @Override
    public void writeToTheLogFile(String toLog) {
        mainPanel.updateLog(toLog);
    }

    /**
     * Affiche un message dans la console et l'écrit dans le fichier de log.
     * @param err Indique si le message est une erreur.
     * @param toWrite Le message à afficher et à écrire dans le fichier de log.
     */
    @Override
    public void message(boolean err, String toWrite) {
        String date = String.format("[%s] ", (new SimpleDateFormat("HH:mm:ss", Locale.getDefault())).format(new Date()));
        String msg = date + " " + toWrite;
        if (err) {
            System.out.println(EnumLogColor.RED + msg + EnumLogColor.RESET);
        } else {
            System.out.println(msg);
        }
        this.writeToTheLogFile(msg);
    }
}
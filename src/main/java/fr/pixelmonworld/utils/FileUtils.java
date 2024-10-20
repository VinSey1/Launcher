package fr.pixelmonworld.utils;

import java.io.*;

/**
 * Utilitaire concernant les fichiers.
 */
public class FileUtils {

    /**
     * Permet de copier le contenu d'un fichier vers un autre.
     * @param source Le fichier à copier.
     * @param dest La destination.
     * @throws IOException Problème lors de la lecture / de l'écriture des fichiers.
     */
    public static void copyFile(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }
}

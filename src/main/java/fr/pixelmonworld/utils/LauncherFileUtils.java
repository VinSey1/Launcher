package fr.pixelmonworld.utils;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utilitaire concernant les fichiers.
 */
public class LauncherFileUtils {

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

    /**
     * Permet de calculer le SHA-1 d'un fichier.
     * @param file Le fichier à traiter.
     * @return Le SHA-1 du fichier.
     * @throws IOException Problème lors de la lecture du fichier.
     * @throws NoSuchAlgorithmException Problème lors de l'initialisation de l'algorithme de hachage.
     */
    private static String calculateSha1(File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest sha1Digest = MessageDigest.getInstance("SHA-1");
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            sha1Digest.update(buffer, 0, bytesRead);
        }
        fis.close();

        byte[] sha1Bytes = sha1Digest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : sha1Bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}

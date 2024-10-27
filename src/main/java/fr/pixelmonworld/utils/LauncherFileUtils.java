package fr.pixelmonworld.utils;

import fr.pixelmonworld.Launcher;

import java.io.*;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utilitaire concernant les fichiers.
 */
public class LauncherFileUtils {

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

    /**
     * Permet de vérifier si un fichier correspond bien à un SHA-1 donné.
     * @param file1 Le premier fichier à comparer.
     * @param shaToCompare Le SHA-1 à comparer.
     * @return Vrai si les fichiers sont identiques, faux sinon.
     * @throws IOException Problème lors de la lecture des fichiers.
     * @throws NoSuchAlgorithmException Problème lors de l'initialisation de l'algorithme de hachage.
     */
    public static boolean areFilesIdentical(File file1, String shaToCompare) throws IOException, NoSuchAlgorithmException {
        return calculateSha1(file1).equals(shaToCompare);
    }
}

package fr.pixelmonworld.domain;

/**
 * Enumération contenant le type de messages qui peuvent être envoyés au pop-up d'information.
 */
public enum TypeMessage {
    // Si jamais on est en train de mettre à jour Minecraft
    UPDATE_MINECRAFT("download_minecraft_text.png"),
    // Si jamais on est en train de récupérer les fichiers nécessaires sur le site web
    RECUPERATION_FICHIERS("get_files_text.png");

    // Le nom du fichier associé au message
    String nomFichier;

    /**
     * Constructeur par défaut.
     * @param nomFichier Le nom du fichier à afficher dans le pop-up d'information.
     */
    TypeMessage(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public String getNomFichier() {
        return nomFichier;
    }
}

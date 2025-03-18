package fr.pixelmonworld.domain;

import java.io.Serializable;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Représente une actualité.
 */
public class News implements Serializable {

    // Le titre de la news.
    private String title;
    // L'URL de la news.
    private URL url;
    // La date de la news.
    private LocalDateTime date;

    /**
     * Constructeur par défaut.
     * @param title Le titre de la news.
     * @param url L'URL de la news.
     * @param date La date de la news.
     */
    public News(String title, URL url, LocalDateTime date) {
        this.title = title;
        this.url = url;
        this.date = date;
    }

    /**
     * Permet de récupérer le titre de la news.
     * @return Le titre de la news.
     */
    public URL getUrl() {
        return url;
    }

    /**
     * Permet d'afficher la news sous forme de chaîne de caractères.
     * @return La news sous forme de chaîne de caractères.
     */
    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", url=" + url +
                ", date=" + date +
                '}';
    }

    /** Permet de comparer deux news.
     * @param obj L'objet à comparer.
     * @return Vrai si les deux news sont égales, faux sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        News news = (News) obj;
        return title.equals(news.title) &&
                url.equals(news.url) &&
                date.equals(news.date);
    }

    /**
     * Permet d'afficher la news sous forme de message affichable dans le launcher.
     * @return La news sous forme de message affichable dans le launcher.
     */
    public String toMessage() {
        if (title.length() > 80) {
            title = title.substring(0, 80) + "...";
        }
        return "<html><p>" + date.format(DateTimeFormatter.ofPattern("dd/MM/YY")) + " - " + title + "</p></html>";
    }
}

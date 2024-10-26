package fr.pixelmonworld.domain;

import java.io.Serializable;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class News implements Serializable {
    private String title;
    private URL url;
    private LocalDateTime date;

    public News(String title, URL url, LocalDateTime date) {
        this.title = title;
        this.url = url;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public URL getUrl() {
        return url;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", url=" + url +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        News news = (News) obj;
        return title.equals(news.title) &&
                url.equals(news.url) &&
                date.equals(news.date);
    }

    public String toMessage() {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/YY")) + " - " + title;
    }
}

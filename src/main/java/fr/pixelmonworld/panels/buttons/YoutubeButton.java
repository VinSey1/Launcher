package fr.pixelmonworld.panels.buttons;

import fr.pixelmonworld.domain.DefaultLauncherButton;
import fr.theshark34.swinger.event.SwingerEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

import static fr.pixelmonworld.utils.ImagesSelector.getBufferedImage;

public class YoutubeButton extends DefaultLauncherButton {
    public YoutubeButton(Component parent, int y) throws IOException {
        super(parent, y, getBufferedImage("youtube_button.png"));
    }

    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        try {
            Desktop.getDesktop().browse(URI.create("https://www.youtube.com/c/PixelmonWorldFR"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}

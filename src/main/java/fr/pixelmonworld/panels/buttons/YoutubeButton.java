package fr.pixelmonworld.panels.buttons;

import fr.pixelmonworld.domain.LauncherButton;
import fr.theshark34.swinger.event.SwingerEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

import static fr.pixelmonworld.utils.ImagesSelector.getBufferedImage;

public class YoutubeButton extends LauncherButton {
    public YoutubeButton(int parentWidth, int y) throws IOException {
        super(parentWidth, y, getBufferedImage("youtube_button.png"));
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

package fr.pixelmonworld.panels.buttons;

import fr.pixelmonworld.domain.DefaultLauncherButton;
import fr.theshark34.swinger.event.SwingerEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

import static fr.pixelmonworld.utils.ImagesSelector.getBufferedImage;

public class TwitterButton extends DefaultLauncherButton {
    public TwitterButton(Component parent, int y) throws IOException {
        super(parent, y, getBufferedImage("twitter_button.png"));
        this.setBounds(this.getX() + this.getX() / 2, this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        try {
            Desktop.getDesktop().browse(URI.create("https://twitter.com/PixelmonWorldFR"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
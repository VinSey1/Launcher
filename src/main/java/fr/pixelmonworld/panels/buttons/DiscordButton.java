package fr.pixelmonworld.panels.buttons;

import fr.pixelmonworld.domain.DefaultLauncherButton;
import fr.theshark34.swinger.event.SwingerEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

import static fr.pixelmonworld.utils.ImagesSelector.getBufferedImage;

public class DiscordButton extends DefaultLauncherButton {

    public DiscordButton(Component parent, int y) throws IOException {
        super(parent, y, getBufferedImage("discord_button.png"));
    }

    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        try {
            Desktop.getDesktop().browse(URI.create("https://discord.gg/cu4XET2"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}

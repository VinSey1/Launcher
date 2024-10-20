package fr.pixelmonworld.panels.main;

import fr.pixelmonworld.domain.DefaultLauncherButton;
import fr.theshark34.swinger.event.SwingerEvent;

import java.awt.*;
import java.io.IOException;

import static fr.pixelmonworld.utils.ImagesSelector.getBufferedImage;

public class CloseButton extends DefaultLauncherButton {
    public CloseButton(Component parent, int y) throws IOException {
        super(parent, y, getBufferedImage("close_button.png"));
        this.setBounds(parent.getWidth() - this.getWidth() - 10, y);
    }

    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        System.exit(0);
    }
}

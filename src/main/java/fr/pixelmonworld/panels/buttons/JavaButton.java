package fr.pixelmonworld.panels.buttons;

import fr.pixelmonworld.domain.DefaultLauncherButton;
import fr.theshark34.swinger.event.SwingerEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

import static fr.pixelmonworld.utils.ImagesSelector.getBufferedImage;

public class JavaButton extends DefaultLauncherButton {

    public JavaButton(int parentWidth, int y) throws IOException {
        super(parentWidth, y, getBufferedImage("java_button.png"));
    }

    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        try {
            Desktop.getDesktop().browse(URI.create("https://www.java.com/fr/download/manual.jsp"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}

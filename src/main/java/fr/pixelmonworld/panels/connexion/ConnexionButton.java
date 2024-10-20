package fr.pixelmonworld.panels.connexion;

import fr.pixelmonworld.Launcher;
import fr.pixelmonworld.domain.DefaultLauncherButton;
import fr.pixelmonworld.utils.MicrosoftThread;
import fr.pixelmonworld.utils.MinecraftThread;
import fr.theshark34.swinger.event.SwingerEvent;

import java.awt.*;
import java.io.IOException;

import static fr.pixelmonworld.utils.ImagesSelector.getBufferedImage;

public class ConnexionButton extends DefaultLauncherButton {

    boolean microsoftAuth;

    public ConnexionButton(Component parent, int y) throws IOException {
        super(parent, y, getBufferedImage("microsoft_button.png"));
        microsoftAuth = Launcher.defaultAuth();
        if (microsoftAuth) {
            this.setTexture(getBufferedImage("minecraft_button.png"));
        }
    }

    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        if (!microsoftAuth) {
            Thread t = new Thread(new MicrosoftThread(this));
            t.start();
        } else {
            Thread t = new Thread(new MinecraftThread());
            t.start();
        }
    }

    public void update() throws IOException {
        this.microsoftAuth = true;
        this.setTexture(getBufferedImage("minecraft_button.png"));
    }
}

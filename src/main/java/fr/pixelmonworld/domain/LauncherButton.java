package fr.pixelmonworld.domain;

import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LauncherButton extends STexturedButton implements SwingerEventListener {

    public LauncherButton(int parentWidth, int y, BufferedImage texture) {
        super(texture, texture);
        this.setBounds(parentWidth / 2 - texture.getWidth() / 2, y - texture.getHeight() / 2, texture.getWidth(), texture.getHeight());
        this.addEventListener(this);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void onEvent(SwingerEvent swingerEvent) {}
}

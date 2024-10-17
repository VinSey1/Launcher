package fr.pixelmonworld;

import fr.theshark34.swinger.textured.STexturedButton;

import java.awt.image.BufferedImage;

public class PlayButton extends STexturedButton {

    public PlayButton(BufferedImage texture, BufferedImage textureHover) {
        super(texture, textureHover);

        this.setBounds(100, 100);
        this.setLocation(200, 200);
    }
}

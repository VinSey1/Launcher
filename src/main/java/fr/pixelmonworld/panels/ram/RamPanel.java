package fr.pixelmonworld.panels.ram;

import fr.pixelmonworld.Launcher;
import fr.pixelmonworld.domain.DefaultLauncherPanel;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static fr.pixelmonworld.utils.ImagesSelector.getBufferedImage;

public class RamPanel extends DefaultLauncherPanel implements SwingerEventListener {

    private STexturedButton moins;
    private STexturedButton plus;
    private JLabel background;

    public RamPanel(Component parent, int width, int height, int x, int y) throws IOException {
        super(parent, width, height, x, y);

        moins = new STexturedButton(getBufferedImage("fill.png"), getBufferedImage("fill.png"));
        moins.setBounds(400, 20, 40, 40);
        moins.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        moins.addEventListener(this);
        this.add(moins);

        plus = new STexturedButton(getBufferedImage("fill.png"), getBufferedImage("fill.png"));
        plus.setBounds(500, 20, 40, 40);
        plus.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        plus.addEventListener(this);
        this.add(plus);

        this.genererBackground();
    }

    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        if (swingerEvent.getSource() == plus) {
            Launcher.addRam();
        }
        if (swingerEvent.getSource() == moins) {
            Launcher.removeRam();
        }
        try {
            this.remove(background);
            genererBackground();
        } catch (IOException e) {
            Launcher.erreurInterne(e);
        }
    }

    private void genererBackground() throws IOException {
        // Ajout du background
        ImageIcon backgroundIcon = new ImageIcon(Objects.requireNonNull(getBufferedImage("ram_" + Launcher.getRam() + ".png")));
        background = new JLabel(backgroundIcon);
        background.setBounds(0, 0, backgroundIcon.getIconWidth(), backgroundIcon.getIconHeight());
        this.add(background);
        this.repaint();
    }
}

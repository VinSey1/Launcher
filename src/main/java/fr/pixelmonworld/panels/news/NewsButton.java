package fr.pixelmonworld.panels.news;

import fr.pixelmonworld.Launcher;
import fr.pixelmonworld.domain.DefaultLauncherButton;
import fr.theshark34.swinger.event.SwingerEvent;
import org.pushingpixels.radiance.animation.api.Timeline;
import org.pushingpixels.radiance.animation.api.callback.TimelineCallback;

import java.awt.*;
import java.io.IOException;

import static fr.pixelmonworld.utils.ResourcesUtils.getBufferedImage;

public class NewsButton extends DefaultLauncherButton {

    private float opacity = 1.0f;

    private Thread thread;
    private Timeline fade;
    private boolean interrupted;
    /**
     * Constructeur par défaut permettant de créer un bouton de news.
     * @param parent Le parent à appeler pour repaint lors d'une mise à jour graphique.
     * @param x L'axe X sur lequel afficher l'image.
     * @param y L'axe Y sur lequel afficher l'image.
     * @throws IOException Problème lors d'une mise à jour graphique.
     */
    public NewsButton(Component parent, int x, int y) throws IOException {
        super(parent, x, y, getBufferedImage("news_button.png"));

        fade = Timeline.builder(this)
                .addPropertyToInterpolate("opacity", 1.0f, 0.0f)
                .addCallback(new TimelineCallback() {
                    @Override
                    public void onTimelineStateChanged(Timeline.TimelineState timelineState, Timeline.TimelineState timelineState1, float v, float v1) {
                        if (timelineState1 == Timeline.TimelineState.DONE) {
                            Timeline.builder(NewsButton.this)
                                    .addPropertyToInterpolate("opacity", 0.0f, 1.0f)
                                    .setDuration(500)
                                    .build()
                                    .play();
                        }
                    }

                    @Override
                    public void onTimelinePulse(float v, float v1) {

                    }
                })
                .setDuration(500)
                .build();

        // Permet de changer le render toutes les 10 secondes
        this.thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    fade.play();
                    Thread.sleep(1000);
                } catch (Exception e) {
                    if (e instanceof InterruptedException) {
                        return;
                    }
                    Launcher.erreurInterne(e);
                }
            }
        });
        this.thread.start();
    }

    /**
     * Permet d'afficher l'onglet de news quand le bouton est appuyé.
     * @param swingerEvent L'événement à traiter.
     */
    @Override
    public void onEvent(SwingerEvent swingerEvent) {
        if (!this.thread.isInterrupted()) {
            this.thread.interrupt();
            try {
                this.setTexture(getBufferedImage("news_button_clicked.png"));
            } catch (IOException e) {
                Launcher.erreurInterne(e);
            }
            this.fade.end();
        }
        ((NewsPanel) getParent()).showNews();
    }


    public void setOpacity(float opacity) {
        this.opacity = opacity;
        repaint();
    }

    /**
     * Permet de repeindre le composant graphique en prenant en compte l'opacité.
     * @param g L'objet graphique à repeindre.
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        super.paintComponent(g2d);
        g2d.dispose();
    }
}

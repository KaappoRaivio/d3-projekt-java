package process;

import camera.Camera;
import entity.Entity;
import event.EventType;
import misc.Vector2D;
import scene.Scene;
import event.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class Renderer implements Process {
    private final Object lock = new Object();
    private final Camera camera;

    private final JFrame frame;
    private final JPanel panel;
    private final Vector2D viewportDimensions;

    private Set<Entity> entities = Collections.emptySet();

    private int frameCounter = 0;

    public Renderer(Camera camera) {
        this.camera = camera;
        viewportDimensions = camera.getViewportDimensions();

        this.frame = new JFrame();
        panel = new MyRenderPanel();
        panel.setSize(viewportDimensions.getIInt(), viewportDimensions.getJInt());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private void resetBuffer() {
    }

    @Override
    public Set<Entity> update(Scene Scene, Set<Entity> entities, double deltaTime, int frameCounter, Function<Event, Void> dispatchEvent) {
        this.resetBuffer();
        this.frameCounter = frameCounter;

        synchronized (lock) {
            this.entities = entities;
        }
        EventQueue.invokeLater(() -> {
            panel.repaint();
            frame.repaint();
        });
        return entities;
    }

    @Override
    public void onEvent(Event event, Function<Event, Void> dispatchEvent) {
        if (event.getEventType() == EventType.HALT) {
            EventQueue.invokeLater(() -> {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            });
        }
    }

    class MyRenderPanel extends JPanel {
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(viewportDimensions.getIInt(), viewportDimensions.getJInt());
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            long start = System.currentTimeMillis();
//            for (int y = 0; y < viewportDimensions.getJ(); y++) {
//                for (int x = 0; x < viewportDimensions.getI(); x++) {
//                    Pixel pixel = buffer[y][x];
//                    Color color = new Color(pixel.getR(), pixel.getG(), pixel.getB(), pixel.getA());
//                    image.setRGB(x, y, color.getRGB());
//                }
//            }

//            g.drawImage(buffer, 0, 0, this);
            synchronized (lock) {
                g.drawString("Frame " + frameCounter, 100, 100);
                entities.forEach(entity -> {
//                    if (entity.getName().equalsIgnoreCase("focus")) {
//                        camera.setCameraPosition(entity.getPosition());
//                    }
                    if (camera.isVisibleInViewport(entity.getPosition(), entity.getSprite().getDimensions())) {
                        Vector2D entityPosition = entity.getPosition().add(camera.getCameraPosition().multiply(-1));
                        g.drawImage(
                                entity.getSprite().getOriginalImage(),
                                entityPosition.getIInt(),
                                entityPosition.getJInt(),
                                entity.getSprite().getDimensions().getIInt(),
                                entity.getSprite().getDimensions().getJInt(),
                                this);

                        g.drawString(entity.getPosition() + ", " + entity.getVelocity(), entityPosition.getIInt(), entityPosition.getJInt());

                        g.drawRect(
                                entityPosition.getIInt(),
                                entityPosition.getJInt(),
                                entity.getSprite().getDimensions().getIInt(),
                                entity.getSprite().getDimensions().getJInt());
                    }
                });
            }
            long end = System.currentTimeMillis();
            System.out.println("Burning took " + (end - start) + " ms");
        }
    }
}

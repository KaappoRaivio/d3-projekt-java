package process;

import camera.Camera;
import entity.Entity;
import misc.Pixel;
import misc.Vector2D;
import sprite.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Renderer implements Process {
    private final Object lock = new Object();
    private final Camera camera;

    private JFrame frame;
    private JPanel panel;
    private Vector2D viewportDimensions;

    private List<Entity> entities = Collections.emptyList();

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
    public List<Entity> update(List<Entity> entities, double deltaTime, int frameCounter) {
        this.resetBuffer();

        synchronized (lock) {
            this.entities = entities;
        }
        EventQueue.invokeLater(() -> {
            panel.repaint();
            frame.repaint();
        });
        return entities;
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
                entities.forEach(entity -> {
                    Vector2D entityPosition = entity.getPosition();
                    g.drawImage(
                            entity.getSprite().getOriginalImage(),
                            entityPosition.getIInt(),
                            entityPosition.getJInt(),
                            entity.getSprite().getDimensions().getIInt(),
                            entity.getSprite().getDimensions().getJInt(),
                            this);
                });
            }
            long end = System.currentTimeMillis();
            System.out.println("Burning took " + (end - start) + " ms");
        }
    }
}

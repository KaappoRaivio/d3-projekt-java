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
    private final Camera camera;
    volatile private BufferedImage buffer;

    private JFrame frame;
    private JPanel panel;
    private Vector2D viewportDimensions;

    private List<Entity> entities = Collections.emptyList();

    public Renderer(Camera camera) {
        this.camera = camera;
        viewportDimensions = camera.getViewportDimensions();
        this.buffer = new BufferedImage(viewportDimensions.getIInt(), viewportDimensions.getJInt(), BufferedImage.TYPE_INT_ARGB);

        this.frame = new JFrame();
        panel = new MyRenderPanel();
        panel.setSize(viewportDimensions.getIInt(), viewportDimensions.getJInt());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private void resetBuffer() {
//        for (int y = 0; y < buffer.length; y++) {
//            for (int x = 0; x < buffer[y].length; x++) {
//                buffer[y][x] = new Pixel(0, 0, 0, 0);
//            }
//        }
        buffer.setRGB(0, 0, buffer.getWidth(), buffer.getHeight(), new int[buffer.getWidth() * buffer.getHeight()], 0, 0);
    }

    @Override
    public List<Entity> update(List<Entity> entities, double deltaTime, int frameCounter) {
        this.resetBuffer();

        this.entities = entities;
//        entities.forEach(entity -> {
//            Vector2D entityPosition = entity.getPosition();
//            Vector2D entitySize = entity.getSprite().getDimensions();
//
//            Vector2D cameraPosition = camera.getCameraPosition();
//            if (camera.isVisibleInViewport(entityPosition, entitySize)) {
//                Sprite entitySprite = entity.getSprite();
//                Vector2D dimensions = entitySprite.getDimensions();
//                for (int y = 0; y < dimensions.getJ(); y++) {
//                    for (int x = 0; x < dimensions.getI(); x++) {
//                        int bufferY = cameraPosition.getJInt() + entityPosition.getJInt() + y;
//                        int bufferX = cameraPosition.getIInt() + entityPosition.getIInt() + x;
//                        if (bufferX < 0 || bufferX >= buffer.getWidth()
//                                || bufferY < 0 || bufferY >= buffer.getHeight()) {
//                            continue;
//                        }
//                        Pixel pixel = entitySprite.getPixelAt(Vector2D.of(x, y));
//                        buffer.setRGB(bufferX, bufferY, new Color(pixel.getR(), pixel.getG(), pixel.getB(), pixel.getA()).getRGB());
//                    }
//                }
//            }
//        });
        EventQueue.invokeLater(() -> {
            panel.repaint();
            frame.repaint();
        });
//        frame.validate();
//        frame.setVisible(true);
//        AssetManager.printImage(this.buffer);

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
            long end = System.currentTimeMillis();
            System.out.println("Burning took " + (end - start) + " ms");
        }
    }
}

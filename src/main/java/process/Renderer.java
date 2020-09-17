package process;

import camera.Camera;
import entity.Entity;
import misc.Pixel;
import misc.Vector2D;
import sprite.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Renderer implements Process {
    private final Camera camera;
    volatile private Pixel[][] buffer;

    private JFrame frame;
    private JPanel panel;
    private Vector2D viewportDimensions;

    public Renderer(Camera camera) {
        this.camera = camera;
        viewportDimensions = camera.getViewportDimensions();
        this.buffer = new Pixel[viewportDimensions.getJInt()][viewportDimensions.getIInt()];

        this.frame = new JFrame();
        panel = new MyRenderPanel();
        panel.setSize(viewportDimensions.getIInt(), viewportDimensions.getJInt());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private void resetBuffer() {
        for (int y = 0; y < buffer.length; y++) {
            for (int x = 0; x < buffer[y].length; x++) {
                buffer[y][x] = new Pixel(0, 0, 0, 0);
            }
        }
    }

    @Override
    public List<Entity> update(List<Entity> entities, double deltaTime, int frameCounter) {
        this.resetBuffer();

        entities.forEach(entity -> {
            Vector2D entityPosition = entity.getPosition();
            Vector2D entitySize = entity.getSprite().getDimensions();

            Vector2D cameraPosition = camera.getCameraPosition();
            if (camera.isVisibleInViewport(entityPosition, entitySize)) {
                Sprite entitySprite = entity.getSprite();
                Vector2D dimensions = entitySprite.getDimensions();
                for (int y = 0; y < dimensions.getJ(); y++) {
                    for (int x = 0; x < dimensions.getI(); x++) {
                        int bufferY = cameraPosition.getJInt() + entityPosition.getJInt() + y;
                        int bufferX = cameraPosition.getIInt() + entityPosition.getIInt() + x;
                        if (bufferX < 0 || bufferX >= buffer[0].length
                                || bufferY < 0 || bufferY >= buffer.length) {
                            continue;
                        }
                        buffer[bufferY][bufferX] = buffer[bufferY][bufferX].blend(entitySprite.getPixelAt(Vector2D.of(x, y)));
                    }
                }
            }
        });
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
            BufferedImage image = new BufferedImage(viewportDimensions.getIInt(), viewportDimensions.getJInt(), BufferedImage.TYPE_INT_ARGB);
            for (int y = 0; y < viewportDimensions.getJ(); y++) {
                for (int x = 0; x < viewportDimensions.getI(); x++) {
                    Pixel pixel = buffer[y][x];
                    Color color = new Color(pixel.getR(), pixel.getG(), pixel.getB(), pixel.getA());
                    image.setRGB(x, y, color.getRGB());
                }
            }
            g.drawImage(image, 0, 0, this);
        }
    }
}
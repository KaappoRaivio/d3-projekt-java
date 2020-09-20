import process.Collision;
import camera.Camera;
import entity.Entity;
import entity.MovableEntity;
import misc.Vector2D;
import process.Mover;
import process.Process;
import process.Renderer;
import scene.Scene;
import sprite.Sprite;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    public static void main(String[] args) {
        List<Entity> entities = new CopyOnWriteArrayList<>(Arrays.asList(
                new MovableEntity(
                        Vector2D.of(0, 0),
                        Vector2D.of(0, 0),
                        "Testi",
                        new Sprite("/home/kaappo/git/d3/src/main/resources/graphics/idle0.png", Vector2D.of(20, 20))

                ),
                new MovableEntity(
                        Vector2D.of(500, 0),
                        Vector2D.of(-30, 0),
                        "Testi",
                        new Sprite("/home/kaappo/git/d3/src/main/resources/graphics/idle0.png", Vector2D.of(10, 10))

                )
        ));
        Vector2D maxSize = Vector2D.of(640, 480);
        List<Process> processes = Arrays.asList(
                new Renderer(new Camera(Vector2D.of(0, 0), maxSize)),
                new Mover(),
                new Collision(maxSize)
//                new Process() {
//                    private Random random = new Random();
//
//                    @Override
//                    public List<Entity> update(Scene Scene, List<Entity> entities, double deltaTime, int frameCounter) {
//                        if (frameCounter % 60 == 0) {
//                            int size = random.nextInt(5) + 5;
//                            String path = random.nextBoolean() ? "/home/kaappo/git/d3/src/main/resources/graphics/idle0.png"
//                                    : "/home/kaappo/git/d3/src/main/resources/graphics/idle0.png";
//                            entities.add(new MovableEntity(
//                                        Vector2D.of(random.nextInt(1800), 500),
//                                        Vector2D.of(-20 + random.nextInt(40), 0),
//                                        "Added on frame " + frameCounter,
//                                        new Sprite(path, Vector2D.of(size, size))
//                                )
//                            );
//                        }
//                        return entities;
//                    }
//                }
        );
        Scene scene = new Scene(
                entities,
                processes,
                Vector2D.of(16384, 16384)
        );

        scene.play(0);
    }
}

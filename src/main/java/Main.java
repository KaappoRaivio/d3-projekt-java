import sprite.AssetManager;
import camera.Camera;
import entity.Entity;
import entity.MovableEntity;
import misc.Vector2D;
import process.Mover;
import process.Process;
import process.Renderer;
import scene.Scene;
import sprite.Sprite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        List<Entity> entities = new ArrayList<>(Arrays.asList(
                new MovableEntity(
                        Vector2D.of(0, 0),
                        Vector2D.of(50, -30),
                        "Testi",
                        new Sprite("/home/kaappo/git/d3/src/main/resources/graphics/idle0.png", Vector2D.of(20, 20))

                ),
                new MovableEntity(
                        Vector2D.of(580, 0),
                        Vector2D.of(-80, -20),
                        "Testi",
                        new Sprite("/home/kaappo/git/d3/src/main/resources/graphics/idle0.png", Vector2D.of(10, 10))

                )
        ));
        List<Process> processes = Arrays.asList(
                new Renderer(new Camera(Vector2D.of(0, 0), Vector2D.of(1800, 1000))),
                new Mover(),
                new Process() {
                    private Random random = new Random();

                    @Override
                    public List<Entity> update(List<Entity> entities, double deltaTime, int frameCounter) {
                        if (frameCounter % 3 == 0) {
                            int size = random.nextInt(5) + 5;
                            String path = random.nextBoolean() ? "/home/kaappo/git/d3/src/main/resources/graphics/testrainbow.png"
                                    : "/home/kaappo/git/d3/src/main/resources/graphics/idle0.png";
                            entities.add(new MovableEntity(
                                        Vector2D.of(random.nextInt(1800), random.nextInt(1000)),
                                        Vector2D.of(-20 + random.nextInt(40), -1000),
                                        "Added on frame " + frameCounter,
                                        new Sprite(path, Vector2D.of(size, size))
                                )
                            );
                        }
                        return entities;
                    }
                }
        );
        Scene scene = new Scene(
                entities,
                processes
        );

        scene.play();
    }
}

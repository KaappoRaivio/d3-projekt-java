import camera.Camera;
import entity.Entity;
import entity.ImmovableEntity;
import entity.MovableEntity;
import misc.Vector2D;
import process.Collision;
import process.Mover;
import process.Process;
import process.Renderer;
import scene.Scene;
import sprite.Sprite;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    public static void main(String[] args) {
        List<Entity> entities = new CopyOnWriteArrayList<>(Arrays.asList(
//                new MovableEntity(
//                        Vector2D.of(0, 0),
//                        Vector2D.of(200, 0),
//                        "Big",
//                        new Sprite("/home/kaappo/git/d3/src/main/resources/graphics/idle0.png", Vector2D.of(20, 20)),
//                        1
//                ),
//                new MovableEntity(
//                        Vector2D.of(300, 70),
//                        Vector2D.of(-200, 0),
//                        "Small",
//                        new Sprite("/home/kaappo/git/d3/src/main/resources/graphics/idle0.png", Vector2D.of(10, 10)),
//                        1
//                )
//                new MovableEntity(
//                        Vector2D.of(500, 0),
//                        Vector2D.of(0, 0),
//                        "Big2",
//                        new Sprite("/home/kaappo/git/d3/src/main/resources/graphics/idle0.png", Vector2D.of(20, 20)),
//                        10
//                )
        ));
        Vector2D maxSize = Vector2D.of(1280, 720);
        List<Process> processes = Arrays.asList(
                new Renderer(new Camera(Vector2D.of(-360, -360), maxSize)),
                new Mover(),
                new Collision(maxSize),
                new Process() {
                    private Random random = new Random();

                    @Override
                    public List<Entity> update(Scene Scene, List<Entity> entities, double deltaTime, int frameCounter) {
                        if ((frameCounter + 1) == 200) {
                            entities.add(new ImmovableEntity(
                                            Vector2D.of(0, 0),
                                            "Big",
                                            new Sprite("/home/kaappo/git/d3/src/main/resources/graphics/idle0.png", Vector2D.of(20, 20))
                                    )
                            );
                            entities.add(new MovableEntity(
                                            Vector2D.of(500, 0),
                                            Vector2D.of(-200, 0),
                                            "Big",
                                            new Sprite("/home/kaappo/git/d3/src/main/resources/graphics/idle0.png", Vector2D.of(20, 20)),
                                            100
                                    )
                            );
                            entities.add(new MovableEntity(
                                            Vector2D.of(350, 70),
                                            Vector2D.of(0, 0),
                                            "Small",
                                            new Sprite("/home/kaappo/git/d3/src/main/resources/graphics/idle0.png", Vector2D.of(10, 10)),
                                            1
                                    ));
                        }
                        return entities;
                    }
                }
        );
        Scene scene = new Scene(
                entities,
                processes,
                Vector2D.of(16384, 16384)
        );

        scene.play(0);
    }
}

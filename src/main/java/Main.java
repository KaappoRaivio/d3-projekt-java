import camera.Camera;
import entity.*;
import event.*;
import misc.Vector2D;
import process.*;
import process.Process;
import scene.Scene;
import sprite.Sprite;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Set<Entity> entities = new CopyOnWriteArraySet<>(Arrays.asList(

                new ImmovableEntity(
                        Vector2D.of(0, 0),
                        "Wall1",
                        new Sprite("/home/kaappo/git/d3/src/main/resources/graphics/idle0.png", Vector2D.of(2, 200)),
                        new EntityProperties(1, 1, 1, CollisionModel.RECTANGULAR)
                ),
                new ImmovableEntity(
                        Vector2D.of(50, 0),
                        "Wall2",
                        new Sprite("/home/kaappo/git/d3/src/main/resources/graphics/idle0.png", Vector2D.of(200, 2)),
                        new EntityProperties(1, 1, 1, CollisionModel.RECTANGULAR)
                ),
                new ImmovableEntity(
                        Vector2D.of(800, 50),
                        "Wall2",
                        new Sprite("/home/kaappo/git/d3/src/main/resources/graphics/idle0.png", Vector2D.of(2, 200)),
                        new EntityProperties(1, 1, 1, CollisionModel.RECTANGULAR)
                ),
                new ImmovableEntity(
                        Vector2D.of(50, 2900),
                        "Wall2",
                        new Sprite("/home/kaappo/git/d3/src/main/resources/graphics/idle0.png", Vector2D.of(200, 2)),
                        new EntityProperties(1, 1, 1, CollisionModel.RECTANGULAR)
                )
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
        Vector2D maxSize = Vector2D.of(1920, 1080);
                            MovableEntity biggest = new MovableEntity(
                                    Vector2D.of(200, 100),
                                    Vector2D.of(0, 0),
                                    "asd",
                                    new Sprite("/home/kaappo/git/d3/src/main/resources/graphics/idle0.png", Vector2D.of(10, 10)),
                                    new EntityProperties(1, 1, 0, CollisionModel.RECTANGULAR)
                            );
        List<Process> processes = Arrays.asList(
                new Renderer(new Camera(Vector2D.of(-360, -360), maxSize), true),
                new Popper(),
                new Collider(),
                new AirResistance(),
                new Mover(),
                new Collision(maxSize),
                new Killer(),
                new Keyboard(biggest),
                new Process() {
                    private Random random = new Random();

                    @Override
                    public Set<Entity> update(Scene Scene, Set<Entity> entities, double deltaTime, int frameCounter, Function<Event, Void> dispatchEvent) {
                        if ((frameCounter + 1) == 200) {
                            entities.add(biggest);

//                            entities.add(new MovableEntity(
//                                    Vector2D.of(400, 0),
//                                    Vector2D.of(-50, 0),
//                                    "biggest",
//                                    new Sprite("/home/kaappo/git/d3/src/main/resources/graphics/idle0.png", Vector2D.of(10, 10)),
//                                    new EntityProperties(1, 1, 0, CollisionModel.RECTANGULAR)
//                            ));
//
//                            entities.add(new MovableEntity(
//                                    Vector2D.of(500, 0),
//                                    Vector2D.of(0, 50),
//                                    "biggest",
//                                    new Sprite("/home/kaappo/git/d3/src/main/resources/graphics/idle0.png", Vector2D.of(10, 10)),
//                                    new EntityProperties(1, 1, 0, CollisionModel.RECTANGULAR)
//                            ));
//
//                            entities.add(new MovableEntity(
//                                    Vector2D.of(500, 500),
//                                    Vector2D.of(0, -50),
//                                    "biggest",
//                                    new Sprite("/home/kaappo/git/d3/src/main/resources/graphics/idle0.png", Vector2D.of(10, 10)),
//                                    new EntityProperties(1, 1, 0, CollisionModel.RECTANGULAR)
//                            ));
                        }

                        return entities;
                    }

                    @Override
                    public void onEvent(Event event, Function<Event, Void> dispatchEvent, Set<Entity> entities) {
                        if (event.getEventType() == EventType.COLLISION) {
                            List<Entity> collidingEntities = ((CollisionEvent) event).getCollidingEntities();
                            Entity entity1 = collidingEntities.get(0);
                            Entity entity2 = collidingEntities.get(1);

                            if (entity1.getVelocity().length() > entity2.getVelocity().length() && entity2.isMovable()) {
                                dispatchEvent.apply(new KillEvent(entity2));
                            } else if (entity1.getVelocity().length() < entity2.getVelocity().length() && entity1.isMovable()) {
                                dispatchEvent.apply(new KillEvent(entity1));
                            }
                        }
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

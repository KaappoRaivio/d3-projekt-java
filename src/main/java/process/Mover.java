package process;

import entity.Entity;
import event.Event;
import misc.Vector2D;
import scene.Scene;

import java.util.Set;
import java.util.function.Function;

public class Mover implements Process {
    private int counter = 0;
//    private static final Vector2D gravity = new Vector2D( 0, 300);
    private static final Vector2D gravity = new Vector2D( 0, 0);

    @Override
    public Set<Entity> update(Scene Scene, Set<Entity> entities, double deltaTime, int frameCounter, Function<Event, Void> dispatchEvent) {
        ++counter;
        for (Entity entity : entities) {
            if (entity.isMovable()) {
                entity.setVelocity(entity.getVelocity().add((entity.getAcceleration().add(gravity).multiply(deltaTime))));
                entity.setPosition(entity.getPosition().add(entity.getVelocity().multiply(deltaTime)));
            }
        }

        return entities;
    }

    @Override
    public void onEvent(Event event, Function<Event, Void> dispatchEvent, Set<Entity> entities) {

    }
}

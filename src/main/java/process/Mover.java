package process;

import entity.Entity;
import misc.Vector2D;

import java.util.List;

public class Mover implements Process {
    private int counter = 0;
    private static final Vector2D gravity = new Vector2D(0, 1000);
    @Override
    public List<Entity> update(List<Entity> entities, double deltaTime, int frameCounter) {
        ++counter;
        for (Entity entity : entities) {
            if (entity.isMovable()) {
                entity.setVelocity(entity.getVelocity().add((entity.getAcceleration().add(gravity).multiply(deltaTime))));
                entity.setPosition(entity.getPosition().add(entity.getVelocity().multiply(deltaTime)));
            }
        }

        return entities;
    }
}

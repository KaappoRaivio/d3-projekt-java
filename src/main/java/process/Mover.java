package process;

import entity.Entity;
import misc.Vector2D;

import java.util.List;

public class Mover implements Process {
    private int counter = 0;
    private static final Vector2D gravity = new Vector2D(0, 9.81);
    @Override
    public List<Entity> update(List<Entity> entities, double deltaTime, int frameCounter) {
        ++counter;
        for (Entity entity : entities) {
            if (entity.isMovable()) {
//                System.out.println(entity);
//                System.out.println(entity.getAcceleration().add(gravity));
//                System.out.println("Velocity: " + entity.getVelocity().add((entity.getAcceleration().add(gravity).multiply(deltaTime))));
                entity.setVelocity(entity.getVelocity().add((entity.getAcceleration().add(gravity).multiply(deltaTime))));
//                System.out.println("Position: " + entity.getPosition().add(entity.getVelocity().multiply(deltaTime)));
                entity.setPosition(entity.getPosition().add(entity.getVelocity().multiply(deltaTime)));
//                System.out.println(entity);
            }
        }

        return entities;
    }
}

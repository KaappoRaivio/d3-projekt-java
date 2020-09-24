package process;

import entity.Entity;
import event.CollisionEvent;
import event.Event;
import event.EventType;
import misc.Vector2D;
import scene.Scene;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class Popper implements Process {
    @Override
    public Set<Entity> update(Scene scene, Set<Entity> entities, double deltaTime, int frameCounter, Function<Event, Void> dispatchEvent) {
        return entities;
    }

    @Override
    public void onEvent(Event event, Function<Event, Void> dispatchEvent) {
        if (event.getEventType() == EventType.COLLISION) {
            List<Entity> collidingEntities = ((CollisionEvent) event).getCollidingEntities();
            Entity entity1 = collidingEntities.get(0);
            Entity entity2 = collidingEntities.get(1);

            if (entity1.getVelocity().length() > entity2.getVelocity().length()) {
                entity1.setPosition(getNewPosition(entity2, entity1));
            } else {
                entity2.setPosition(getNewPosition(entity1, entity2));
            }
        }
    }

    private Vector2D getNewPosition (Entity stationary, Entity moving) {
        double deltaX = getSmallestDistanceBetweenVertices(stationary.getPosition().getI(), stationary.getSprite().getDimensions().getI(),
                moving.getPosition().getI(), moving.getSprite().getDimensions().getI());

        double deltaY = getSmallestDistanceBetweenVertices(stationary.getPosition().getJ(), stationary.getSprite().getDimensions().getJ(),
                moving.getPosition().getJ(), moving.getSprite().getDimensions().getJ());

        if (deltaX < 0 && deltaY < 0) {
            if (deltaX > deltaY) {
                return moving.getPosition().add(Vector2D.of(deltaX, 0));
            } else {
                return moving.getPosition().add(Vector2D.of(0, deltaY));
            }
        } else {
            return moving.getPosition();
        }
//        if (deltaX < 0) {
//            return moving.getPosition().add(Vector2D.of(deltaX, 0));
//        } else if (deltaY < 0) {
//            return moving.getPosition().add(Vector2D.of(0, deltaY));
//        } else {
//            return moving.getPosition();
//        }
    }

    private double getSmallestDistanceBetweenVertices (double x1, double d1, double x2, double d2) {
        double candinate1 = (x1 + d1) - x2;
        double candinate2 = x1 - (x2 + d2);

        if (Math.abs(candinate1) < Math.abs(candinate2)) {
            return candinate1;
        } else {
            return candinate2;
        }
    }
}

package process;

import entity.Entity;
import event.CollisionEvent;
import event.Event;
import event.EventType;
import event.HaltEvent;
import misc.Vector2D;
import scene.Scene;

import java.util.List;
import java.util.function.Function;

public class Collider implements Process {
    @Override
    public List<Entity> update(Scene scene, List<Entity> entities, double deltaTime, int frameCounter, Function<Event, Void> dispatchEvent) {
        return entities;
    }

    @Override
    public void onEvent(Event event) {
        if (event.getEventType() == EventType.COLLISION) {
            List<Entity> collidingEntities = ((CollisionEvent) event).getCollidingEntities();

            Entity entity1 = collidingEntities.get(0);
            Entity entity2 = collidingEntities.get(1);

            if (!entity1.isMovable()) {
                entity2.setVelocity(entity2.getVelocity().multiply(-1));
                return;
            } else if (!entity2.isMovable()) {
                entity1.setVelocity(entity1.getVelocity().multiply(-1));
                return;
            }

            Vector2D u1 = entity1.getVelocity();
            Vector2D u2 = entity2.getVelocity();

            double m1 = entity1.getMass();
            double m2 = entity2.getMass();

            Vector2D v1 = u1.multiply((m1 - m2) / (m1 + m2)).add(u2.multiply(2 / (m1 + m2) * m2));
            Vector2D v2 = u1.multiply(2 / (m1 + m2) * m1).add(u2.multiply((m2 - m1) / (m1 + m2)));

            entity1.setVelocity(v1);
            entity2.setVelocity(v2);

        }
    }
}
